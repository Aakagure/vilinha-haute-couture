package net.aakagure.blocks;

import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;

public class Custom_WindowFrameBlock extends StairsBlock {

    public Custom_WindowFrameBlock(Settings settings) {
        super(Blocks.GLASS.getDefaultState(), settings);
    }

    // Assim como o Slipper, herda a lógica de Stairs para fazer cantos (Inner/Outer)
    // Isso permite criar molduras de janelas que contornam a casa.
}