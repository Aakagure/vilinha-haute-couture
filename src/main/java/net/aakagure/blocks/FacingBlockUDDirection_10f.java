package net.aakagure.blocks;

import net.aakagure.blocks.enums.UDDirectionEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class FacingBlockUDDirection_10f extends Block {
    public static final EnumProperty<UDDirectionEnum> FACING = EnumProperty.of("facing", UDDirectionEnum.class);

    public FacingBlockUDDirection_10f(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, UDDirectionEnum.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getHorizontalPlayerFacing().getOpposite();
        Direction side = ctx.getSide();
        boolean upperHalf = ctx.getHitPos().y - (double)ctx.getBlockPos().getY() > 0.5D;

        UDDirectionEnum placed;

        // Lógica de 3 níveis: Normal, UP (teto), DOWN (chão)
        // Isso é uma aproximação da lógica original do Cocricot
        if (side == Direction.DOWN || (side.getAxis().isHorizontal() && upperHalf)) {
            // Variante de teto (UP)
            switch (facing) {
                case EAST: placed = UDDirectionEnum.UP_EAST; break;
                case SOUTH: placed = UDDirectionEnum.UP_SOUTH; break;
                case WEST: placed = UDDirectionEnum.UP_WEST; break;
                default: placed = UDDirectionEnum.UP_NORTH; break;
            }
        } else if (side == Direction.UP) {
            // Variante de chão (DOWN) - No original pode ser chamado de DOWN_...
            // Mas o enum tem DOWN_NORTH etc.
            switch (facing) {
                case EAST: placed = UDDirectionEnum.DOWN_EAST; break;
                case SOUTH: placed = UDDirectionEnum.DOWN_SOUTH; break;
                case WEST: placed = UDDirectionEnum.DOWN_WEST; break;
                default: placed = UDDirectionEnum.DOWN_NORTH; break;
            }
        } else {
            // Variante normal (parede/centro)
            switch (facing) {
                case EAST: placed = UDDirectionEnum.EAST; break;
                case SOUTH: placed = UDDirectionEnum.SOUTH; break;
                case WEST: placed = UDDirectionEnum.WEST; break;
                default: placed = UDDirectionEnum.NORTH; break;
            }
        }
        return this.getDefaultState().with(FACING, placed);
    }
}