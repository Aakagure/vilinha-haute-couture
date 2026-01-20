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

public class Custom_Fence extends Block {
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty WEST = BooleanProperty.of("west");

    // Formas simplificadas para o poste e as laterais
    protected static final VoxelShape POST = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    // Na implementação real, seria ideal combinar as formas dinamicamente

    public Custom_Fence(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Retorna apenas o poste central para simplificar a colisão neste momento
        return POST;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculateConnection(this.getDefaultState(), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis().isHorizontal()) {
            return calculateConnection(state, world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    protected BlockState calculateConnection(BlockState state, WorldAccess world, BlockPos pos) {
        boolean n = canConnect(world.getBlockState(pos.north()));
        boolean e = canConnect(world.getBlockState(pos.east()));
        boolean s = canConnect(world.getBlockState(pos.south()));
        boolean w = canConnect(world.getBlockState(pos.west()));
        return state.with(NORTH, n).with(EAST, e).with(SOUTH, s).with(WEST, w);
    }

    protected boolean canConnect(BlockState neighbor) {
        // Por padrão conecta com qualquer bloco sólido ou ele mesmo
        return neighbor.isOf(this) || neighbor.isFullCube(null, null); // Check simplificado
    }
}