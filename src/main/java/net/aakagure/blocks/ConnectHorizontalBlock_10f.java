package net.aakagure.blocks;


import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ConnectHorizontalBlock_10f extends ConnectHorizontalBlock {
    public ConnectHorizontalBlock_10f(Settings settings) {
        super(settings);
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }
}
