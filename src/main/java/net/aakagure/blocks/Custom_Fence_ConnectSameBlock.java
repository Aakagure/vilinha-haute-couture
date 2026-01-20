package net.aakagure.blocks;

import net.minecraft.block.BlockState;

public class Custom_Fence_ConnectSameBlock extends Custom_Fence {
    public Custom_Fence_ConnectSameBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canConnect(BlockState neighbor) {
        // Só conecta se for exatamente este bloco
        return neighbor.isOf(this);
    }
}