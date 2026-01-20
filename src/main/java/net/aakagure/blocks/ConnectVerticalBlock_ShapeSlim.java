package net.aakagure.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.aakagure.blocks.util.ShapeUtil;

public class ConnectVerticalBlock_ShapeSlim extends ConnectVerticalBlock {
    public ConnectVerticalBlock_ShapeSlim(Settings settings) { super(settings); }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ShapeUtil.SHAPE_SLIM;
    }
}