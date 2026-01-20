package net.aakagure.blocks;

import net.aakagure.blocks.enums.BedFrameEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;

public class Custom_BedFrame extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<BedFrameEnum> PART = EnumProperty.of("part", BedFrameEnum.class);

    public Custom_BedFrame(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, net.minecraft.util.math.Direction.NORTH)
                .with(PART, BedFrameEnum.CENTER));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // Lógica simplificada: Coloca o centro.
        // Para uma implementação completa de cama (multibloco), seria necessário
        // verificar vizinhos e colocar blocos "fantasma", o que é complexo.
        // Aqui mantemos simples para decoração.
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
}