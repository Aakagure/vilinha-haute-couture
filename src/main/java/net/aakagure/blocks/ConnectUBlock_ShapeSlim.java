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
import net.aakagure.blocks.util.ShapeUtil;

public class ConnectUBlock_ShapeSlim extends Block {
    public static final BooleanProperty UP = BooleanProperty.of("up");

    public ConnectUBlock_ShapeSlim(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(UP, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ShapeUtil.SHAPE_SLIM;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculateState(this.getDefaultState(), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP) {
            return calculateState(state, world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private BlockState calculateState(BlockState state, WorldAccess world, BlockPos pos) {
        boolean connectUp = world.getBlockState(pos.up()).isOf(this);
        return state.with(UP, connectUp);
    }
}