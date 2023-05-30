package tjcore.common.recipes;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.OreProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.loaders.recipe.GTRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class Ores {

    public static void RegisterOres() {
        OrePrefix.crushed.addProcessingHandler(PropertyKey.ORE, Ores::removeTinyPiles);
    }

    public static void removeTinyPiles(OrePrefix crushedPrefix, Material material, OreProperty property) {
        ItemStack impureDustStack = OreDictUnifier.get(OrePrefix.dustImpure, material);
        Material byproductMaterial = GTUtility.selectItemInList(0, material, property.getOreByProducts(), Material.class);

        ItemStack crushedPurifiedOre = GTUtility.copy(
                OreDictUnifier.get(OrePrefix.crushedPurified, material),
                OreDictUnifier.get(OrePrefix.dust, material));
        ItemStack crushedCentrifugedOre = GTUtility.copy(
                OreDictUnifier.get(OrePrefix.crushedCentrifuged, material),
                OreDictUnifier.get(OrePrefix.dust, material));

        // TODO figure out the voltage for these autogenned recipes

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ORE_WASHER_RECIPES, new ItemStack[]{OreDictUnifier.get(crushedPrefix, material), IntCircuitIngredient.getIntegratedCircuit(1)}, new FluidStack[]{Materials.Water.getFluid(1000)});
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ORE_WASHER_RECIPES, new ItemStack[]{OreDictUnifier.get(crushedPrefix, material)}, new FluidStack[]{Materials.DistilledWater.getFluid(100)});
        //RecipeMaps.ORE_WASHER_RECIPES.findRecipe(16, new ArrayList<ItemStack>(OreDictUnifier.get(crushedPrefix, material)), new FluidStack[]{Materials.Water.getFluid(1000)}, 5);

        RecipeMaps.ORE_WASHER_RECIPES.recipeBuilder()
                .input(crushedPrefix, material)
                .fluidInputs(Materials.Water.getFluid(1000))
                .circuitMeta(1)
                .outputs(crushedPurifiedOre,
                        OreDictUnifier.get(OrePrefix.dust, Materials.Stone))
                .chancedOutput(OreDictUnifier.get(OrePrefix.dust, byproductMaterial), 3300, 0)
                .buildAndRegister();

        RecipeMaps.ORE_WASHER_RECIPES.recipeBuilder()
                .input(crushedPrefix, material)
                .fluidInputs(Materials.DistilledWater.getFluid(100))
                .outputs(crushedPurifiedOre,
                        OreDictUnifier.get(OrePrefix.dust, Materials.Stone))
                .chancedOutput(OreDictUnifier.get(OrePrefix.dust, byproductMaterial), 3300, 0)
                .duration(200)
                .buildAndRegister();

//        RecipeMaps.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder()
//                .input(crushedPrefix, material)
//                .outputs(crushedCentrifugedOre,
//                        OreDictUnifier.get(OrePrefix.dustTiny, GTUtility.selectItemInList(1, material, property.getOreByProducts(), Material.class), property.getByProductMultiplier() * 3),
//                        OreDictUnifier.get(OrePrefix.dust, Materials.Stone))
//                .buildAndRegister();
    }





}
