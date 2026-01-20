package net.aakagure.blocks;

import net.aakagure.blocks.enums.VerticalEnum;
import net.aakagure.blocks.util.HelperUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class FacingBlock_ConnectVertical_ActionLit extends FacingBlock {
    public static final BooleanProperty LIT = Properties.LIT;
    public static final EnumProperty<VerticalEnum> VERTICAL = EnumProperty.of("vertical", VerticalEnum.class);

    public FacingBlock_ConnectVertical_ActionLit(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, true).with(VERTICAL, VerticalEnum.SINGLE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIT, VERTICAL);
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
        if (state == null) return null;
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
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        return HelperUtil.toggleProperty(state, world, pos, player, LIT);
    }
}