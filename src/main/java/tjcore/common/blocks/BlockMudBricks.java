package tjcore.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockMudBricks extends Block {
    public BlockMudBricks() {
        super(Material.CLAY);
        setTranslationKey("mud_bricks");
        setSoundType(SoundType.GROUND);
        setResistance(0.2f);
        setHardness(0.3f);
        setLightOpacity(0);
        setTickRandomly(false);
    }
}
