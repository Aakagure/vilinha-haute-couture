package net.aakagure.blocks;

import net.aakagure.blocks.enums.VerticalEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class Custom_PostStreetBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<VerticalEnum> VERTICAL = EnumProperty.of("vertical", VerticalEnum.class);

    public Custom_PostStreetBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(VERTICAL, VerticalEnum.SINGLE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, VERTICAL);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        return calculateVertical(state, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis() == Direction.Axis.Y) {
            return calculateVertical(state, world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private BlockState calculateVertical(BlockState state, WorldAccess world, BlockPos pos) {
        boolean up = world.getBlockState(pos.up()).isOf(this);
        boolean down = world.getBlockState(pos.down()).isOf(this);

        VerticalEnum shape;
        if (up && down) shape = VerticalEnum.MIDDLE;
        else if (up) shape = VerticalEnum.LOWER;
        else if (down) shape = VerticalEnum.UPPER;
        else shape = VerticalEnum.SINGLE;

        return state.with(VERTICAL, shape);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
}