package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class HorizontalFacingBlock_ConnectUDLR extends HorizontalFacingBlock {
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final BooleanProperty LEFT = BooleanProperty.of("left");
    public static final BooleanProperty RIGHT = BooleanProperty.of("right");

    public HorizontalFacingBlock_ConnectUDLR(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(UP, false).with(DOWN, false)
                .with(LEFT, false).with(RIGHT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(UP, DOWN, LEFT, RIGHT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculateConnections(super.getPlacementState(ctx), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return calculateConnections(state, world, pos);
    }

    protected BlockState calculateConnections(BlockState state, WorldAccess world, BlockPos pos) {
        Direction facing = state.get(FACING);

        // As conexões Left/Right dependem da rotação do bloco
        Direction leftDir = facing.rotateYClockwise();
        Direction rightDir = facing.rotateYCounterclockwise();

        boolean up = isConnectable(world, pos.up(), facing);
        boolean down = isConnectable(world, pos.down(), facing);
        boolean left = isConnectable(world, pos.offset(leftDir), facing);
        boolean right = isConnectable(world, pos.offset(rightDir), facing);

        return state.with(UP, up).with(DOWN, down).with(LEFT, left).with(RIGHT, right);
    }

    protected boolean isConnectable(WorldAccess world, BlockPos neighborPos, Direction myFacing) {
        BlockState neighbor = world.getBlockState(neighborPos);
        // Regra padrão: conecta se for o mesmo bloco
        // (Opcionalmente poderia checar se o vizinho tem o mesmo FACING, mas geralmente UDLR conecta mesmo se girado)
        return neighbor.isOf(this);
    }
}