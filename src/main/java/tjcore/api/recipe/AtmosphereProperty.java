package tjcore.api.recipe;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class AtmosphereProperty extends RecipeProperty<AtmosphereRecipeInfo> {

    public static final String KEY = "atmosphere";

    private static AtmosphereProperty INSTANCE;

    private AtmosphereProperty() {
        super(KEY, AtmosphereRecipeInfo.class);
    }

    public static AtmosphereProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AtmosphereProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object o) {
        minecraft.fontRenderer.drawString("Atmosphere: " +  I18n.format(castValue(o).getAtmosphere() == null ? "Vacuum" : castValue(o).getAtmosphere().getUnlocalizedName()), x, y - (20), color);
        minecraft.fontRenderer.drawString("Minimum Pressure: " + castValue(o).getMinPressure(), x, y - (10), color);
        minecraft.fontRenderer.drawString("Maximum Pressure: " + castValue(o).getMaxPressure(), x, y, color);
    }

    @Override
    public int getInfoHeight(Object value) {
        return 30;
    }
}
