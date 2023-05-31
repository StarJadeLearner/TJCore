package tjcore.api.recipe;

import net.minecraftforge.fluids.Fluid;

public class AtmosphereRecipeInfo {
    private int minPressure;
    private int maxPressure;
    private Fluid atmosphere;

    public AtmosphereRecipeInfo(int minPressure, int maxPressure, Fluid atmosphere) {
        this.minPressure = minPressure;
        this.maxPressure = maxPressure;
        this.atmosphere = atmosphere;
    }

    @Override
    public String toString() {
        return minPressure + ":" + maxPressure + ":"  + atmosphere.getUnlocalizedName();
    }

    public Fluid getAtmosphere() {
        return atmosphere;
    }

    public int getMaxPressure() {
        return maxPressure;
    }

    public int getMinPressure() {
        return minPressure;
    }
}
