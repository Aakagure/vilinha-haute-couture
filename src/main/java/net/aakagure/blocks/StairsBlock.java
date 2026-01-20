package net.aakagure.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class StairsBlock extends net.minecraft.block.StairsBlock {

    // Construtor principal usado pelo ModBlocks
    public StairsBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
    }

    // Construtor auxiliar caso o código original use apenas Settings
    // (Assume um bloco base padrão, como Pedra, para propriedades de explosão/física herdadas)
    public StairsBlock(Settings settings) {
        super(Blocks.STONE.getDefaultState(), settings);
    }
}