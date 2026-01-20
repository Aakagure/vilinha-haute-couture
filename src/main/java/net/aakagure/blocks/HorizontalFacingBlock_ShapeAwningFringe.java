package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class HorizontalFacingBlock_ShapeAwningFringe extends HorizontalFacingBlock {
    // Exemplo de forma para franja (fina, no topo da frente)
    private static final VoxelShape NORTH = Block.createCuboidShape(0, 12, 0, 16, 16, 2);
    private static final VoxelShape SOUTH = Block.createCuboidShape(0, 12, 14, 16, 16, 16);
    private static final VoxelShape WEST = Block.createCuboidShape(0, 12, 0, 2, 16, 16);
    private static final VoxelShape EAST = Block.createCuboidShape(14, 12, 0, 16, 16, 16);

    public HorizontalFacingBlock_ShapeAwningFringe(Settings settings) {
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