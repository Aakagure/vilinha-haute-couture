package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class ConnectHorizontalBlock_ConnectUD extends ConnectHorizontalBlock {
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");

    public ConnectHorizontalBlock_ConnectUD(Settings settings) {
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
        BlockState state = super.getPlacementState(ctx);
        return calculateUDState(state, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState updatedState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        return calculateUDState(updatedState, world, pos);
    }

    private BlockState calculateUDState(BlockState state, WorldAccess world, BlockPos pos) {
        if (state == null) return null;

        BlockState upState = world.getBlockState(pos.up());
        BlockState downState = world.getBlockState(pos.down());

        boolean up = upState.isOf(this);
        boolean down = downState.isOf(this);

        return state.with(UP, up).with(DOWN, down);
    }
}
