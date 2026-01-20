package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.List;

// Imports solicitados
import net.minecraft.item.tooltip.TooltipType;
import net.aakagure.blocks.util.HelperUtil;

public class ConnectHorizontalBlock_ActionShift extends ConnectHorizontalBlock {
    // Propriedade personalizada "shift" (no original era boolean)
    public static final BooleanProperty SHIFT = BooleanProperty.of("shift");

    public ConnectHorizontalBlock_ActionShift(Settings settings) {
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
        // Usa o HelperUtil para alternar o estado SHIFT
        // Isso vai tocar o som e atualizar o bloco
        return HelperUtil.toggleProperty(state, world, pos, player, SHIFT);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.vilinha_haute_couture.action_shift").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, options);
    }
}
