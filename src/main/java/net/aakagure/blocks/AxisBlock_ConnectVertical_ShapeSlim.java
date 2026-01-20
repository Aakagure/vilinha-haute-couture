package net.aakagure.blocks;



import net.aakagure.blocks.enums.VerticalEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class AxisBlock_ConnectVertical_ShapeSlim extends AxisBlock_ShapeSlim {
    public static final EnumProperty<VerticalEnum> VERTICAL = EnumProperty.of("vertical", VerticalEnum.class);

    public AxisBlock_ConnectVertical_ShapeSlim(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(VERTICAL, VerticalEnum.SINGLE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(VERTICAL);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        if (state == null) return null;

        // Se estiver no eixo Y, calcula a conexão
        if (state.get(AXIS) == Direction.Axis.Y) {
            return calculateBlockState(state, ctx.getWorld(), ctx.getBlockPos());
        }
        return state.with(VERTICAL, VerticalEnum.SINGLE); // Se deitado, não conecta
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(AXIS) == Direction.Axis.Y && direction.getAxis() == Direction.Axis.Y) {
            return calculateBlockState(state, world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private BlockState calculateBlockState(BlockState state, WorldAccess world, BlockPos pos) {
        Block currentBlock = state.getBlock();
        BlockState aboveState = world.getBlockState(pos.up());
        BlockState belowState = world.getBlockState(pos.down());

        boolean upMatch = aboveState.isOf(currentBlock); // Simplificação: checa apenas se é o mesmo bloco
        boolean downMatch = belowState.isOf(currentBlock);

        VerticalEnum shape;
        if (upMatch && downMatch) {
            shape = VerticalEnum.MIDDLE;
        } else if (upMatch) {
            shape = VerticalEnum.LOWER; // Tem em cima, sou a base
        } else if (downMatch) {
            shape = VerticalEnum.UPPER; // Tem embaixo, sou o topo
        } else {
            shape = VerticalEnum.SINGLE;
        }

        return state.with(VERTICAL, shape);
    }
}