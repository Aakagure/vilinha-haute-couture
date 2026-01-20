package net.aakagure.blocks;

import net.aakagure.blocks.enums.Position3Enum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class Custom_WindowFrameSideBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<Position3Enum> POSITION3 = EnumProperty.of("position3", Position3Enum.class); // No original era POSITION3
    public static final BooleanProperty FENCE = BooleanProperty.of("fence");

    public Custom_WindowFrameSideBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(POSITION3, Position3Enum.SINGLE)
                .with(FENCE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POSITION3, FENCE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        return calculateState(state, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return calculateState(state, world, pos);
    }

    private BlockState calculateState(BlockState state, WorldAccess world, BlockPos pos) {
        Direction facing = state.get(FACING);
        Direction left = facing.rotateYClockwise();
        Direction right = facing.rotateYCounterclockwise();

        BlockState leftState = world.getBlockState(pos.offset(left));
        BlockState rightState = world.getBlockState(pos.offset(right));

        // Lógica de Conexão Horizontal
        boolean hasLeft = leftState.isOf(this) && leftState.get(FACING) == facing;
        boolean hasRight = rightState.isOf(this) && rightState.get(FACING) == facing;

        Position3Enum pos3;
        if (hasLeft && hasRight) pos3 = Position3Enum.SINGLE; // Curiosamente, em alguns mods 'Single' no meio significa 'Centro' ou 'Vidro liso'
        else if (hasLeft) pos3 = Position3Enum.RIGHT;
        else if (hasRight) pos3 = Position3Enum.LEFT;
        else pos3 = Position3Enum.SINGLE;

        // Lógica de Cerca (Fence)
        // Verifica se há algo sólido ou uma cerca atrás da janela? Ou do lado?
        // Vamos supor conexão traseira comum em janelas
        BlockState backState = world.getBlockState(pos.offset(facing.getOpposite()));
        boolean isFence = backState.isIn(net.minecraft.registry.tag.BlockTags.FENCES) || backState.isIn(net.minecraft.registry.tag.BlockTags.WALLS);

        return state.with(POSITION3, pos3).with(FENCE, isFence);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
}