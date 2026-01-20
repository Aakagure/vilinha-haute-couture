package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class HorizontalFacingBlock_ConnectVertical_ShapeLadder extends HorizontalFacingBlock_ConnectVertical {
    // Hitboxes finas encostadas na parede
    private static final VoxelShape NORTH = Block.createCuboidShape(0, 0, 14, 16, 16, 16);
    private static final VoxelShape SOUTH = Block.createCuboidShape(0, 0, 0, 16, 16, 2);
    private static final VoxelShape WEST = Block.createCuboidShape(14, 0, 0, 16, 16, 16);
    private static final VoxelShape EAST = Block.createCuboidShape(0, 0, 0, 2, 16, 16);

    public HorizontalFacingBlock_ConnectVertical_ShapeLadder(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case SOUTH: return SOUTH;
            case WEST: return WEST;
            case EAST: return EAST;
            default: return NORTH;
        }
    }
}