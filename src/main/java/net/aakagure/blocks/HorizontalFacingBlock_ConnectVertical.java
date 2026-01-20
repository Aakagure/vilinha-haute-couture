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

public class HorizontalFacingBlock_ConnectVertical extends HorizontalFacingBlock {
    public static final EnumProperty<VerticalEnum> VERTICAL = EnumProperty.of("vertical", VerticalEnum.class);

    public HorizontalFacingBlock_ConnectVertical(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(VERTICAL, VerticalEnum.SINGLE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(VERTICAL);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
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
        BlockState upState = world.getBlockState(pos.up());
        BlockState downState = world.getBlockState(pos.down());

        boolean up = upState.isOf(this);
        boolean down = downState.isOf(this);

        VerticalEnum shape;
        if (up && down) shape = VerticalEnum.MIDDLE;
        else if (up) shape = VerticalEnum.LOWER;
        else if (down) shape = VerticalEnum.UPPER;
        else shape = VerticalEnum.SINGLE;

        return state.with(VERTICAL, shape);
    }
}