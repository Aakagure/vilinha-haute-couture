package net.aakagure.blocks;

import net.aakagure.blocks.enums.DDirectionEnum;
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

public class FacingBlockDDirection_ShapeVSlab extends Block {
    public static final EnumProperty<DDirectionEnum> FACING = EnumProperty.of("facing", DDirectionEnum.class);

    public FacingBlockDDirection_ShapeVSlab(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, DDirectionEnum.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Mapeamento simplificado para formas VSLAB
        // UP_NORTH e NORTH compartilham a forma base neste contexto, mudando apenas a textura/modelo
        DDirectionEnum face = state.get(FACING);
        switch (face) {
            case SOUTH: case UP_SOUTH: return ShapeUtil.SHAPE_VSLAB_SOUTH;
            case WEST: case UP_WEST: return ShapeUtil.SHAPE_VSLAB_WEST;
            case EAST: case UP_EAST: return ShapeUtil.SHAPE_VSLAB_EAST;
            default: return ShapeUtil.SHAPE_VSLAB_NORTH;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // Lógica para determinar se é a variante normal ou "UP" (superior)
        // Se o jogador clicar na metade superior do bloco ou estiver olhando para cima
        Direction side = ctx.getSide();
        Direction facing = ctx.getHorizontalPlayerFacing().getOpposite();
        boolean isUp = side == Direction.DOWN || (side != Direction.UP && ctx.getHitPos().y - (double)ctx.getBlockPos().getY() > 0.5D);

        DDirectionEnum placed;
        if (isUp) {
            switch (facing) {
                case EAST: placed = DDirectionEnum.UP_EAST; break;
                case SOUTH: placed = DDirectionEnum.UP_SOUTH; break;
                case WEST: placed = DDirectionEnum.UP_WEST; break;
                default: placed = DDirectionEnum.UP_NORTH; break;
            }
        } else {
            switch (facing) {
                case EAST: placed = DDirectionEnum.EAST; break;
                case SOUTH: placed = DDirectionEnum.SOUTH; break;
                case WEST: placed = DDirectionEnum.WEST; break;
                default: placed = DDirectionEnum.NORTH; break;
            }
        }
        return this.getDefaultState().with(FACING, placed);
    }
}