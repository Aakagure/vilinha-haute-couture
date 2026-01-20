package net.aakagure.blocks;

import net.aakagure.blocks.enums.VerticalEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class ConnectVerticalBlock extends Block {
    public static final EnumProperty<VerticalEnum> VERTICAL = EnumProperty.of("vertical", VerticalEnum.class);

    public ConnectVerticalBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(VERTICAL, VerticalEnum.SINGLE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VERTICAL);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculateState(this.getDefaultState(), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis() == Direction.Axis.Y) {
            return calculateState(state, world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    protected BlockState calculateState(BlockState state, WorldAccess world, BlockPos pos) {
        boolean up = world.getBlockState(pos.up()).isOf(this);
        boolean down = world.getBlockState(pos.down()).isOf(this);

        VerticalEnum shape;
        if (up && down) shape = VerticalEnum.MIDDLE;
        else if (up) shape = VerticalEnum.LOWER;
        else if (down) shape = VerticalEnum.UPPER;
        else shape = VerticalEnum.SINGLE;

        return state.with(VERTICAL, shape);
    }
}