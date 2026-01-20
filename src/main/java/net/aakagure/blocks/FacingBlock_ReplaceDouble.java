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

public class FacingBlock_ReplaceDouble extends FacingBlock {
    public static final BooleanProperty DOUBLE = BooleanProperty.of("double");
    private static final VoxelShape HALF_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 8, 16);

    public FacingBlock_ReplaceDouble(Settings settings) {
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
        return state.get(DOUBLE) ? VoxelShapes.fullCube() : HALF_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        BlockState currentState = ctx.getWorld().getBlockState(pos);

        // Se já tem um bloco igual, vira double e mantém a rotação existente
        if (currentState.isOf(this)) {
            return currentState.with(DOUBLE, true);
        }
        return super.getPlacementState(ctx).with(DOUBLE, false);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        boolean isDouble = state.get(DOUBLE);
        if (!isDouble && itemStack.isOf(this.asItem())) {
            return true;
        }
        return super.canReplace(state, context);
    }
}