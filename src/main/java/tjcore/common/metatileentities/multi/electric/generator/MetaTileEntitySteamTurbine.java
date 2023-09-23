package tjcore.common.metatileentities.multi.electric.generator;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.IFluidTank;
import org.apache.commons.lang3.ArrayUtils;
import tjcore.api.axle.IRotationProvider;
import tjcore.api.block.ITurbineBladeStats;
import tjcore.common.blocks.BlockBearing;
import tjcore.common.blocks.BlockTurbineBlades;
import tjcore.common.blocks.TJMetaBlocks;
import tjcore.common.pipelike.rotation.AxleWhole;
import tjcore.common.pipelike.rotation.BlockRotationAxle;
import tjcore.common.pipelike.rotation.TileEntityRotationAxle;
import tjcore.common.recipes.TJFuelMaps;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

import static tjcore.common.recipes.TJFuelMaps.combustionFuels;
import static tjcore.common.recipes.TJFuelMaps.steamTurbineFuels;

public class MetaTileEntitySteamTurbine extends MultiblockWithDisplayBase implements IRotationProvider {

    private int turbineTier;
    private int bearingTier;

    int duration;
    int quantity;
    int quantityReal;

    private float rps;
    private float torque;
    private float speedDecrement = 0.99f;
    private AxleWhole axleWhole;
    private IFluidTank tankIn;
    private BlockPos bearingPos;
    private int recipeTickTimer = 0;
    private boolean doublyConnected = false;
    private boolean hasFuel;

    public MetaTileEntitySteamTurbine(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }


    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            textList.add(new TextComponentString(axleWhole != null ? "Connected to Axle" : "Not Connected to Axle"));
            textList.add(new TextComponentString("Blade Tier: " + turbineTier));
            textList.add(new TextComponentString("Bearing Tier: " + bearingTier));
            textList.add(new TextComponentString("Rotations Per Second: " + rps));
            textList.add(new TextComponentString("Torque: " + torque));
            textList.add(new TextComponentString(hasFuel ? "Consumption: \nMax: " + quantityReal + "/" + quantity + "mb / " + (duration == 1 ? "" : duration) + "t" : "Invalid or No Fuel"));
        }
        super.addDisplayText(textList);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type1 = context.get("BladesType");
        if (type1 instanceof ITurbineBladeStats) {
            this.turbineTier = ((ITurbineBladeStats) type1).getTier();
        } else {
            this.turbineTier = 0;
        }
        Object type2 = context.get("BearingType");
        if (type2 instanceof BlockBearing.BearingType) {
            this.bearingTier = ((BlockBearing.BearingType) type2).getTier();
        } else {
            this.bearingTier = 0;
        }
        this.tankIn = getAbilities(MultiblockAbility.IMPORT_FLUIDS).get(0);
    }

    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.BACK, RelativeDirection.RIGHT, RelativeDirection.DOWN)
                .aisle(
                        " EEE  ",
                        "EEEEE ",
                        " EEE  ")
                .aisle(
                        "EE EE ",
                        "PBBBE ",
                        "EE EE ")
                .aisle(
                        "E   E ",
                        "EBRBE ",
                        "E   E ")
                .aisle(
                        "EE EES",
                        "EBBBPT",
                        "EE EES")
                .aisle(
                        " EEE S",
                        "EEEEES",
                        " EEE S")
                .where(' ', TraceabilityPredicate.ANY)
                .where('~', TraceabilityPredicate.AIR)
                .where('E', states(GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.STEAM_CASING)))
                .where('T', selfPredicate())
                .where('S', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(1)))
                .where('P', states(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE)))
                .where('R', bearings())
                .where('B', blades())
                .build();
    }

    private TraceabilityPredicate blades() {
        return new TraceabilityPredicate(blockWorldState -> {
            IBlockState blockState = blockWorldState.getBlockState();
            Block block = blockState.getBlock();
            if (block instanceof BlockTurbineBlades) {
                BlockTurbineBlades.TurbineBladesType bladesType = ((BlockTurbineBlades) block).getState(blockState);
                    Object currentBlades = blockWorldState.getMatchContext().getOrPut("BladesType", bladesType);
                return currentBlades.equals(bladesType);
            }
            return false;
        }, () -> ArrayUtils.addAll(
                Arrays.stream(BlockTurbineBlades.TurbineBladesType.values())
                        .map(type -> new BlockInfo(TJMetaBlocks.TURBINE_BLADES.getState(type), null))
                        .toArray(BlockInfo[]::new)));
    }

    private TraceabilityPredicate bearings() {
        return new TraceabilityPredicate(blockWorldState -> {
            IBlockState blockState = blockWorldState.getBlockState();
            Block block = blockState.getBlock();
            if (block instanceof BlockBearing) {
                bearingPos = blockWorldState.getPos();
                BlockBearing.BearingType bearingType = ((BlockBearing) block).getState(blockState);
                Object currentBearing = blockWorldState.getMatchContext().getOrPut("BearingType", bearingType);
                return true;
            }
            return false;
        }, () -> ArrayUtils.addAll(
                Arrays.stream(BlockBearing.BearingType.values())
                        .map(type -> new BlockInfo(TJMetaBlocks.BLOCK_BEARING.getState(type), null))
                        .toArray(BlockInfo[]::new)));
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public boolean isStructureFormed() {
        boolean formed = super.isStructureFormed();
        if (!formed && axleWhole != null) {
            axleWhole.removeProvider(this);
            if(doublyConnected) {
                axleWhole.deleteNetAndCreateNew(bearingPos);
            }
        }
        return formed;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true, true);
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.AIR_VENT_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitySteamTurbine(metaTileEntityId);
    }

    @Override
    public void pushRotation(float rotationSpeed, float torque) {
        if (!getWorld().isRemote && axleWhole != null) {
            axleWhole.pushRotation(rotationSpeed, torque);
        }
    }

    public float getRotation() {
        return rps;
    }

    public void setAxleWhole(AxleWhole axleNew) {
        axleWhole = axleNew;
        if (axleNew != null && !getWorld().isRemote) {
            axleWhole.addProvider(this);
        }
    }

    @Override
    public void joinNet(boolean recalculate) {
        if (recalculate) {
            doublyConnected = false;
        }
        if (bearingPos != null) {
            BlockPos[] posArr = new BlockPos[]{bearingPos.north(), bearingPos.south(), bearingPos.east(), bearingPos.west()};
            for (BlockPos pos : posArr) {
                if (getWorld().getBlockState(pos).getBlock() instanceof BlockRotationAxle) {
                    if (axleWhole == null) {
                        axleWhole = ((TileEntityRotationAxle) getWorld().getTileEntity(pos)).getAxleWhole();
                    } else if (axleWhole != ((TileEntityRotationAxle) getWorld().getTileEntity(pos)).getAxleWhole()) {
                        axleWhole.incorperate(((TileEntityRotationAxle) getWorld().getTileEntity(pos)).getAxleWhole());
                        doublyConnected = true;
                    }
                    if(axleWhole != null) {
                        setAxleWhole(axleWhole);
                    }
                }
            }
        }
    }

    @Override
    public void updateFormedValid() {
        if (!getWorld().isRemote && isStructureFormed()) {
            if (!doublyConnected) {
                joinNet(false);
            }
            if (axleWhole != null) {
                pushRotation(rps, torque);
            }
            if (recipeTickTimer == 0) {
                if (tankIn.getFluid() != null) {
                    TJFuelMaps.TJFuelBurnStats stats = steamTurbineFuels.get(tankIn.getFluid().getFluid());
                    if (stats != null) {
                         this.rps = (float) Math.pow(4, bearingTier) / 2;
                         duration = stats.duration.apply(bearingTier);
                         quantity = (int) (stats.quantity.apply(turbineTier));
                         quantityReal = Math.min(tankIn.getFluidAmount(), quantity);
                         tankIn.drain(quantityReal, true);
                         this.recipeTickTimer = duration;
                         hasFuel = true;
                         this.torque = (float) Math.pow(4, turbineTier) * ((float) quantityReal / (float) quantity);
                    } else {
                        rps = rps > 0.25 ? rps * speedDecrement : 0;
                        hasFuel = false;
                    }
                } else {
                    hasFuel = false;
                    rps = rps > 0.25 ? rps * speedDecrement : 0;
                }
            }
            if (recipeTickTimer > 0) {recipeTickTimer -= 1;}
        }
    }
}
