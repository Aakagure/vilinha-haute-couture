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
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import java.util.List;

public class Block_OnHalf_ShapeSlim extends Block {
    public static final BooleanProperty ON = BooleanProperty.of("on");

    public Block_OnHalf_ShapeSlim(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ON, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ShapeUtil.SHAPE_SLIM;
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
        BlockPos posBelow = pos.down();
        BlockState belowState = world.getBlockState(posBelow);

        // Pega a altura máxima da forma do bloco de baixo no eixo Y
        VoxelShape shape = belowState.getOutlineShape(world, posBelow);
        double maxY = shape.getMax(Direction.Axis.Y);

        // Se for 0.5 (meio bloco/slab), liga o estado ON
        boolean isOn = (maxY == 0.5D);
        return state.with(ON, isOn);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.vilinha_haute_couture.on_half").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, options);
    }
}