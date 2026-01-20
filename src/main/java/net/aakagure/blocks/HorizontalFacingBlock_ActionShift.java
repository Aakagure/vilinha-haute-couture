package net.aakagure.blocks;

import net.aakagure.blocks.util.HelperUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HorizontalFacingBlock_ActionShift extends HorizontalFacingBlock {
    public static final BooleanProperty SHIFT = BooleanProperty.of("shift");

    public HorizontalFacingBlock_ActionShift(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SHIFT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SHIFT);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        return HelperUtil.toggleProperty(state, world, pos, player, SHIFT);
    }
}