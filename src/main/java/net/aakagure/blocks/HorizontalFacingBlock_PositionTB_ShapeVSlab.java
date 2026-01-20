package net.aakagure.blocks;

import net.aakagure.blocks.enums.PositionEnum;
import net.aakagure.blocks.util.ShapeUtil;
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

public class HorizontalFacingBlock_PositionTB_ShapeVSlab extends HorizontalFacingBlock {
    public static final EnumProperty<PositionEnum> POSITION = EnumProperty.of("position", PositionEnum.class);

    public HorizontalFacingBlock_PositionTB_ShapeVSlab(Settings settings) {
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
        // A forma base é VSlab (fino vertical), mas a posição pode mudar a hitbox?
        // Assumindo que VSlab é a forma padrão definida no ShapeUtil, e a posição apenas muda o modelo visual ou desloca a hitbox.
        // Se a hitbox precisar subir/descer, precisaríamos de novas shapes no ShapeUtil.
        // Por hora, usamos a forma padrão rotacionada.
        switch (state.get(FACING)) {
            case SOUTH: return ShapeUtil.SHAPE_VSLAB_SOUTH;
            case WEST: return ShapeUtil.SHAPE_VSLAB_WEST;
            case EAST: return ShapeUtil.SHAPE_VSLAB_EAST;
            default: return ShapeUtil.SHAPE_VSLAB_NORTH;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        Direction side = ctx.getSide();
        boolean upper = ctx.getHitPos().y - (double)ctx.getBlockPos().getY() > 0.5D;
        PositionEnum posEnum = (side == Direction.DOWN || (side != Direction.UP && upper)) ? PositionEnum.TOP : PositionEnum.BOTTOM;
        return state.with(POSITION, posEnum);
    }
}