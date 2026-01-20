package net.aakagure.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import java.util.List;

public class Block_ReplaceDouble extends Block {
    public static final BooleanProperty DOUBLE = BooleanProperty.of("double");
    protected static final VoxelShape HALF_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    public Block_ReplaceDouble(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DOUBLE, false));
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DOUBLE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Se for DOUBLE, retorna cubo cheio, senão retorna metade
        return state.get(DOUBLE) ? VoxelShapes.fullCube() : HALF_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        BlockState currentState = ctx.getWorld().getBlockState(pos);

        // Se já existe este bloco aqui, vira DOUBLE
        if (currentState.isOf(this)) {
            return currentState.with(DOUBLE, true);
        }
        return super.getPlacementState(ctx).with(DOUBLE, false);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        boolean isDouble = state.get(DOUBLE);

        // Permite substituir se ainda não é double e o jogador está segurando este item
        if (!isDouble && itemStack.isOf(this.asItem())) {
            return true; // Simplificado para permitir empilhar como slab
        }
        return super.canReplace(state, context);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.vilinha_haute_couture.action_double").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, options);
    }
}