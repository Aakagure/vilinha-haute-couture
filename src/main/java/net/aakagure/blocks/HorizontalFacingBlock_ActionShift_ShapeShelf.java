package net.aakagure.blocks;

import net.aakagure.blocks.util.ShapeUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class HorizontalFacingBlock_ActionShift_ShapeShelf extends HorizontalFacingBlock_ActionShift {
    public HorizontalFacingBlock_ActionShift_ShapeShelf(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case SOUTH: return ShapeUtil.SHAPE_VSLAB_SOUTH;
            case WEST: return ShapeUtil.SHAPE_VSLAB_WEST;
            case EAST: return ShapeUtil.SHAPE_VSLAB_EAST;
            default: return ShapeUtil.SHAPE_VSLAB_NORTH;
        }
    }
}