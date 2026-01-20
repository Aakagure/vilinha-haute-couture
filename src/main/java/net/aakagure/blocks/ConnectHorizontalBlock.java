package net.aakagure.blocks;

import net.aakagure.blocks.enums.HorizontalEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class ConnectHorizontalBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<HorizontalEnum> HORIZONTAL = EnumProperty.of("horizontal", HorizontalEnum.class);

    public ConnectHorizontalBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(HORIZONTAL, HorizontalEnum.SINGLE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, HORIZONTAL);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculateBlockState(this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis().isHorizontal()) {
            return calculateBlockState(state, world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // Método auxiliar para rotacionar o bloco (WorldEdit/Structure Block friendly)
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    protected BlockState calculateBlockState(BlockState state, WorldAccess world, BlockPos pos) {
        Direction facing = state.get(FACING);

        // Determina quem está à esquerda e à direita baseado na rotação do bloco atual
        Direction leftDir = facing.rotateYClockwise();
        Direction rightDir = facing.rotateYCounterclockwise();

        BlockPos leftPos = pos.offset(leftDir);
        BlockPos rightPos = pos.offset(rightDir);

        BlockState leftState = world.getBlockState(leftPos);
        BlockState rightState = world.getBlockState(rightPos);

        // Verifica se os vizinhos são do mesmo bloco e estão virados para o mesmo lado
        boolean isLeftSame = leftState.isOf(this) && leftState.get(FACING) == facing;
        boolean isRightSame = rightState.isOf(this) && rightState.get(FACING) == facing;

        HorizontalEnum shape;
        if (isLeftSame && isRightSame) {
            shape = HorizontalEnum.MIDDLE;
        } else if (isLeftSame) {
            // Se conecta à esquerda (horário), visualmente é a peça da Direita
            shape = HorizontalEnum.RIGHT;
        } else if (isRightSame) {
            // Se conecta à direita (anti-horário), visualmente é a peça da Esquerda
            shape = HorizontalEnum.LEFT;
        } else {
            shape = HorizontalEnum.SINGLE;
        }

        return state.with(HORIZONTAL, shape);
    }
}
