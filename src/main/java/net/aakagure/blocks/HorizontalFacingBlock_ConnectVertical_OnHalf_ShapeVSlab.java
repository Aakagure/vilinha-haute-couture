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

public class HorizontalFacingBlock_ConnectVertical_OnHalf_ShapeVSlab extends HorizontalFacingBlock_ConnectVertical {
    public static final BooleanProperty ON = BooleanProperty.of("on");

    public HorizontalFacingBlock_ConnectVertical_OnHalf_ShapeVSlab(Settings settings) {
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
        switch (state.get(FACING)) {
            case SOUTH: return ShapeUtil.SHAPE_VSLAB_SOUTH;
            case WEST: return ShapeUtil.SHAPE_VSLAB_WEST;
            case EAST: return ShapeUtil.SHAPE_VSLAB_EAST;
            default: return ShapeUtil.SHAPE_VSLAB_NORTH;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // Pega o estado da classe pai (que já calcula Vertical connection)
        BlockState state = super.getPlacementState(ctx);
        return calculateOnHalf(state, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        // Atualiza conexão vertical (super) e depois verifica o slab
        BlockState updatedState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        return calculateOnHalf(updatedState, world, pos);
    }

    private BlockState calculateOnHalf(BlockState state, WorldAccess world, BlockPos pos) {
        if (state == null) return null;
        BlockPos below = pos.down();
        VoxelShape shape = world.getBlockState(below).getOutlineShape(world, below);
        boolean isOnHalf = shape.getMax(Direction.Axis.Y) == 0.5D;
        return state.with(ON, isOnHalf);
    }
}