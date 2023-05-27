package tjcore.common.recipes;

import gregtech.api.unification.material.Material;
import net.minecraftforge.fluids.Fluid;

import java.sql.Ref;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import static gregtech.api.unification.material.Materials.*;

public class TJFuelMaps {

    public static final HashMap<Fluid, TJFuelBurnStats> combustionFuels = new HashMap<>();
    public static final HashMap<Fluid, TJFuelBurnStats> steamTurbineFuels = new HashMap<>();
    public static final HashMap<Fluid, TJFuelBurnStats> gasTurbineFuels = new HashMap<>();

    public static void initFuelRecipes() {

        //Steam Turbine Fuels
        steamTurbineFuels.put(Steam.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 1 : (tier * 5),
                (tier) -> (int) Math.pow(2, (tier * 2) + 8)
        ));

        //Gas Turbine Fuels
        gasTurbineFuels.put(CoalGas.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 4.5))
        ));
        gasTurbineFuels.put(WoodGas.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 4.5))
        ));
        gasTurbineFuels.put(NaturalGas.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 4.25))
        ));
        gasTurbineFuels.put(SulfuricGas.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 4.25))
        ));
        gasTurbineFuels.put(RefineryGas.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 4))
        ));
        gasTurbineFuels.put(SulfuricNaphtha.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 3.5))
        ));
        gasTurbineFuels.put(LPG.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 3.5))
        ));

        gasTurbineFuels.put(Methane.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 3.25))
        ));
        gasTurbineFuels.put(Phenol.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, tier + 3.25)
        ));
        gasTurbineFuels.put(Benzene.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 3))
        ));
        gasTurbineFuels.put(Ethylene.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 3))
        ));
        gasTurbineFuels.put(Ethane.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 2.5))
        ));
        gasTurbineFuels.put(Propane.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 2.5))
        ));
        gasTurbineFuels.put(Propene.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 2.5))
        ));
        gasTurbineFuels.put(Butene.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 2.5))
        ));
        gasTurbineFuels.put(Butane.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 2.5))
        ));
        gasTurbineFuels.put(Butadiene.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 2.5))
        ));
        gasTurbineFuels.put(Nitrobenzene.getFluid(), new TJFuelBurnStats(
                (tier) -> tier == 0 ? 5 : (tier * 10),
                (tier) -> (int) Math.pow(4, (tier + 1.5))
        ));

        //Combustion Piston Fuels
        combustionFuels.put(RawOil.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(4, tier + 3)
        ));
        combustionFuels.put(OilLight.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(4, tier + 3)
        ));
        combustionFuels.put(SulfuricLightFuel.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(4, tier + 2.75)
        ));
        combustionFuels.put(Naphtha.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(4, tier + 2.75)
        ));
        combustionFuels.put(LightFuel.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(4, tier + 2.5)
        ));
        combustionFuels.put(Toluene.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(4, tier + 2.5)
        ));
        combustionFuels.put(Ethanol.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(4, tier + 2.25)
        ));
        combustionFuels.put(Methanol.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(4, tier + 2.25)
        ));
        combustionFuels.put(Octane.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(4, tier + 2.25)
        ));
        combustionFuels.put(BioDiesel.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(4, tier + 2)
        ));
        combustionFuels.put(Diesel.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(4, tier + 2)
        ));
        combustionFuels.put(CetaneBoostedDiesel.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(4, tier + 2)
        ));
        combustionFuels.put(Gasoline.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(2, tier + 4)
        ));
        combustionFuels.put(HighOctaneGasoline.getFluid(), new TJFuelBurnStats(
                (tier) -> (tier + 1) * 5,
                (tier) -> (int) Math.pow(2, tier + 4)
        ));
    }

    public static class TJFuelBurnStats {
        public final Function<Integer, Integer> duration;
        public final Function<Integer, Integer> quantity;



        public TJFuelBurnStats(Function<Integer, Integer> duration, Function<Integer, Integer> quantity) {
            this.duration = duration;
            this.quantity = quantity;
        }
    }
}
