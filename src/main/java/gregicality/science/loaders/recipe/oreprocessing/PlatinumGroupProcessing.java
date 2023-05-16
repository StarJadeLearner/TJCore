package gregicality.science.loaders.recipe.oreprocessing;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregicality.science.api.recipes.GCYSRecipeMaps.*;
import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tjcore.common.recipes.recipemaps.TJRecipeMaps.ROASTING_RECIPES;

/**
 * The Platinum Process
 *
 * <p>
 * Produces Platinum Group Metals from Platinum Group Sludge
 * </p>
 *
 * <p>Main Products: Platinum, Palladium, Rhodium, Ruthenium, Iridium, Osmium</p>
 * <p>Side Products: Gold, Silicon Dioxide</p>
 *
 * <p>4.8 PGS -> 1 Platinum</p>
 * <p>8 PGS -> 1 Palladium</p>
 * <p>18 PGS -> 1 Rhodium</p>
 * <p>18 PGS -> 1 Ruthenium</p>
 * <p>18 PGS -> 1 Iridium</p>
 * <p>36 PGS -> 1 Osmium</p>
 */
public class PlatinumGroupProcessing {

    public static void init() {
        sludge();
        platinum();
        palladium();
        ruthenium();
        rhodium();
        iridium();
        osmium();
        removeGTCERecipes();



    }

    private static void removeGTCERecipes() {
        // Remove Platinum Group Sludge -> Everything
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.CENTRIFUGE_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, PlatinumGroupSludge, 6)},
                new FluidStack[]{AquaRegia.getFluid(1200)});

        // Remove Raw Platinum -> Platinum
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ELECTROLYZER_RECIPES, OreDictUnifier.get(dust, PlatinumRaw, 3));

        // Remove Raw Palladium -> Palladium
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, PalladiumRaw, 5)},
                new FluidStack[]{HydrochloricAcid.getFluid(1000)});
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.LARGE_CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, PalladiumRaw, 5)},
                new FluidStack[]{HydrochloricAcid.getFluid(1000)});

        // Remove Inert Metal Residue -> Ruthenium Tetroxide + Rhodium Sulfate + Hydrogen
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, InertMetalMixture, 6)},
                new FluidStack[]{SulfuricAcid.getFluid(1500)});
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.LARGE_CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, InertMetalMixture, 6)},
                new FluidStack[]{SulfuricAcid.getFluid(1500)});

        // Remove Rhodium Sulfate -> Rhodium
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ELECTROLYZER_RECIPES, RhodiumSulfate.getFluid(1000));

        // Remove Acidic Osmium Solution -> Osmium Tetroxide
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.DISTILLATION_RECIPES, AcidicOsmiumSolution.getFluid(2000));
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.DISTILLERY_RECIPES,
                new ItemStack[]{IntCircuitIngredient.getIntegratedCircuit(1)},
                new FluidStack[]{AcidicOsmiumSolution.getFluid(400)});
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.DISTILLERY_RECIPES,
                new ItemStack[]{IntCircuitIngredient.getIntegratedCircuit(2)},
                new FluidStack[]{AcidicOsmiumSolution.getFluid(400)});

        // Remove Osmium Tetroxide -> Osmium
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, OsmiumTetroxide, 5)},
                new FluidStack[]{Hydrogen.getFluid(8000)});
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.LARGE_CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, OsmiumTetroxide, 5)},
                new FluidStack[]{Hydrogen.getFluid(8000)});

        // Remove Rarest Metal Mixture -> Iridium Residue and Osmium Solution
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.LARGE_CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, RarestMetalMixture, 7)},
                new FluidStack[]{HydrochloricAcid.getFluid(4000)});

        // Remove Iridium Metal Residue -> Iridium Chloride
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.CENTRIFUGE_RECIPES, OreDictUnifier.get(dust, IridiumMetalResidue, 5));
    }

    private static void sludge() {

        ROASTING_RECIPES.recipeBuilder()
                .input(dust,Braggite,4)
                .fluidInputs(Oxygen.getFluid(16000))
                .fluidOutputs(SulfurDioxide.getFluid(8000))
                .output(dust, RoastedBraggite, 4)
                .blastFurnaceTemp(3600)
                .duration(50).EUt(VA[HV])
                .buildAndRegister();
        //Roasted Braggite -> Nickel Free Braggite
        HIGH_TEMP_REACTOR_RECIPES.recipeBuilder()
                .input(dust, RoastedBraggite, 5)
                .fluidInputs(CarbonMonoxide.getFluid(4000))
                .output(dust,NickelDepletedBraggite,4)
                .fluidOutputs(NickelCarbonyl.getFluid(1000))
                .blastFurnaceTemp(3600)
                .duration(50).EUt(VA[EV])
                .buildAndRegister();
        //Nickel Carboynl -> Nickel
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(NickelCarbonyl.getFluid(1000))
                .output(dust, Nickel, 1)
                .fluidOutputs(CarbonMonoxide.getFluid(4000))
                .duration(50).EUt(VA[HV])
                .buildAndRegister();
        //Depleted Braggite -> Braggite Solution
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,NickelDepletedBraggite, 4)
                .fluidInputs(NitricAcid.getFluid(7000))
                .fluidInputs(HydrochloricAcid.getFluid(11000))
                .fluidOutputs(BraggiteSolution.getFluid(9000))
                .duration(50).EUt(VA[HV])

        .buildAndRegister();
    }

    private static void platinum() {

    }

    private static void palladium() {
         // HCO2CH3 + H2O -> HCOOH + CH3OH
        HIGH_TEMP_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(MethylFormate.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(FormicAcid.getFluid(1000))
                .fluidOutputs(Methanol.getFluid(1000))
                .blastFurnaceTemp(353)
                .duration(50).EUt(VA[LV]).buildAndRegister();

    }

    private static void ruthenium() {
        // CH4 + 8Cl -> CCl4 + 4HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Methane.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(8000))
                .notConsumable(new IntCircuitIngredient(2))
                .fluidOutputs(CarbonTetrachloride.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(4000))
                .duration(80).EUt(VA[LV]).buildAndRegister();

        // 2RhRu + 2CCl4 + 3H2SO4 -> 2RuCl3 + Rh2(SO4)3 + 2HCl + CH4 + C (C lost)
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, InertMetalMixture, 6)
                .fluidInputs(CarbonTetrachloride.getFluid(2000))
                .fluidInputs(SulfuricAcid.getFluid(3000))
                .output(dust, RutheniumChloride, 8)
                .fluidOutputs(RhodiumSulfate.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(Methane.getFluid(1000))
                .duration(100).EUt(VA[EV]).buildAndRegister();

        // RuCl3 + 2Na2O2 + Cl -> RuO4 + 4NaCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, RutheniumChloride, 4)
                .input(dust, SodiumPeroxide, 8)
                .fluidInputs(Chlorine.getFluid(1000))
                .output(dust, RutheniumTetroxide, 5)
                .output(dust, Salt, 8)
                .duration(200).EUt(VA[HV]).buildAndRegister();

        // proceed to CEu's Ruthenium Tetroxide + Carbon recipe
    }

    private static void rhodium() {
        // Rh2(SO4)3 + 3H2O -> Rh2O3 + 3H2SO4
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(RhodiumSulfate.getFluid(1000))
                .fluidInputs(Water.getFluid(3000))
                .output(dust, RhodiumOxide, 5)
                .fluidOutputs(SulfuricAcid.getFluid(3000))
                .duration(200).EUt(VA[HV]).buildAndRegister();

        // Proceed to electrolyze Rhodium Oxide
    }

    private static void iridium() {
        // NaCl + 3H2O -> NaClO3 + 6H
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, Salt, 2)
                .fluidInputs(Water.getFluid(3000))
                .notConsumable(new IntCircuitIngredient(2))
                .output(dust, SodiumChlorate, 5)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(100).EUt(VA[MV]).buildAndRegister();

        // Chemical Oxygen Generator
        // NaClO3 -> NaCl + 3O
        DRYER_RECIPES.recipeBuilder()
                .input(dust, SodiumChlorate, 5)
                .output(dust, Salt, 2)
                .fluidOutputs(Oxygen.getFluid(3000))
                .duration(100).EUt(60).buildAndRegister();

        // Ir2Os + 2NaClO3 + O -> Ir2O3 + OsO4 + 2NaCl
        ROASTING_RECIPES.recipeBuilder()
                .input(dust, RarestMetalMixture, 12)
                .input(dust, SodiumChlorate, 10)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, IridiumMetalResidue, 5)
                .output(dust, OsmiumTetroxide, 5)
                .output(dust, Salt, 4)
                .blastFurnaceTemp(1304)
                .duration(200).EUt(VA[IV]).buildAndRegister();

        // Ir2O3 + 6HCl -> 2IrCl3 + 3H2O
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, IridiumMetalResidue, 5)
                .fluidInputs(HydrochloricAcid.getFluid(6000))
                .output(dust, IridiumChloride, 8)
                .fluidOutputs(Water.getFluid(3000))
                .duration(100).EUt(240).buildAndRegister();

        // Proceed to CEu reaction of Iridium Chloride to Iridium
    }

    private static void osmium() {
        // S + 2Cl -> SCl2
        CRYOGENIC_REACTOR_RECIPES.recipeBuilder()
                .input(dust, Sulfur)
                .fluidInputs(Chlorine.getFluid(2000))
                .fluidOutputs(SulfurDichloride.getFluid(1000))
                .temperature(242)
                .duration(80).EUt(120).buildAndRegister();

        // SO3 + SCl2 -> SOCl2 + SO2
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SulfurTrioxide.getFluid(1000))
                .fluidInputs(SulfurDichloride.getFluid(1000))
                .fluidOutputs(ThionylChloride.getFluid(1000))
                .fluidOutputs(SulfurDioxide.getFluid(1000))
                .duration(100).EUt(VA[LV]).buildAndRegister();

        // OsO4 + 2SOCl2 -> OsCl4 + 2SO3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, OsmiumTetroxide, 5)
                .fluidInputs(ThionylChloride.getFluid(2000))
                .output(dust, OsmiumTetrachloride, 5)
                .fluidOutputs(SulfurTrioxide.getFluid(2000))
                .duration(100).EUt(240).buildAndRegister();

        // Proceed to electrolyze Osmium Tetrachloride
    }
}
