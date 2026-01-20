package net.aakagure.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ConnectHorizontalBlock_ConnectUD_08f extends ConnectHorizontalBlock_ConnectUD {
    public ConnectHorizontalBlock_ConnectUD_08f(Settings settings) { super(settings); }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 0.8F;
    }
}