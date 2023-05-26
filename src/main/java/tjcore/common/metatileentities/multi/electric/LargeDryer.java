package tjcore.common.metatileentities.multi.electric;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.science.api.recipes.GCYSRecipeMaps;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.impl.HeatingCoilRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;
import tjcore.common.TJTextures;
import tjcore.common.blocks.BlockGeneratorCoil;

import javax.annotation.Nonnull;

import java.util.List;

import static gregicality.science.api.recipes.GCYSRecipeMaps.DRYER_RECIPES;
import static gregtech.api.pattern.TraceabilityPredicate.HEATING_COILS;

public class LargeDryer extends RecipeMapMultiblockController implements IHeatingCoil {
    private int multiBlastTemp;
    public LargeDryer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GCYSRecipeMaps.LARGE_DRYER_RECIPES);
        recipeMapWorkable = new HeatingCoilRecipeLogic(this);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.UP)
                .aisle(
                        "HHHHH",
                        "HHHHH",
                        "HHHHH",
                        "HHHHH",
                        "HHSHH")
                .aisle(
                        "HGGGH",
                        "G   G",
                        "G   G",
                        "G   G",
                        "HGGGH")
                .aisle(
                        "HGGGH",
                        "G   G",
                        "G   G",
                        "G   G",
                        "HGGGH")
                .aisle(
                        "HHHHH",
                        "HOOOH",
                        "HOOOH",
                        "HOOOH",
                        "HHHHH")
                .where('S', selfPredicate())
                .where('#', TraceabilityPredicate.ANY)
                .where(' ',TraceabilityPredicate.AIR)
                .where('O', HEATING_COILS.get())
                .where('C', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF)))
                .where('H', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF))
                        .or(autoAbilities()))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.Invar).getBlock(Materials.Invar)))
                .where('G', states(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .build();
    }
    public int getCurrentTemperature(){
        return multiBlastTemp;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        if(type instanceof IHeatingCoilBlockStats){
            multiBlastTemp = ((IHeatingCoilBlockStats) type).getCoilTemperature();
        }
        else {
            multiBlastTemp = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
        }
        multiBlastTemp += Math.max(0, GTUtility.getFloorTierByVoltage(this.getEnergyContainer().getInputVoltage())-2);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()){
            textList.add(new TextComponentString("Heat Capacity: "+multiBlastTemp).setStyle((new Style()).setColor(TextFormatting.RED)));
        }
        super.addDisplayText(textList);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        multiBlastTemp = 0;
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        return multiBlastTemp >= (Integer)recipe.getProperty(TemperatureProperty.getInstance(),0);

    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.HEAT_PROOF_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntityID) {
        return new LargeDryer(this.metaTileEntityId);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return TJTextures.ROASTER_OVERLAY;
    }
}
