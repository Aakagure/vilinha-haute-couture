package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class HorizontalFacingBlock_ReplaceDouble extends HorizontalFacingBlock {
    public static final BooleanProperty DOUBLE = BooleanProperty.of("double");
    // Forma padrão de slab (metade inferior)
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    public HorizontalFacingBlock_ReplaceDouble(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DOUBLE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(DOUBLE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(DOUBLE) ? VoxelShapes.fullCube() : SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        BlockState current = ctx.getWorld().getBlockState(pos);

        if (current.isOf(this)) {
            // Se já existe, vira double e mantém a rotação existente (ou atualiza, dependendo da lógica desejada)
            // Geralmente mantém a rotação original do bloco base
            return current.with(DOUBLE, true);
        }
        return super.getPlacementState(ctx).with(DOUBLE, false);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack stack = context.getStack();
        boolean isDouble = state.get(DOUBLE);
        if (!isDouble && stack.isOf(this.asItem())) {
            return true; // Permite colocar outro no mesmo bloco
        }
        return super.canReplace(state, context);
    }
}