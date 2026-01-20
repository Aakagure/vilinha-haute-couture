package net.aakagure.blocks;

import net.aakagure.blocks.util.ShapeUtil;
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

public class HorizontalFacingBlock_OnSofa_ShapeHalfBottom extends HorizontalFacingBlock {
    public static final BooleanProperty ON = BooleanProperty.of("on");

    public HorizontalFacingBlock_OnSofa_ShapeHalfBottom(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ON, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ON);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ShapeUtil.SHAPE_HALFBOTTOM;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculateOnSofa(super.getPlacementState(ctx), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return calculateOnSofa(state, world, pos);
    }

    private BlockState calculateOnSofa(BlockState state, WorldAccess world, BlockPos pos) {
        if (state == null) return null;

        BlockState belowState = world.getBlockState(pos.down());
        // Lógica: Verifica se o bloco de baixo é um Sofá Customizado
        // Como não temos acesso garantido à classe, verificamos se o nome do bloco contém "sofa" ou se é um stairs block (comum para sofás)
        // Ajuste conforme a sua implementação final dos sofás.
        boolean isSofa = belowState.getBlock() instanceof Custom_Sofa_ConnectHorizontal
                || belowState.getBlock() instanceof Custom_Sofa_Slipper;

        return state.with(ON, isSofa);
    }
}