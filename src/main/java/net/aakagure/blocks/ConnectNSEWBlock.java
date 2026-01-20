package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
// Imports solicitados
import net.aakagure.blocks.util.ShapeUtil;

public class ConnectNSEWBlock extends Block {
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty WEST = BooleanProperty.of("west");

    public ConnectNSEWBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculateState(this.getDefaultState(), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis().isHorizontal()) {
            return calculateState(state, world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private BlockState calculateState(BlockState state, WorldAccess world, BlockPos pos) {
        boolean n = canConnect(world.getBlockState(pos.north()));
        boolean e = canConnect(world.getBlockState(pos.east()));
        boolean s = canConnect(world.getBlockState(pos.south()));
        boolean w = canConnect(world.getBlockState(pos.west()));

        return state.with(NORTH, n).with(EAST, e).with(SOUTH, s).with(WEST, w);
    }

    private boolean canConnect(BlockState neighborState) {
        // Conecta se o vizinho for do mesmo bloco
        return neighborState.isOf(this);
    }
}