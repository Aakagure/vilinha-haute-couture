package net.aakagure.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.aakagure.blocks.util.ShapeUtil;

public class ConnectNSEWBlock_ShapeHalfBottom extends ConnectNSEWBlock {
    public ConnectNSEWBlock_ShapeHalfBottom(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ShapeUtil.SHAPE_HALFBOTTOM;
    }
}