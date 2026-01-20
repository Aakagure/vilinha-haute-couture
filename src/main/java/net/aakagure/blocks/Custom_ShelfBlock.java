package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class Custom_ShelfBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    // Propriedades booleanas para conexão visual
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final BooleanProperty LEFT = BooleanProperty.of("left");
    public static final BooleanProperty RIGHT = BooleanProperty.of("right");

    public Custom_ShelfBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(UP, false).with(DOWN, false)
                .with(LEFT, false).with(RIGHT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, UP, DOWN, LEFT, RIGHT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        return calculateConnections(state, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return calculateConnections(state, world, pos);
    }

    private BlockState calculateConnections(BlockState state, WorldAccess world, BlockPos pos) {
        Direction facing = state.get(FACING);

        // Determina as direções relativas
        Direction leftDir = facing.rotateYClockwise();
        Direction rightDir = facing.rotateYCounterclockwise();

        boolean up = isSameShelf(world, pos.up(), facing);
        boolean down = isSameShelf(world, pos.down(), facing);
        boolean left = isSameShelf(world, pos.offset(leftDir), facing);
        boolean right = isSameShelf(world, pos.offset(rightDir), facing);

        return state.with(UP, up).with(DOWN, down).with(LEFT, left).with(RIGHT, right);
    }

    private boolean isSameShelf(WorldAccess world, BlockPos pos, Direction myFacing) {
        BlockState state = world.getBlockState(pos);
        // Conecta se for o mesmo bloco e estiver virado para o mesmo lado
        return state.isOf(this) && state.get(FACING) == myFacing;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
}