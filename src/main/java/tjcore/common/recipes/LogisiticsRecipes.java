package tjcore.common.recipes;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;

import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static tjcore.api.material.TJMaterials.*;
import static tjcore.common.metaitem.TJMetaItems.STEAM_MOTOR;
import static tjcore.common.metatileentities.TJMetaTileEntities.STEAM_MIXER_BRONZE;
import static tjcore.common.recipes.recipemaps.TJRecipeMaps.*;

public class LogisiticsRecipes {
    public static void init() {
        longDistance();
        multiblockComponents();
    }



    private static void longDistance() {
        Material[] conductors = new Material[]{Tin, Copper, Gold, Aluminium, Platinum};
        for (int i = 0; i < longDistanceWireMaterials.length; i++) {
            LAMINATOR_RECIPES.recipeBuilder()
                    .EUt(VA[1 + i])
                    .duration(45)
                    .input(ring, SilicaCeramic, 2)
                    .input(foil, i < 2 ? Rubber : SiliconeRubber, 4)
                    .input(wireGtSingle, conductors[i], 4)
                    .fluidInputs(i < 3 ? Polyethylene.getFluid(144) : i == 3 ? Polytetrafluoroethylene.getFluid(144) : Polycaprolactam.getFluid(144))
                    .output(cableGtQuadruple, longDistanceWireMaterials[i])
                    .buildAndRegister();
        }
    }

    private static void multiblockComponents() {
        ModHandler.removeRecipeByOutput(PASSTHROUGH_HATCH_ITEM.getStackForm());
        ModHandler.removeRecipeByOutput(PASSTHROUGH_HATCH_FLUID.getStackForm());
        ModHandler.addShapedRecipe("steam_mixer_bronze", STEAM_MIXER_BRONZE.getStackForm(),
                "GRG", "GMG", "PHP",
                'G', OreDictUnifier.get("blockGlass"),
                'R', OreDictUnifier.get(rotor, Bronze),
                'H', MetaBlocks.STEAM_CASING.getItemVariant(BlockSteamCasing.SteamCasingType.BRONZE_HULL),
                'P', OreDictUnifier.get(pipeNormalFluid, Bronze),
                'M', STEAM_MOTOR);
    }
}
