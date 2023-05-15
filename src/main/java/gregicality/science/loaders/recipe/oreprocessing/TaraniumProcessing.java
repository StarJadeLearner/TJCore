package gregicality.science.loaders.recipe.oreprocessing;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static com.ibm.icu.lang.UCharacter.GraphemeClusterBreak.LV;
import static gregicality.science.api.recipes.GCYSRecipeMaps.*;
import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;

/**
 * <p>Credit to the <a href="https://github.com/GT-IMPACT">GT-IMPACT Modpack</a>.
 * This processing chain was adapted from their Hyper Fuel production process</p>
 *
 *
 * The Taranium Production Process
 *
 * <p>
 * Produces Taranium and Bedrock Dust from Bedrock Smoke
 * </p>
 *
 * <p>Main Products: Taranium Dust, Taranium Fuels, Bedrock Dust</p>
 * <p>Side Products: Platinum, Iridium, Osmium</p>
 *
 */
public class TaraniumProcessing {

    public static void init() {
        // regular
        regularFractions();
        regularFractionCleaning();
        regularFractionCracking();
        regularFractionSeparation();

        // enriched
        enrichedFractions();
        enrichedFractionCleaning();
        enrichedFractionCracking();
        enrichedFractionSeparation();

        fuels();
        generators();
    }

    private static void regularFractions() {

    }

    private static void regularFractionCleaning() {

    }

    private static void regularFractionCracking() {

    }

    private static void regularFractionSeparation() {

    }

    private static void enrichedFractions() {

    }

    private static void enrichedFractionCleaning() {

    }

    private static void enrichedFractionCracking() {

    }

    private static void enrichedFractionSeparation() {

    }

    private static void fuels() {

    }

    private static void generators() {

    }
}
