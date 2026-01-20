package net.aakagure.blocks;

import net.aakagure.blocks.enums.ShiftEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import java.util.List;

public class Custom_GlassBlock_ActionShift extends Block {
    public static final EnumProperty<ShiftEnum> SHIFT = EnumProperty.of("shift", ShiftEnum.class);

    public Custom_GlassBlock_ActionShift(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SHIFT, ShiftEnum.NONE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SHIFT);
    }

    // Configurações de Vidro
    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this)) return true; // Esconde face se vizinho for igual
        return super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;

        // Cicla pelos valores do Enum
        ShiftEnum current = state.get(SHIFT);
        ShiftEnum[] values = ShiftEnum.values();
        int nextIndex = (current.ordinal() + 1) % values.length;

        world.setBlockState(pos, state.with(SHIFT, values[nextIndex]), 3);
        // Opcional: Som de clique via HelperUtil ou manual
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.vilinha_haute_couture.action_shift").formatted(Formatting.GRAY));
    }
}