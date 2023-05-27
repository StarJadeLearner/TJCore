package tjcore.common.blocks;

import gregtech.api.block.IStateHarvestLevel;
import gregtech.api.block.VariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import tjcore.api.block.ITurbineBladeStats;

import javax.annotation.Nonnull;

public class BlockPistonHead extends VariantBlock<BlockPistonHead.PistonHeadType> {

    public BlockPistonHead() {
        super(Material.IRON);
        setTranslationKey("piston_head");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setDefaultState(getState(PistonHeadType.GALVANIZED_STEEL_PISTON_HEAD));
    }

    public enum PistonHeadType implements IStringSerializable, IStateHarvestLevel, ITurbineBladeStats {

        GALVANIZED_STEEL_PISTON_HEAD("galvanized_piston_head", 50,0, 2),
        ALUMINUM_PISTON_HEAD("aluminium_piston_head", 100,1,3),
        STAINLESS_PISTON_HEAD("stainless_piston_head", 150,2,3),
        TITANIUM_PISTON_HEAD("titanium_piston_head", 250,3, 4),
        TUNGSTENSTEEL_PISTON_HEAD("tungstensteel_piston_head", 400,4, 4);

        private final String name;
        private final int tier;
        private final int rotationCap;
        private final int harvestLevel;

        PistonHeadType(String name, int rotationCap, int tier, int harvestLevel) {
            this.rotationCap = rotationCap;
            this.tier = tier;
            this.name = name;
            this.harvestLevel = harvestLevel;
        }

        @Nonnull
        @Override
        public String getName() {
            return this.name;
        }

        public int getTier() {
            return tier;
        }

        public int getRotationCap() {
            return rotationCap;
        }

        @Override
        public int getHarvestLevel(IBlockState state) {
            return harvestLevel;
        }

        @Override
        public String getHarvestTool(IBlockState state) {
            return "wrench";
        }
    }
}
