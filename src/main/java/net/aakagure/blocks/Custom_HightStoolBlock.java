package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.aakagure.blocks.util.ShapeUtil;

public class Custom_HightStoolBlock extends Block {
    public static final BooleanProperty ON = BooleanProperty.of("on");

    public Custom_HightStoolBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ON, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ON);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ShapeUtil.SHAPE_SMALL; // Usa a forma pequena (centralizada)
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculateState(super.getPlacementState(ctx), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            return calculateState(state, world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private BlockState calculateState(BlockState state, WorldAccess world, BlockPos pos) {
        if (state == null) return null;

        BlockState below = world.getBlockState(pos.down());
        // Se o bloco de baixo for um slab (altura 0.5), o banco fica no modo ON (provavelmente perna mais curta ou ajustada)
        boolean isOn = below.getOutlineShape(world, pos.down()).getMax(Direction.Axis.Y) == 0.5D;

        return state.with(ON, isOn);
    }
}