package net.aakagure.blocks;

import net.aakagure.blocks.util.ShapeUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class HorizontalFacingBlock_ShapeSmall extends HorizontalFacingBlock {

    public HorizontalFacingBlock_ShapeSmall(Settings settings) {
        super(settings);

        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        return ShapeUtil.SHAPE_SMALL;
    }
}