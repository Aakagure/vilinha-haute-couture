package net.aakagure.blocks;

import net.aakagure.blocks.enums.Position3Enum;
import net.aakagure.blocks.enums.VerticalEnum;
import net.aakagure.blocks.util.ShapeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class Custom_CurtainVertical extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<VerticalEnum> VERTICAL = EnumProperty.of("vertical", VerticalEnum.class);
    public static final EnumProperty<Position3Enum> POSITION = EnumProperty.of("position", Position3Enum.class);

    public Custom_CurtainVertical(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(VERTICAL, VerticalEnum.SINGLE)
                .with(POSITION, Position3Enum.SINGLE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, VERTICAL, POSITION);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Usa formas finas (VSLAB)
        switch (state.get(FACING)) {
            case SOUTH: return ShapeUtil.SHAPE_VSLAB_SOUTH;
            case WEST: return ShapeUtil.SHAPE_VSLAB_WEST;
            case EAST: return ShapeUtil.SHAPE_VSLAB_EAST;
            default: return ShapeUtil.SHAPE_VSLAB_NORTH;
        }
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

        // Conexão Vertical
        boolean up = world.getBlockState(pos.up()).isOf(this);
        boolean down = world.getBlockState(pos.down()).isOf(this);
        VerticalEnum vShape = (up && down) ? VerticalEnum.MIDDLE : (up ? VerticalEnum.LOWER : (down ? VerticalEnum.UPPER : VerticalEnum.SINGLE));

        // Conexão Horizontal (Position3Enum)
        Direction left = facing.rotateYClockwise();
        Direction right = facing.rotateYCounterclockwise();

        boolean hasLeft = world.getBlockState(pos.offset(left)).isOf(this);
        boolean hasRight = world.getBlockState(pos.offset(right)).isOf(this);

        Position3Enum pShape;
        if (hasLeft && hasRight) pShape = Position3Enum.SINGLE; // Curioso, no original pode ser Middle, mas vamos manter Single para 'fechado'
        else if (hasLeft) pShape = Position3Enum.RIGHT;
        else if (hasRight) pShape = Position3Enum.LEFT;
        else pShape = Position3Enum.SINGLE;

        return state.with(VERTICAL, vShape).with(POSITION, pShape);
    }
}