package net.aakagure.blocks;

import net.aakagure.blocks.util.ShapeUtil;

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
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.List;

public class Block_ReplaceDouble_OnHalf extends Block_ReplaceDouble {
    public static final BooleanProperty ON = BooleanProperty.of("on");

    public Block_ReplaceDouble_OnHalf(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ON, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ON);
    }

    // Sobrescreve para usar o SHAPE_HALFBOTTOM se não for DOUBLE
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(DOUBLE) ? VoxelShapes.fullCube() : ShapeUtil.SHAPE_HALFBOTTOM;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        return calculateBlockState(state, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return calculateBlockState(state, world, pos);
    }

    private BlockState calculateBlockState(BlockState state, WorldAccess world, BlockPos pos) {
        if (state == null) return null;

        BlockPos belowPos = pos.down();
        BlockState belowState = world.getBlockState(belowPos);
        VoxelShape belowShape = belowState.getOutlineShape(world, belowPos);

        // Verifica se o bloco de baixo tem altura de 0.5 (Slab/Step)
        double maxY = belowShape.getMax(Direction.Axis.Y);
        boolean isOn = (maxY == 0.5D);

        return state.with(ON, isOn);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        super.appendTooltip(stack, context, tooltip, options);
        tooltip.add(Text.translatable("tooltip.vilinha_haute_couture.on_half").formatted(Formatting.GRAY));
    }
}
