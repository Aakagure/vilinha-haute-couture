package net.aakagure.blocks;



import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import java.util.List;

public class Block_OnWater extends Block {
    public static final BooleanProperty ON = BooleanProperty.of("on");

    public Block_OnWater(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ON, false));
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ON);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculateBlockState(super.getPlacementState(ctx), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return calculateBlockState(state, world, pos);
    }

    private BlockState calculateBlockState(BlockState state, WorldAccess world, BlockPos pos) {
        if (state == null) return null;
        BlockState belowState = world.getBlockState(pos.down());

        // Verifica se o bloco de baixo é um bloco de fluido
        boolean isOn = belowState.getBlock() instanceof FluidBlock;
        return state.with(ON, isOn);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.vilinha_haute_couture.on_water").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, options);
    }
}
