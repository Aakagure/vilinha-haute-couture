package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class Custom_GlassBlock_PaneUD extends Custom_GlassBlock_Pane {
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");

    public Custom_GlassBlock_PaneUD(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(UP, false).with(DOWN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(UP, DOWN);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx)
                .with(UP, canConnectVertical(ctx.getWorld(), ctx.getBlockPos().up()))
                .with(DOWN, canConnectVertical(ctx.getWorld(), ctx.getBlockPos().down()));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP) {
            return state.with(UP, canConnectVertical(world, neighborPos));
        }
        if (direction == Direction.DOWN) {
            return state.with(DOWN, canConnectVertical(world, neighborPos));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private boolean canConnectVertical(WorldAccess world, BlockPos pos) {
        return world.getBlockState(pos).isOf(this);
    }
}