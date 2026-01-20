package net.aakagure.blocks;

import net.aakagure.blocks.util.ShapeUtil;
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

public class HorizontalFacingBlock_OnHalf_ShapeSlim extends HorizontalFacingBlock {
    public static final BooleanProperty ON = BooleanProperty.of("on");

    public HorizontalFacingBlock_OnHalf_ShapeSlim(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ON, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ON);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ShapeUtil.SHAPE_SLIM;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculateState(super.getPlacementState(ctx), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return calculateState(state, world, pos);
    }

    private BlockState calculateState(BlockState state, WorldAccess world, BlockPos pos) {
        if (state == null) return null;
        BlockPos below = pos.down();
        VoxelShape shape = world.getBlockState(below).getOutlineShape(world, below);

        // Verifica se a altura máxima do bloco de baixo é 0.5 (Slab)
        boolean isOnHalf = shape.getMax(Direction.Axis.Y) == 0.5D;
        return state.with(ON, isOnHalf);
    }
}