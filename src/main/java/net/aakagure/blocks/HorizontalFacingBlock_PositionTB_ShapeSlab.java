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
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class HorizontalFacingBlock_PositionTB_ShapeSlab extends HorizontalFacingBlock {
    public static final EnumProperty<PositionEnum> POSITION = EnumProperty.of("position", PositionEnum.class);

    protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public HorizontalFacingBlock_PositionTB_ShapeSlab(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(POSITION, PositionEnum.BOTTOM));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POSITION);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(POSITION) == PositionEnum.TOP ? TOP_SHAPE : BOTTOM_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        Direction side = ctx.getSide();
        BlockPos pos = ctx.getBlockPos();
        // Lógica de Slab: Se clicou na face de baixo -> Topo. Face de cima -> Base.
        // Ou se clicou no lado, depende da altura do clique (hitY).
        PositionEnum posEnum;
        if (side == Direction.DOWN || (side != Direction.UP && ctx.getHitPos().y - (double)pos.getY() > 0.5D)) {
            posEnum = PositionEnum.TOP;
        } else {
            posEnum = PositionEnum.BOTTOM;
        }
        return state.with(POSITION, posEnum);
    }
}