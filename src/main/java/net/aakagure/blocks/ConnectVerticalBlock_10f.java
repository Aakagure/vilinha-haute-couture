package net.aakagure.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ConnectVerticalBlock_10f extends ConnectVerticalBlock {
    public ConnectVerticalBlock_10f(Settings settings) { super(settings); }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }
}