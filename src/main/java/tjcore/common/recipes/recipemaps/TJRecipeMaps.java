package tjcore.common.recipes.recipemaps;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.*;
import gregtech.core.sound.GTSoundEvents;
import tjcore.api.recipe.CVDRecipeBuilder;

public class TJRecipeMaps {

    public static final RecipeMap<SimpleRecipeBuilder> SPINNING_RECIPES = new RecipeMap<>("spinning_machine", 1, 4, 1, 1, 0, 2, 0, 0, new SimpleRecipeBuilder(), false).setSound(GTSoundEvents.MIXER).setSlotOverlay(false, true, GuiTextures.CANISTER_OVERLAY).setProgressBar(GuiTextures.PROGRESS_BAR_MIXER, ProgressWidget.MoveType.CIRCULAR);
    public static final RecipeMap<SimpleRecipeBuilder> FSZM_RECIPES = new RecipeMap<>("free_stand_zone_melter", 1, 1, 1, 1, 0, 1, 0, 1, new SimpleRecipeBuilder(), false).setSlotOverlay(false, false, GuiTextures.HEATING_OVERLAY_1).setSlotOverlay(true, false, GuiTextures.CRYSTAL_OVERLAY);
    public static final RecipeMap<IntCircuitRecipeBuilder> NANOSCALE_GROWTH_RECIPES = new RecipeMap<>("nanoscale_growth", 0, 1, 1, 1, 1, 1, 0, 0, new IntCircuitRecipeBuilder(), false).setSound(GTSoundEvents.SUS_RECORD).setSlotOverlay(true, false, GuiTextures.OUT_SLOT_OVERLAY).setSlotOverlay(false, true, GuiTextures.NEUTRAL_MATTER_OVERLAY).setSlotOverlay(false, false, GuiTextures.CIRCUIT_OVERLAY);
    public static final RecipeMap<IntCircuitRecipeBuilder> PRINTER_RECIPES = new RecipeMap<>("printer", 1, 9, 1, 2, 0, 1, 0, 1, new IntCircuitRecipeBuilder(), false).setSound(GTSoundEvents.ASSEMBLER);
    public static final RecipeMap<IntCircuitRecipeBuilder> LOOM_RECIPES = new RecipeMap<>("loom", 1, 4, 1, 1, 0, 2, 0, 0, new IntCircuitRecipeBuilder(), false).setSlotOverlay(false, false, GuiTextures.STRING_SLOT_OVERLAY).setSlotOverlay(true, false, GuiTextures.PAPER_OVERLAY);
    public static final RecipeMap<SimpleRecipeBuilder> STEAM_ASSEMBLER_RECIPES = new RecipeMap<>("steam_assembler", 2, 6, 1, 1, 0, 2, 0, 0, new SimpleRecipeBuilder(), false).setSound(GTSoundEvents.ASSEMBLER);
    public static final RecipeMap<SimpleRecipeBuilder> TREE_FARMER_RECIPES = new RecipeMap<>("tree_farmer", 1, 3, 0, 6, 0, 1, 0, 1, new SimpleRecipeBuilder(), false).setSound(GTSoundEvents.ASSEMBLER);
    public static final RecipeMap<SimpleRecipeBuilder> EXPOSURE_CHAMBER_RECIPES = new RecipeMap<>("exposure_chamber", 0, 4, 0, 4, 0, 2, 0, 2, new SimpleRecipeBuilder(), false).setProgressBar(GuiTextures.PROGRESS_BAR_MASS_FAB, ProgressWidget.MoveType.HORIZONTAL).setSound(GTSoundEvents.ASSEMBLER);
    public static final RecipeMap<SimpleRecipeBuilder> ARMOR_INFUSER_RECIPES = new ArmorInfuserRecipeMap("armor_infuser", 1, 12, 1, 1, 0, 0, 0, 0, new SimpleRecipeBuilder(), false);
    public static final RecipeMap<SimpleRecipeBuilder> MEGA_FUSION_RECIPES = new RecipeMap<>("mega_fusion", 0, 1, 0, 3, 0, 4, 0, 3, new SimpleRecipeBuilder(), false);
    public static final RecipeMap<SimpleRecipeBuilder> SURFACE_ROCK_DRILL_RECIPES = new RecipeMap<>("surface_rock_drill", 0, 12, 1, 1, 0, 0, 0, 0, new SimpleRecipeBuilder(), false);
    public static final RecipeMap<SimpleRecipeBuilder> LAMINATOR_RECIPES = new RecipeMap<>("laminator", 1, 6, 1, 2, 0, 2, 0, 0, new SimpleRecipeBuilder(), false).setSound(GTSoundEvents.ASSEMBLER);
    public static final RecipeMap<SimpleRecipeBuilder> PRESSURE_REACTOR_RECIPES = new RecipeMap<>("pressure_reactor", 0, 3, 0, 3, 0, 3, 0, 3, new SimpleRecipeBuilder(), false).setSound(GTSoundEvents.ASSEMBLER);
    public static final RecipeMap<BlastRecipeBuilder> ROASTING_RECIPES = new RecipeMap<>("roaster", 0, 3, 0,3,0,3,0,3, new BlastRecipeBuilder(), false).setSlotOverlay(false, false, GuiTextures.FURNACE_OVERLAY_1).setSound(GTSoundEvents.FURNACE);
    public static final RecipeMap<PrimitiveRecipeBuilder> PRIMITIVE_ROASTING_RECIPES = new RecipeMap<>("primitive_roaster", 0, 3, 0, 3, 0, 1, 0, 1, new PrimitiveRecipeBuilder(), false).setSlotOverlay(false, false, GuiTextures.FURNACE_OVERLAY_1).setProgressBar(GuiTextures.PROGRESS_BAR_COKE_OVEN, ProgressWidget.MoveType.HORIZONTAL).setSound(GTSoundEvents.FURNACE);
    public static final RecipeMap<CVDRecipeBuilder> CVD_RECIPES = new RecipeMap<>("cvd_unit", 0, 3, 0, 3, 0, 3, 0, 3, new CVDRecipeBuilder(), false);
    public static final RecipeMap<PrimitiveRecipeBuilder> BLOOMERY_RECIPES = new RecipeMap<>("bloomery", 0, 3, 0, 3, 0, 0,0,0, new PrimitiveRecipeBuilder(), false);
}
