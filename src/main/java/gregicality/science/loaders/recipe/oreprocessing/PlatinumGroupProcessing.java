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
import static tjcore.api.material.TJMaterials.*;
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
        braggite();
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

    private static void braggite() {

        ROASTING_RECIPES.recipeBuilder()
                .input(dust,Braggite,4)
                .fluidInputs(Oxygen.getFluid(16000))
                .fluidOutputs(SulfurDioxide.getFluid(8000))
                .output(dust, RoastedBraggite, 4)
                .blastFurnaceTemp(3600)
                .duration(320).EUt(VA[HV])
                .buildAndRegister();
        //Roasted Braggite -> Nickel Free Braggite
        HIGH_TEMP_REACTOR_RECIPES.recipeBuilder()
                .input(dust, RoastedBraggite, 5)
                .fluidInputs(CarbonMonoxide.getFluid(4000))
                .output(dust,NickelDepletedBraggite,4)
                .fluidOutputs(NickelCarbonyl.getFluid(1000))
                .blastFurnaceTemp(3600)
                .duration(160).EUt(VA[HV])
                .buildAndRegister();
        //Nickel Carboynl -> Nickel
        HIGH_TEMP_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(NickelCarbonyl.getFluid(1000))
                .output(dust, Nickel, 1)
                .fluidOutputs(CarbonMonoxide.getFluid(4000))
                .duration(80).EUt(VA[HV])
                .blastFurnaceTemp(3600)
                .buildAndRegister();
        //Depleted Braggite -> Braggite Solution
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,NickelDepletedBraggite, 4)
                .fluidInputs(NitricAcid.getFluid(7000))
                .fluidInputs(HydrochloricAcid.getFluid(11000))
                .fluidOutputs(BraggiteSolution.getFluid(9000))
                .duration(480).EUt(VA[HV])
                .buildAndRegister();
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, AmmoniumChloride,15)
                .fluidInputs(BraggiteSolution.getFluid(9000))
                .output(dust, AmmoniumHexachloroplatinate, 15)
                .fluidOutputs(PlatinumDepletedBraggiteSolution.getFluid(7500))
                .duration(480).EUt(VA[HV])
                .buildAndRegister();
        LARGE_DRYER_RECIPES.recipeBuilder()
                .input(dust, AmmoniumChloride,5)
                .fluidInputs(PlatinumDepletedBraggiteSolution.getFluid(7500))
                .output(dust, AmmoniumHexachloropalladate,5)
                .output(dust,  InsolublePlatinumGroupResidue,1)
                .fluidOutputs(HydrochloricAcid.getFluid(1500))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .duration(480).EUt(VA[EV])
                .buildAndRegister();
//        .buildAndRegister();
    }

    private static void platinum() {
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,AmmoniumHexachloroplatinate,10)
                .fluidInputs(Hydrogen.getFluid(4000))
                .output(dust, Platinum, 1)
                .fluidOutputs(Ammonia.getFluid(2000))
                .fluidOutputs(HydrochloricAcid.getFluid(6000))
                .duration(240).EUt(VA[IV])
                .buildAndRegister();
    }

    private static void palladium() {
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,AmmoniumHexachloropalladate,10)
                .fluidInputs(Ammonia.getFluid(6000))
                .output(dust, TetraamminePalladiumDichlroide,10)
                .output(dust, AmmoniumChloride,20)
                .duration(480).EUt(VA[EV])
                .buildAndRegister();
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,TetraamminePalladiumDichlroide,10)
                .fluidInputs(Hydrazine.getFluid(500))
                .output(dust, Palladium,1)
                .output(dust, AmmoniumChloride,10)
                .duration(240).EUt(VA[IV])
                .buildAndRegister();




    }

    private static void ruthenium() {
        BLAST_RECIPES.recipeBuilder()
                .input(dust, RhodiumDepletedPlatinumGroupResidue,1)
                .input(dust, SodiumPeroxide,17)
                .output(dust, RhodiumRutheniumOsmiumDepletedPlatinumGroupResidue, 1)
                .fluidOutputs(SodiumRuthenatePerosmateSolution.getFluid(1000))
                .duration(480).EUt(VA[HV])
                .buildAndRegister();
        CRACKING_RECIPES.recipeBuilder()
                .fluidInputs(SodiumRuthenatePerosmateSolution.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(3000))
                .fluidOutputs(RutheniumOsmiumTetroxidesSolution.getFluid(1000))
                .duration(480).EUt(VA[HV])
                .buildAndRegister();
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(RutheniumOsmiumTetroxidesSolution.getFluid(100))
                .fluidInputs(HydrochloricAcid.getFluid(4600))
                .fluidOutputs(ChlororuthenicAcidOsmiumTetroxideSolution.getFluid(3100))
                .duration(480).EUt(VA[EV])
                .buildAndRegister();
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(ChlororuthenicAcidOsmiumTetroxideSolution.getFluid(31000))
                .output(dust, ImpureOsmiumTetroxide,20) //OSMIUM CHAIN ->
                .fluidOutputs(ChlororuthenicAcid.getFluid(9000))
                .fluidOutputs(Chlorine.getFluid(12000))
                .fluidOutputs(Water.getFluid(22000))
                .duration(2000).EUt(VA[HV])
                .buildAndRegister();
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, AmmoniumChloride,10)
                .fluidInputs(ChlororuthenicAcid.getFluid(3000))
                .output(dust, AmmoniumHexachlororuthenate, 10)
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(Water.getFluid(2000))
                .duration(480).EUt(VA[EV])
                .buildAndRegister();
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, AmmoniumHexachlororuthenate,10)
                .fluidInputs(Hydrogen.getFluid(4000))
                .output(dust, Ruthenium, 1)
                .fluidOutputs(Ammonia.getFluid(2000))
                .fluidOutputs(HydrochloricAcid.getFluid(6000))
                .duration(240).EUt(VA[IV])
                .buildAndRegister();
    }

    private static void rhodium() {
        BLAST_RECIPES.recipeBuilder()
                .input(dust, InsolublePlatinumGroupResidue,9)
                .input(dust, SodiumBisulfate,42)
                .fluidInputs(Water.getFluid(7000))
                .blastFurnaceTemp(4500)
                .output(dust, RhodiumDepletedPlatinumGroupResidue, 1)
                .fluidOutputs(RhodiumSulfateSolution.getFluid(1000))
                .duration(480).EUt(VA[HV])
                .buildAndRegister();
        LARGE_DRYER_RECIPES.recipeBuilder()
                .input(dust, Zinc,6)
                .fluidInputs(RhodiumSulfateSolution.getFluid(1000))
                .output(dust, CrudeRhodium,2)
                .output(dust,  ZincSulfate,36)
                .output(dust,  SodiumHydroxide,18)
                .duration(240).EUt(VA[EV])
                .buildAndRegister();
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, CrudeRhodium,1)
                .input(dust, Salt,6)
                .output(dust, SodiumHexachlororhodate, 10)
                .fluidOutputs(Chlorine.getFluid(3000))
                .duration(240).EUt(VA[EV])
                .buildAndRegister();
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodiumHexachlororhodate,10)
                .input(dust, SodiumHydroxide,9)
                .fluidInputs(Hydrazine.getFluid(750))
                .output(dust, Rhodium, 1)
                .output(dust, Salt, 12)
                .fluidOutputs(Nitrogen.getFluid(1500))
                .fluidOutputs(Water.getFluid(3000))
                .duration(480).EUt(VA[IV])
                .buildAndRegister();

    }

    private static void iridium() {
    BLAST_RECIPES.recipeBuilder()
            .input(dust,RhodiumRutheniumOsmiumDepletedPlatinumGroupResidue,1)
            .input(dust,BariumPeroxide,12)
            .fluidInputs(HydrochloricAcid.getFluid(12000))
            .blastFurnaceTemp(4500)
            .output(dust,BariumOxide,8)
            .fluidOutputs(ChloroiridicAcid.getFluid(6000))
            .duration(480).EUt(VA[HV])
            .buildAndRegister();
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, AmmoniumChloride,10)
                .fluidInputs(ChloroiridicAcid.getFluid(3000))
                .output(dust,AmmoniumHexachloroiridate,10)
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(Water.getFluid(2000))
                .duration(240).EUt(VA[EV])
                .buildAndRegister();
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,AmmoniumHexachloroiridate,10)
                .input(dust, AscorbicAcid,16)
                .output(dust,Iridium,1)
                .output(dust,DehydroascorbicAcid,16)
                .fluidOutputs(Ammonia.getFluid(2000))
                .fluidOutputs(HydrochloricAcid.getFluid(6000))
                .duration(240).EUt(VA[IV])
                .buildAndRegister();
    }

    private static void osmium() {
        LARGE_DRYER_RECIPES.recipeBuilder()
                .input(dust, ImpureOsmiumTetroxide,10)
                .fluidInputs(CarbonTetrachloride.getFluid(100))
                .output(dust, OsmiumTetroxide,2)
                .output(dust,  Salt,34)
                .duration(240).EUt(VA[HV])
                .buildAndRegister();
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, OsmiumTetroxide,20)
                .input(dust, SodiumHydroxide,9)
                .fluidInputs(Ethanol.getFluid(1000))
                .output(dust,SodiumOsmate,20)
                .fluidOutputs(SodiumAcetate.getFluid(8000))
                .duration(480).EUt(VA[EV])
                .buildAndRegister();
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodiumOsmate,10)
                .input(dust, AmmoniumChloride,20)
                .output(dust,TetraammineOsmylChloride,10)
                .output(dust,Salt,4)
                .fluidOutputs(Water.getFluid(4000))
                .duration(480).EUt(VA[EV])
                .buildAndRegister();
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, TetraammineOsmylChloride,10)
                .fluidInputs(Hydrogen.getFluid(6000))
                .output(dust,Osmium,1)
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(Ammonia.getFluid(4000))
                .fluidOutputs(Water.getFluid(2000))
                .duration(240).EUt(VA[IV])
                .buildAndRegister();
    }
}
