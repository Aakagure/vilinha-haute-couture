package net.aakagure.blocks;

import net.aakagure.blocks.util.HelperUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HorizontalFacingBlock_ActionClick extends HorizontalFacingBlock {
    public static final BooleanProperty ON = BooleanProperty.of("on");

    public HorizontalFacingBlock_ActionClick(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ON, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ON);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(ON, false);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        return HelperUtil.toggleProperty(state, world, pos, player, ON);
    }
}