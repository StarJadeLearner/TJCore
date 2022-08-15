package TJCore.common.metaitem;


import TJCore.common.tools.TJToolMetaItem;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.ToolMetaItem;

import java.util.*;

public class TJMetaItems {

    public static final List<MetaItem<?>> ITEMS = MetaItem.getMetaItems();

    public static void init() {
        TJMetaItem items = new TJMetaItem();
        items.setRegistryName("TJMetaItem");

        TJToolMetaItem tools = new TJToolMetaItem();
        tools.setRegistryName("TJMetaTool");
    }

    //public static MetaItem<?>.MetaValueItem RESISTOR;
    //public static MetaItem<?>.MetaValueItem TRANSISTOR;
    //public static MetaItem<?>.MetaValueItem DIODE;
    //public static MetaItem<?>.MetaValueItem CAPACITOR;
    //public static MetaItem<?>.MetaValueItem INDUCTOR;

    //CIRCUITFRAMEWORK

    public static MetaItem<?>.MetaValueItem WETPHENOLICPULP;
    public static MetaItem<?>.MetaValueItem WETPRESSEDPHENOLICSUBSTRATE;

    public static MetaItem<?>.MetaValueItem UVEMITTER_A;
    public static MetaItem<?>.MetaValueItem UVEMITTER_B;
    public static MetaItem<?>.MetaValueItem UVEMITTER_C;
    public static MetaItem<?>.MetaValueItem UVEMITTER_D;
    public static MetaItem<?>.MetaValueItem UVEMITTER_E;
    //SMDs

    public static MetaItem<?>.MetaValueItem SMD_RESISTOR_1;
    public static MetaItem<?>.MetaValueItem SMD_RESISTOR_2;
    public static MetaItem<?>.MetaValueItem SMD_RESISTOR_3;
    public static MetaItem<?>.MetaValueItem SMD_RESISTOR_4;
    public static MetaItem<?>.MetaValueItem SMD_RESISTOR_5;

    public static MetaItem<?>.MetaValueItem SMD_TRANSISTOR_1;
    public static MetaItem<?>.MetaValueItem SMD_TRANSISTOR_2;
    public static MetaItem<?>.MetaValueItem SMD_TRANSISTOR_3;
    public static MetaItem<?>.MetaValueItem SMD_TRANSISTOR_4;
    public static MetaItem<?>.MetaValueItem SMD_TRANSISTOR_5;

    public static MetaItem<?>.MetaValueItem SMD_DIODE_1;
    public static MetaItem<?>.MetaValueItem SMD_DIODE_2;
    public static MetaItem<?>.MetaValueItem SMD_DIODE_3;
    public static MetaItem<?>.MetaValueItem SMD_DIODE_4;
    public static MetaItem<?>.MetaValueItem SMD_DIODE_5;

    public static MetaItem<?>.MetaValueItem SMD_CAPACITOR_1;
    public static MetaItem<?>.MetaValueItem SMD_CAPACITOR_2;
    public static MetaItem<?>.MetaValueItem SMD_CAPACITOR_3;
    public static MetaItem<?>.MetaValueItem SMD_CAPACITOR_4;
    public static MetaItem<?>.MetaValueItem SMD_CAPACITOR_5;

    public static MetaItem<?>.MetaValueItem SMD_INDUCTOR_1;
    public static MetaItem<?>.MetaValueItem SMD_INDUCTOR_2;
    public static MetaItem<?>.MetaValueItem SMD_INDUCTOR_3;
    public static MetaItem<?>.MetaValueItem SMD_INDUCTOR_4;
    public static MetaItem<?>.MetaValueItem SMD_INDUCTOR_5;

    //Boules + Wafers

    public static MetaItem<?>.MetaValueItem SILICON_BOULE;
    public static MetaItem<?>.MetaValueItem ANTIMONY_DOPED_SILICON_BOULE;
    public static MetaItem<?>.MetaValueItem BORON_DOPED_SILICON_BOULE;
    public static MetaItem<?>.MetaValueItem GALLIUM_ARSENIDE_BOULE;
    public static MetaItem<?>.MetaValueItem SILVER_GALLIUM_SELENIDE_BOULE;

    public static MetaItem<?>.MetaValueItem SILICON_WAFER;
    public static MetaItem<?>.MetaValueItem ANTIMONY_DOPED_SILICON_WAFER;
    public static MetaItem<?>.MetaValueItem BORON_DOPED_SILICON_WAFER;
    public static MetaItem<?>.MetaValueItem GALLIUM_ARSENIDE_WAFER;
    public static MetaItem<?>.MetaValueItem SILVER_GALLIUM_SELENIDE_WAFER;

    public static MetaItem<?>.MetaValueItem LAYERED_SILICON_WAFER;
    public static MetaItem<?>.MetaValueItem LAYERED_ANTIMONY_DOPED_SILICON_WAFER;
    public static MetaItem<?>.MetaValueItem LAYERED_BORON_DOPED_SILICON_WAFER;
    public static MetaItem<?>.MetaValueItem LAYERED_GALLIUM_ARSENIDE_WAFER;
    public static MetaItem<?>.MetaValueItem LAYERED_SILVER_GALLIUM_SELENIDE_WAFER;

    public static MetaItem<?>.MetaValueItem PREPARED_SILICON_WAFER;
    public static MetaItem<?>.MetaValueItem PREPARED_ANTIMONY_DOPED_SILICON_WAFER;
    public static MetaItem<?>.MetaValueItem PREPARED_BORON_DOPED_SILICON_WAFER;
    public static MetaItem<?>.MetaValueItem PREPARED_GALLIUM_ARSENIDE_WAFER;
    public static MetaItem<?>.MetaValueItem PREPARED_SILVER_GALLIUM_SELENIDE_WAFER;

    public static MetaItem<?>.MetaValueItem INTEGRATED_WAFER_LITHOGRAPHY_PREP;
    public static MetaItem<?>.MetaValueItem MICRO_WAFER_LITHOGRAPHY_PREP;
    public static MetaItem<?>.MetaValueItem NANO_WAFER_LITHOGRAPHY_PREP;
    public static MetaItem<?>.MetaValueItem IMC_WAFER_LITHOGRAPHY_PREP;
    public static MetaItem<?>.MetaValueItem OPTICAL_WAFER_LITHOGRAPHY_PREP;

    public static MetaItem<?>.MetaValueItem PREBAKED_INTEGRATED_WAFER;
    public static MetaItem<?>.MetaValueItem PREBAKED_MICRO_WAFER;
    public static MetaItem<?>.MetaValueItem PREBAKED_NANO_WAFER;
    public static MetaItem<?>.MetaValueItem PREBAKED_IMC_WAFER;
    public static MetaItem<?>.MetaValueItem PREBAKED_OPTICAL_WAFER;

    public static MetaItem<?>.MetaValueItem TREATED_INTEGRATED_WAFER;
    public static MetaItem<?>.MetaValueItem TREATED_MICRO_WAFER;
    public static MetaItem<?>.MetaValueItem TREATED_NANO_WAFER;
    public static MetaItem<?>.MetaValueItem TREATED_IMC_WAFER;
    public static MetaItem<?>.MetaValueItem TREATED_OPTICAL_WAFER;

    public static MetaItem<?>.MetaValueItem RAW_INTEGRATED_WAFER;
    public static MetaItem<?>.MetaValueItem RAW_MICRO_WAFER;
    public static MetaItem<?>.MetaValueItem RAW_NANO_WAFER;
    public static MetaItem<?>.MetaValueItem RAW_IMC_WAFER;
    public static MetaItem<?>.MetaValueItem RAW_OPTICAL_WAFER;

    public static MetaItem<?>.MetaValueItem BAKED_INTEGRATED_WAFER;
    public static MetaItem<?>.MetaValueItem BAKED_MICRO_WAFER;
    public static MetaItem<?>.MetaValueItem BAKED_NANO_WAFER;
    public static MetaItem<?>.MetaValueItem BAKED_IMC_WAFER;
    public static MetaItem<?>.MetaValueItem BAKED_OPTICAL_WAFER;

    public static MetaItem<?>.MetaValueItem INTEGRATED_WAFER;
    public static MetaItem<?>.MetaValueItem MICRO_WAFER;
    public static MetaItem<?>.MetaValueItem NANO_WAFER;
    public static MetaItem<?>.MetaValueItem IMC_WAFER;
    public static MetaItem<?>.MetaValueItem OPTICAL_WAFER;

    public static MetaItem<?>.MetaValueItem ETCHED_INTEGRATED_WAFER;
    public static MetaItem<?>.MetaValueItem ETCHED_MICRO_WAFER;
    public static MetaItem<?>.MetaValueItem ETCHED_NANO_WAFER;
    public static MetaItem<?>.MetaValueItem ETCHED_IMC_WAFER;
    public static MetaItem<?>.MetaValueItem ETCHED_OPTICAL_WAFER;

    public static MetaItem<?>.MetaValueItem INTEGRATED_CHIP;
    public static MetaItem<?>.MetaValueItem MICRO_CHIP;
    public static MetaItem<?>.MetaValueItem NANO_CHIP;
    public static MetaItem<?>.MetaValueItem IMC_CHIP;
    public static MetaItem<?>.MetaValueItem OPTICAL_CHIP;

    public static MetaItem<?>.MetaValueItem INTEGRATED_HARD_MASK;
    public static MetaItem<?>.MetaValueItem MICRO_HARD_MASK;
    public static MetaItem<?>.MetaValueItem NANO_HARD_MASK;
    public static MetaItem<?>.MetaValueItem IMC_HARD_MASK;
    public static MetaItem<?>.MetaValueItem OPTICAL_HARD_MASK;

    //Circuit Boards
    public static MetaItem<?>.MetaValueItem PRIMITIVE_PREBOARD;
    public static MetaItem<?>.MetaValueItem ELECTRONIC_PREBOARD;
    public static MetaItem<?>.MetaValueItem INTEGRATED_PREBOARD;
    public static MetaItem<?>.MetaValueItem MICRO_PREBOARD;
    public static MetaItem<?>.MetaValueItem NANO_PREBOARD;
    public static MetaItem<?>.MetaValueItem IMC_PREBOARD;
    public static MetaItem<?>.MetaValueItem OPTICAL_PREBOARD;
    public static MetaItem<?>.MetaValueItem CRYSTAL_PREBOARD;
    public static MetaItem<?>.MetaValueItem BIOWARE_PREBOARD;
    public static MetaItem<?>.MetaValueItem WETWARE_PREBOARD;
    public static MetaItem<?>.MetaValueItem QUANTUM_PREBOARD;
    public static MetaItem<?>.MetaValueItem EXOTIC_PREBOARD;
    public static MetaItem<?>.MetaValueItem COSMIC_PREBOARD;
    public static MetaItem<?>.MetaValueItem SUPRA_PREBOARD;

    public static MetaItem<?>.MetaValueItem PRIMITIVE_BOARD;
    public static MetaItem<?>.MetaValueItem ELECTRONIC_BOARD;
    public static MetaItem<?>.MetaValueItem INTEGRATED_BOARD;
    public static MetaItem<?>.MetaValueItem MICRO_BOARD;
    public static MetaItem<?>.MetaValueItem NANO_BOARD;
    public static MetaItem<?>.MetaValueItem IMC_BOARD;
    public static MetaItem<?>.MetaValueItem OPTICAL_BOARD;
    public static MetaItem<?>.MetaValueItem CRYSTAL_BOARD;
    public static MetaItem<?>.MetaValueItem BIOWARE_BOARD;
    public static MetaItem<?>.MetaValueItem WETWARE_BOARD;
    public static MetaItem<?>.MetaValueItem QUANTUM_BOARD;
    public static MetaItem<?>.MetaValueItem EXOTIC_BOARD;
    public static MetaItem<?>.MetaValueItem COSMIC_BOARD;
    public static MetaItem<?>.MetaValueItem SUPRA_BOARD;

    //Circuits
    public static MetaItem<?>.MetaValueItem PRIMITIVE_ASSEMBLY_ULV;
    public static MetaItem<?>.MetaValueItem PRIMITIVE_COMPUTER_LV;
    public static MetaItem<?>.MetaValueItem PRIMITIVE_MAINFRAME_MV;

    public static MetaItem<?>.MetaValueItem ELECTRONIC_PROCESSOR_ULV;
    public static MetaItem<?>.MetaValueItem ELECTRONIC_ASSEMBLY_LV;
    public static MetaItem<?>.MetaValueItem ELECTRONIC_COMPUTER_MV;
    public static MetaItem<?>.MetaValueItem ELECTRONIC_MAINFRAME_HV;

    public static MetaItem<?>.MetaValueItem INTEGRATED_PROCESSOR_LV;
    public static MetaItem<?>.MetaValueItem INTEGRATED_ASSEMBLY_MV;
    public static MetaItem<?>.MetaValueItem INTEGRATED_COMPUTER_HV;
    public static MetaItem<?>.MetaValueItem INTEGRATED_MAINFRAME_EV;

    public static MetaItem<?>.MetaValueItem MICRO_PROCESSOR_MV;
    public static MetaItem<?>.MetaValueItem MICRO_ASSEMBLY_HV;
    public static MetaItem<?>.MetaValueItem MICRO_COMPUTER_EV;
    public static MetaItem<?>.MetaValueItem MICRO_MAINFRAME_IV;

    public static MetaItem<?>.MetaValueItem NANO_PROCESSOR_HV;
    public static MetaItem<?>.MetaValueItem NANO_ASSEMBLY_EV;
    public static MetaItem<?>.MetaValueItem NANO_COMPUTER_IV;
    public static MetaItem<?>.MetaValueItem NANO_MAINFRAME_LUV;

    public static MetaItem<?>.MetaValueItem IMC_PROCESSOR_EV;
    public static MetaItem<?>.MetaValueItem IMC_ASSEMBLY_IV;
    public static MetaItem<?>.MetaValueItem IMC_COMPUTER_LUV;
    public static MetaItem<?>.MetaValueItem IMC_MAINFRAME_ZPM;

    public static MetaItem<?>.MetaValueItem OPTICAL_PROCESSOR_IV;
    public static MetaItem<?>.MetaValueItem OPTICAL_ASSEMBLY_LUV;
    public static MetaItem<?>.MetaValueItem OPTICAL_COMPUTER_ZPM;
    public static MetaItem<?>.MetaValueItem OPTICAL_MAINFRAME_UV;

    public static MetaItem<?>.MetaValueItem CRYSTAL_PROCESSOR_LUV;
    public static MetaItem<?>.MetaValueItem CRYSTAL_ASSEMBLY_ZPM;
    public static MetaItem<?>.MetaValueItem CRYSTAL_COMPUTER_UV;
    public static MetaItem<?>.MetaValueItem CRYSTAL_MAINFRAME_UHV;

    public static MetaItem<?>.MetaValueItem BIOWARE_PROCESSOR_ZPM;
    public static MetaItem<?>.MetaValueItem BIOWARE_ASSEMBLY_UV;
    public static MetaItem<?>.MetaValueItem BIOWARE_COMPUTER_UHV;
    public static MetaItem<?>.MetaValueItem BIOWARE_MAINFRAME_UEV;

    public static MetaItem<?>.MetaValueItem WETWARE_PROCESSOR_UV;
    public static MetaItem<?>.MetaValueItem WETWARE_ASSEMBLY_UHV;
    public static MetaItem<?>.MetaValueItem WETWARE_COMPUTER_UEV;
    public static MetaItem<?>.MetaValueItem WETWARE_MAINFRAME_UIV;

    // less tiers of smd, make them upgrading on a *random* pattern
    // have like 4 or 5 tiers of each smd max (giving us a chance to make them more complex on later tiers than just wire, plate, fluid)

    //
    public static ToolMetaItem<?>.MetaToolValueItem LITHOGRAPHY_MASK;

    //Biochem Processes

    public static MetaItem<?>.MetaValueItem TREATED_CPU_WAFER;
    public static MetaItem<?>.MetaValueItem TREATED_RAM_WAFER;
    public static MetaItem<?>.MetaValueItem TREATED_NANO_CPU_WAFER;
    public static MetaItem<?>.MetaValueItem TREATED_ULPIC_WAFER;
    //public static MetaItem<?>.MetaValueItem TREATED_CPU_WAFER;

}
