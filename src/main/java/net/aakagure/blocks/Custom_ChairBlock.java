package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class Custom_ChairBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    // Formas aproximadas para uma cadeira padrão
    protected static final VoxelShape SEAT = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    protected static final VoxelShape BACK_NORTH = Block.createCuboidShape(3.0D, 8.0D, 11.0D, 13.0D, 16.0D, 13.0D);
    protected static final VoxelShape BACK_SOUTH = Block.createCuboidShape(3.0D, 8.0D, 3.0D, 13.0D, 16.0D, 5.0D);
    protected static final VoxelShape BACK_WEST = Block.createCuboidShape(11.0D, 8.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    protected static final VoxelShape BACK_EAST = Block.createCuboidShape(3.0D, 8.0D, 3.0D, 5.0D, 16.0D, 13.0D);

    public Custom_ChairBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, net.minecraft.util.math.Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape back;
        switch (state.get(FACING)) {
            case SOUTH: back = BACK_SOUTH; break;
            case WEST: back = BACK_WEST; break;
            case EAST: back = BACK_EAST; break;
            default: back = BACK_NORTH; break;
        }
        return VoxelShapes.union(SEAT, back);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
}