package tjcore.common.metatileentities.multi.electric;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
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

import static gregicality.science.api.recipes.GCYSRecipeMaps.HIGH_TEMP_REACTOR_RECIPES;
import static gregtech.api.pattern.TraceabilityPredicate.HEATING_COILS;

public class HighTempReactor extends RecipeMapMultiblockController implements IHeatingCoil {
    private int multiBlastTemp;
    public HighTempReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, HIGH_TEMP_REACTOR_RECIPES);
        recipeMapWorkable = new HeatingCoilRecipeLogic(this);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.UP)
                .aisle(
                        "F   F",
                        "     ",
                        "F   F")
                .aisle(
                        "HHHHH",
                        "HOOOH",
                        "HHSHH")
                .aisle(
                        "HOOOH",
                        "H   H",
                        "HOOOH")
                .aisle(
                        "HHHHH",
                        "HOOOH",
                        "HHHHH")
//                .aisle(
//                        "HHH",
//                        "HHH",
//                        "HHH")
                .where('S', selfPredicate())
                .where('#', TraceabilityPredicate.ANY)
                .where(' ',TraceabilityPredicate.AIR)
                .where('O', HEATING_COILS.get())
                .where('C', states(GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.CORROSION_PROOF_CASING)))
                .where('H', states(GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.CORROSION_PROOF_CASING))
                        .or(autoAbilities()))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.Invar).getBlock(Materials.Invar)))
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
        return GCYMTextures.CORROSION_PROOF_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntityID) {
        return new HighTempReactor(this.metaTileEntityId);
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
