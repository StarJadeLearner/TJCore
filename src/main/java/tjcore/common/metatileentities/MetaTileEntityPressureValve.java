package tjcore.common.metatileentities;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.FilteredItemHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import tjcore.api.metatileentity.IAtmosphereRecipeController;
import tjcore.api.metatileentity.TJMultiblockAbilities;
import tjcore.common.metatileentities.multi.electric.MetaTileEntityCVDUnit;

import java.util.List;
import java.util.function.Predicate;

import static gregtech.api.capability.GregtechDataCodes.STRUCTURE_FORMED;

public class MetaTileEntityPressureValve extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IFluidTank> {

    private final FluidTank fluidTank;
    private int pressure;
    private boolean vacuumMode;
    private boolean evacuate;

    public MetaTileEntityPressureValve(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.fluidTank = new FluidTank(getInventorySize());
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPressureValve(metaTileEntityId, getTier());
    }

    //TODO: Custom Overlay
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (shouldRenderOverlay()) {
            Textures.HATCH_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createTankUI(fluidTank, getMetaFullName(), entityPlayer).build(getHolder(), entityPlayer);
    }

    public ModularUI.Builder createTankUI(IFluidTank fluidTank, String title, EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.defaultBuilder();
        builder.image(7, 16, 81, 55, GuiTextures.DISPLAY);
        TankWidget tankWidget = new TankWidget(fluidTank, 69, 52, 18, 18)
                .setHideTooltip(true).setAlwaysShowFull(true);
        builder.widget(tankWidget);
        builder.label(11, 20, "Pressure: ", 0xFFFFFF);
        builder.dynamicLabel(11, 30, () -> Integer.toString(pressure), 0xFFFFFF);
        builder.dynamicLabel(11, 40, () -> isVacuumMode() ? "Vacuum" : tankWidget.getFluidLocalizedName(), 0xFFFFFF);
        builder.dynamicLabel(11, 40, () -> isEvacuate() ? "Evacuating..." : "", 0xFFFFFF);
        builder.widget(new ToggleButtonWidget(111, 16, 18, 18,
                GuiTextures.BUTTON_VOID, this::isVacuumMode, this::setVacuumMode)
                .setTooltipText("tjcore.gui.vacuum_mode.tooltip")
                .shouldUseBaseBackground());
        builder.widget(new ToggleButtonWidget(131, 16, 18, 18,
                GuiTextures.BUTTON_FLUID_OUTPUT, this::isEvacuate, this::setEvacuate)
                .setTooltipText("tjcore.gui.evacuate.tooltip")
                .shouldUseBaseBackground());
        return builder.label(6, 6, title)
                .widget(new FluidContainerSlotWidget(importItems, 0, 90, 16, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.IN_SLOT_OVERLAY))
                .widget(new ImageWidget(91, 36, 14, 15, GuiTextures.TANK_ICON))
                .widget(new SlotWidget(exportItems, 0, 90, 53, true, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.OUT_SLOT_OVERLAY))
                .bindPlayerInventory(entityPlayer.inventory);
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            fillContainerFromInternalTank();
            fillInternalTankFromFluidContainer();
            if(vacuumMode) {
                if (evacuate && getOffsetTimer() % 20 == 0) {
                    int diff = getInventorySize() + pressure;
                    if (diff > 0) pressure = Math.min(pressure - (Math.min(diff, 250)), 0);
                }
            } else {
                pressure = fluidTank.getFluidAmount();
            }
            if (getController() instanceof IAtmosphereRecipeController) {
                ((IAtmosphereRecipeController) getController()).setPressure(pressure);
                ((IAtmosphereRecipeController) getController()).setAtmosphere(fluidTank.getFluid() != null ? fluidTank.getFluid().getFluid() : null);
            }
            writeCustomData(1, buf -> buf.writeInt(pressure));
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == 1) {
            pressure = buf.readInt();
        }
    }

    private int getInventorySize() {
        return 1000 * (1 << (getTier() - 4));
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, fluidTank);
    }

    @Override
    protected FluidTankList createExportFluidHandler() {
        return new FluidTankList(false, fluidTank);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new FilteredItemHandler(1).setFillPredicate((itemStack) ->
              !vacuumMode && FilteredItemHandler.getCapabilityFilter(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).test(itemStack)
        );
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public MultiblockAbility<IFluidTank> getAbility() {
        return TJMultiblockAbilities.ATMOSPHERE;
    }

    @Override
    public void registerAbilities(List<IFluidTank> list) {
        list.addAll(this.importFluids.getFluidTanks());
    }

    public void setVacuumMode(boolean vacuumMode) {
        if (getController() instanceof IAtmosphereRecipeController && getController() instanceof RecipeMapMultiblockController) {
            ((RecipeMapMultiblockController) getController()).getRecipeMapWorkable().invalidate();
        }
        pressure = 0;
        fluidTank.drain(fluidTank.getFluidAmount(), true);
        this.vacuumMode = vacuumMode;
    }

    public boolean isVacuumMode() {
        return vacuumMode;
    }

    public void setEvacuate(boolean evacuate) {
        this.evacuate = evacuate;
    }

    public boolean isEvacuate() {
        return evacuate;
    }

}
