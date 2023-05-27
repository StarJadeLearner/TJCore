package tjcore.common.recipes;

import net.minecraftforge.fml.common.Loader;
import tjcore.api.TJComponents;
import tjcore.common.recipes.chains.*;
import tjcore.common.recipes.circuits.CircuitRecipes;
import tjcore.common.recipes.compatrecipes.ArmorInfuserRecipes;
import tjcore.common.recipes.polymers.TJPolymers;

public class TJCoreRecipeHandler {
    public static void init() {
        TJFuelMaps.initFuelRecipes();
        MaterialOreChains.init();
        CircuitRecipes.registerCircuits();
        TJComponents.init();
        TJPolymers.registerPolymers();
        GTComponents.init();
        PetrochemRecipes.init();
        PhotoresistChains.init();
        LogisiticsRecipes.init();
        if (Loader.isModLoaded("draconicevolution")) {
            ArmorInfuserRecipes.register();
        }
        MaterialRecipes.register();
        Ores.RegisterOres();
        MachineRecipes.registerMachines();
        MultiblockHatches.init();
        CircuitryMaterialChains.init();
        BiochemChains.init();
    }
}
