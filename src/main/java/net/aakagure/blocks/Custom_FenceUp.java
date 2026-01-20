package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class Custom_FenceUp extends Custom_Fence {
    public static final BooleanProperty UP = BooleanProperty.of("up");

    public Custom_FenceUp(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(UP, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(UP);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(UP, canConnectUp(ctx.getWorld().getBlockState(ctx.getBlockPos().up())));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP) {
            return state.with(UP, canConnectUp(neighborState));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private boolean canConnectUp(BlockState upState) {
        // Conecta se o bloco de cima for desta mesma família ou uma cerca
        return upState.getBlock() instanceof Custom_Fence;
    }
}