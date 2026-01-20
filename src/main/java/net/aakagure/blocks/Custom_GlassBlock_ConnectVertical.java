package net.aakagure.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class Custom_GlassBlock_ConnectVertical extends ConnectVerticalBlock {

    public Custom_GlassBlock_ConnectVertical(Settings settings) {
        super(settings);
    }

    // Configurações de Vidro
    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this)) return true;
        return super.isSideInvisible(state, stateFrom, direction);
    }
}