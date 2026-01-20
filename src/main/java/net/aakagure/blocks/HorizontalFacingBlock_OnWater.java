package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class HorizontalFacingBlock_OnWater extends HorizontalFacingBlock {
    public static final BooleanProperty ON = BooleanProperty.of("on");

    public HorizontalFacingBlock_OnWater(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ON, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ON);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculateOnWater(super.getPlacementState(ctx), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return calculateOnWater(state, world, pos);
    }

    private BlockState calculateOnWater(BlockState state, WorldAccess world, BlockPos pos) {
        if (state == null) return null;

        BlockPos below = pos.down();
        BlockState belowState = world.getBlockState(below);

        // Verifica se é água (fluido) ou bloco de gelo, etc.
        // Aqui verificamos especificamente fluidos para simular flutuação
        boolean isOnWater = world.getFluidState(below).getFluid() == Fluids.WATER ||
                world.getFluidState(below).getFluid() == Fluids.FLOWING_WATER;

        return state.with(ON, isOnWater);
    }
}