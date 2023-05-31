package tjcore.common.metatileentities.multi.electric;

import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.science.common.block.GCYSMetaBlocks;
import gregicality.science.common.block.blocks.BlockGCYSMultiblockCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidTank;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import tjcore.api.material.TJMaterials;
import tjcore.api.metatileentity.IAtmosphereRecipeController;
import tjcore.api.metatileentity.TJMultiblockAbilities;
import tjcore.api.recipe.AtmosphereProperty;
import tjcore.api.recipe.AtmosphereRecipeInfo;
import tjcore.common.blocks.BlockTurbineBlades;
import tjcore.common.blocks.TJMetaBlocks;

import javax.annotation.Nonnull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static gregtech.api.unification.material.Materials.Air;
import static tjcore.common.recipes.recipemaps.TJRecipeMaps.CVD_RECIPES;

public class MetaTileEntityCVDUnit extends RecipeMapMultiblockController implements IAtmosphereRecipeController {
    private int pressure;
    private Fluid atmosphere;
    public MetaTileEntityCVDUnit(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, CVD_RECIPES);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.UP)
                .aisle(
                        "SSSSS",
                        "SsssS",
                        "SsssS",
                        "SsssS",
                        "SSSSS")
                .aisle(
                        "FHHHF",
                        "H   H",
                        "H   H",
                        "H   H",
                        "FHcHF")
                .aisle(
                        "FGGGF",
                        "G   G",
                        "G   G",
                        "G   G",
                        "FGGGF")
                .aisle(
                        "FSSSF",
                        "S F S",
                        "SF FS",
                        "S F S",
                        "FSSSF")
                .aisle(
                        "AAAAA",
                        "AGGGA",
                        "AGGGA",
                        "AGGGA",
                        "AAAAA")

                .where('c', selfPredicate())
                .where(' ', TraceabilityPredicate.AIR)
                .where('S', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PTFE_INERT_CASING)))
                .where('H', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PTFE_INERT_CASING))
                        .or(autoAbilities())
                        .or(abilities(TJMultiblockAbilities.ATMOSPHERE).setExactLimit(1)))
                .where('G', states(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS)))
                .where('A', states(GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.NONCONDUCTING_CASING)))
                .where('s', states(GCYSMetaBlocks.MULTIBLOCK_CASING.getState(BlockGCYSMultiblockCasing.CasingType.SUBSTRATE)))
                .where('F', states(MetaBlocks.FRAMES.get(TJMaterials.LutetiumTantalate).getBlock(TJMaterials.LutetiumTantalate)))
                .build();
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        boolean success =  super.checkRecipe(recipe, consumeIfSuccess);
        if (recipe.hasProperty(AtmosphereProperty.getInstance())) {
            AtmosphereRecipeInfo info = recipe.getProperty(AtmosphereProperty.getInstance(), new AtmosphereRecipeInfo(0, 64000, Air.getFluid()));
            return (info.getMinPressure() <= pressure &&
                    info.getMaxPressure() >= pressure &&
                    (info.getAtmosphere() == null || info.getAtmosphere().equals(atmosphere)) &&
                    success
            );
        } else {
            return false;
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (pressure > 0) {
            textList.add(new TextComponentString(pressure + "mb " + I18n.format(getAbilities(TJMultiblockAbilities.ATMOSPHERE).get(0).getFluid().getFluid().getUnlocalizedName())));
        } else {
            textList.add(new TextComponentString("Vacuum: " + pressure));
        }
        super.addDisplayText(textList);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.INERT_PTFE_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntityID) {
        return new MetaTileEntityCVDUnit(this.metaTileEntityId);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.MULTIBLOCK_WORKABLE_OVERLAY;
    }

    @Override
    public void setPressure(int newPressure) {
        pressure = newPressure;
    }

    @Override
    public void setAtmosphere(Fluid newAtmosphere) {
        atmosphere = newAtmosphere;
    }
}
