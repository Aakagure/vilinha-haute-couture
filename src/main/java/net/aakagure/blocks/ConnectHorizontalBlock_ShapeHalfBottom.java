package net.aakagure.blocks;


import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
// Imports solicitados
import net.aakagure.blocks.util.ShapeUtil;

public class ConnectHorizontalBlock_ShapeHalfBottom extends ConnectHorizontalBlock {
    public ConnectHorizontalBlock_ShapeHalfBottom(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ShapeUtil.SHAPE_HALFBOTTOM;
    }
}
