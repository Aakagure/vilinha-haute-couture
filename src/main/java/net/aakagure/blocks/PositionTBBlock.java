package net.aakagure.blocks;

import net.aakagure.blocks.enums.PositionEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class PositionTBBlock extends Block {
    public static final EnumProperty<PositionEnum> POSITION = EnumProperty.of("position", PositionEnum.class);
    protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    public PositionTBBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(POSITION, PositionEnum.BOTTOM));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POSITION);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(POSITION) == PositionEnum.TOP ? TOP_SHAPE : BOTTOM_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction side = ctx.getSide();
        boolean upper = ctx.getHitPos().y - (double)ctx.getBlockPos().getY() > 0.5D;
        PositionEnum pos = (side == Direction.DOWN || (side != Direction.UP && upper)) ? PositionEnum.TOP : PositionEnum.BOTTOM;
        return this.getDefaultState().with(POSITION, pos);
    }
}