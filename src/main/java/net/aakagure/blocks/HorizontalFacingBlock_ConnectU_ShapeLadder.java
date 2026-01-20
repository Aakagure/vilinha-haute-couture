package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class HorizontalFacingBlock_ConnectU_ShapeLadder extends HorizontalFacingBlock {
    public static final BooleanProperty UP = BooleanProperty.of("up"); // Indica se conecta em cima

    // Hitboxes aproximadas de escada
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);

    public HorizontalFacingBlock_ConnectU_ShapeLadder(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(UP, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(UP);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case SOUTH: return SOUTH_SHAPE;
            case WEST: return WEST_SHAPE;
            case EAST: return EAST_SHAPE;
            default: return NORTH_SHAPE;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        return calculateConnection(state, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP) {
            return calculateConnection(state, world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private BlockState calculateConnection(BlockState state, WorldAccess world, BlockPos pos) {
        BlockState upState = world.getBlockState(pos.up());
        // Conecta se o de cima for a mesma escada na mesma direção
        boolean connectUp = upState.isOf(this) && upState.get(FACING) == state.get(FACING);
        return state.with(UP, connectUp);
    }
}