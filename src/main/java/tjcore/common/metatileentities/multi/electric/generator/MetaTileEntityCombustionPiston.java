package tjcore.common.metatileentities.multi.electric.generator;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.*;
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
import tjcore.common.blocks.BlockPistonHead;
import tjcore.common.blocks.BlockTurbineBlades;
import tjcore.common.blocks.TJMetaBlocks;
import tjcore.common.pipelike.rotation.AxleWhole;
import tjcore.common.pipelike.rotation.BlockRotationAxle;
import tjcore.common.pipelike.rotation.TileEntityRotationAxle;
import tjcore.common.recipes.TJFuelMaps;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

import static gregtech.api.unification.material.Materials.StainlessSteel;
import static gregtech.api.unification.material.Materials.Titanium;
import static tjcore.common.recipes.TJFuelMaps.combustionFuels;
import static tjcore.common.recipes.TJFuelMaps.gasTurbineFuels;

public class MetaTileEntityCombustionPiston extends MultiblockWithDisplayBase implements IRotationProvider {

    private int pistonTier;
    private int bearingTier;

    int duration;
    int quantity;
    int quantityReal;

    private float rps;
    private float torque;
    private float speedDecrement = 0.99f;
    private AxleWhole axleWhole;
    private IFluidTank tankIn;
    private BlockPos[] bearingPos = new BlockPos[2];
    private int recipeTickTimer = 0;
    private boolean doublyConnected = false;
    private boolean hasFuel;

    public MetaTileEntityCombustionPiston(ResourceLocation metaTileEntityId) {
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
            textList.add(new TextComponentString("Piston Tier: " + pistonTier));
            textList.add(new TextComponentString("Bearing Tier: " + bearingTier));
            textList.add(new TextComponentString("Rotations Per Second: " + rps));
            textList.add(new TextComponentString("Torque: " + torque));
            textList.add(new TextComponentString(hasFuel ? "Consumption: \nMax: " + quantity + "mb / " + (duration == 1 ? "" : duration) + "t" : "Invalid or No Fuel"));
            textList.add(new TextComponentString(hasFuel ?"Real: "  + quantityReal + "mb / " + (duration == 1 ? "" : duration) + "t" : ""));
        }
        super.addDisplayText(textList);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type1 = context.get("PistonType");
        if (type1 instanceof BlockPistonHead.PistonHeadType) {
            this.pistonTier = ((BlockPistonHead.PistonHeadType) type1).getTier();
        } else {
            this.pistonTier = 0;
        }
        Object type2 = context.get("BearingType");
        if (type2 instanceof BlockBearing.BearingType) {
            this.bearingTier = ((BlockBearing.BearingType) type2).getTier();
        } else {
            this.bearingTier = 0;
        }
        this.doublyConnected = false;
        this.tankIn = getAbilities(MultiblockAbility.IMPORT_FLUIDS).get(0);
    }


    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.DOWN)
                .aisle(
                        "CCCCC CCC",
                        "CCCC     ",
                        "CCCCC CCC")
                .aisle(
                        "CPIPC CBC",
                        "E~NCCCC~~",
                        "CPTPC CBC")
                .aisle(
                        "CCCCC CCC",
                        "CCCC     ",
                        "CCCCC CCC")
                .aisle(
                        "F   F    ",
                        "         ",
                        "F   F    ")
                .where(' ', TraceabilityPredicate.ANY)
                .where('~', TraceabilityPredicate.AIR)
                .where('T', selfPredicate())
                .where('C', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE)))
                .where('E', states(MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.ENGINE_INTAKE_CASING)))
                .where('P', states(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE)))
                .where('B', bearings())
                .where('N', pistons())
                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                .where('F', frames(Titanium))
                .build();
    }

    private TraceabilityPredicate pistons() {
        return new TraceabilityPredicate(blockWorldState -> {
            IBlockState blockState = blockWorldState.getBlockState();
            Block block = blockState.getBlock();
            if (block instanceof BlockPistonHead) {
                BlockPistonHead.PistonHeadType pistonType = ((BlockPistonHead) block).getState(blockState);
                Object currentPiston = blockWorldState.getMatchContext().getOrPut("PistonType", pistonType);
                return currentPiston.equals(pistonType);
            }
            return false;
        }, () -> ArrayUtils.addAll(
                Arrays.stream(BlockPistonHead.PistonHeadType.values())
                        .map(type -> new BlockInfo(TJMetaBlocks.PISTON_HEAD.getState(type), null))
                        .toArray(BlockInfo[]::new)));
    }

    private TraceabilityPredicate bearings() {
        return new TraceabilityPredicate(blockWorldState -> {
            IBlockState blockState = blockWorldState.getBlockState();
            Block block = blockState.getBlock();
            if (block instanceof BlockBearing) {
                if (bearingPos[0] == null) {
                    bearingPos[0] = blockWorldState.getPos();
                } else {
                    bearingPos[1] = blockWorldState.getPos();
                }
                BlockBearing.BearingType bearingType = ((BlockBearing) block).getState(blockState);
                Object currentBearing = blockWorldState.getMatchContext().getOrPut("BearingType", bearingType);
                return currentBearing.equals(bearingType);
            }
            return false;
        }, () -> ArrayUtils.addAll(
                Arrays.stream(BlockBearing.BearingType.values())
                        .map(type -> new BlockInfo(TJMetaBlocks.BLOCK_BEARING.getState(type), null))
                        .toArray(BlockInfo[]::new)));
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.STABLE_TITANIUM_CASING;
    }

    @Override
    public boolean isStructureFormed() {
        boolean formed = super.isStructureFormed();
        if (!formed && axleWhole != null) {
            axleWhole.removeProvider(this);
            if(doublyConnected) {
                axleWhole.deleteNetAndCreateNew(bearingPos[0]);
                axleWhole.deleteNetAndCreateNew(bearingPos[1]);
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
        return Textures.MULTIBLOCK_WORKABLE_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCombustionPiston(metaTileEntityId);
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
        if (!doublyConnected) {
            if (bearingPos != null) {
                BlockPos[] posArr = new BlockPos[]{
                        bearingPos[0].north(), bearingPos[0].south(), bearingPos[0].east(), bearingPos[0].west(),
                        bearingPos[1].north(), bearingPos[1].south(), bearingPos[1].east(), bearingPos[1].west()
                };
                for (BlockPos pos : posArr) {
                    if (getWorld().getBlockState(pos).getBlock() instanceof BlockRotationAxle) {
                        if (axleWhole == null) {
                            axleWhole = ((TileEntityRotationAxle) getWorld().getTileEntity(pos)).getAxleWhole();
                        } else if (axleWhole != ((TileEntityRotationAxle) getWorld().getTileEntity(pos)).getAxleWhole()) {
                            axleWhole.incorperate(((TileEntityRotationAxle) getWorld().getTileEntity(pos)).getAxleWhole());
                            doublyConnected = true;
                        }
                        if (axleWhole != null) {
                            setAxleWhole(axleWhole);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateFormedValid() {
        if (!getWorld().isRemote && isStructureFormed()) {
            joinNet(false);
            if (axleWhole != null) {
                pushRotation(rps, torque);
            }
            if (recipeTickTimer == 0) {
                if (tankIn.getFluid() != null) {
                    TJFuelMaps.TJFuelBurnStats stats = combustionFuels.get(tankIn.getFluid().getFluid());
                     if (stats != null) {
                         hasFuel = true;
                         this.rps = (float) Math.pow(4, bearingTier) / 2;
                         duration = stats.duration.apply(bearingTier);
                         quantity = (int) (stats.quantity.apply(pistonTier) * rps);
                         quantityReal = Math.min(tankIn.getFluidAmount(), quantity);
                         tankIn.drain(quantityReal, true);
                         this.recipeTickTimer = duration;
                         this.torque = (float) Math.pow(4, pistonTier) * ((float) quantityReal / (float) quantity);
                    } else {
                         torque = 0;
                         hasFuel = false;
                         rps = rps > 0.25 ? rps * speedDecrement : 0;
                    }
                } else {
                    torque = 0;
                    hasFuel = false;
                    rps = rps > 0.25 ? rps * speedDecrement : 0;

                }
            }
            if (recipeTickTimer > 0) {recipeTickTimer -= 1;}
        }
    }
}
