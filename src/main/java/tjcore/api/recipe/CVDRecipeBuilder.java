package tjcore.api.recipe;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.BlastRecipeBuilder;
import gregtech.api.recipes.builders.FusionRecipeBuilder;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.unification.material.Material;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import net.minecraftforge.fluids.Fluid;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static gregtech.api.unification.material.Materials.Air;

public class CVDRecipeBuilder extends RecipeBuilder<CVDRecipeBuilder> {

    public CVDRecipeBuilder() {

    }

    public CVDRecipeBuilder(RecipeBuilder<CVDRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public CVDRecipeBuilder copy() {
        return new CVDRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@NotNull String key, @Nullable Object value) {
        if (key.equals(AtmosphereProperty.KEY)) {
            AtmosphereRecipeInfo info = (AtmosphereRecipeInfo) value;
            this.atmosphere(info.getMinPressure(), info.getMaxPressure(), info.getAtmosphere());
            return true;
        } else {
            return super.applyProperty(key, value);
        }
    }

    public CVDRecipeBuilder atmosphere(int minPressure, int maxPressure, Fluid fluid) {
        if (minPressure > maxPressure) {
            GTLog.logger.error("Minimum pressure cannot be greater than maximum pressure", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(AtmosphereProperty.getInstance(), new AtmosphereRecipeInfo(minPressure, maxPressure, fluid));
        return this;
    }

    public CVDRecipeBuilder atmosphere(int minPressure, int maxPressure) {
        if (minPressure > maxPressure) {
            GTLog.logger.error("Minimum pressure cannot be greater than maximum pressure", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        } else if (maxPressure > 0) {
            GTLog.logger.error("Recipes which need a vacuum must be at negative pressure", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(AtmosphereProperty.getInstance(), new AtmosphereRecipeInfo(minPressure, maxPressure, null));
        return this;
    }

    public AtmosphereRecipeInfo getAtmosphereInfo() {
        return this.recipePropertyStorage.getRecipePropertyValue(AtmosphereProperty.getInstance(), new AtmosphereRecipeInfo(0, 64000, Air.getFluid()));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(AtmosphereProperty.getInstance().getKey(), getAtmosphereInfo().toString())
                .toString();
    }
}
