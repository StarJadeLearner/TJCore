package tjcore.api.metatileentity;

import net.minecraftforge.fluids.Fluid;

public interface IAtmosphereRecipeController {

    void setPressure(int newPressure);
    void setAtmosphere(Fluid newAtmosphere);
}
