package net.aakagure.blocks;

import net.aakagure.blocks.enums.PositionEnum;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.Direction;

public class PositionTBBlock_Reverse extends PositionTBBlock {
    public PositionTBBlock_Reverse(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // Lógica invertida: Se clicar no chão -> Topo. Se clicar no teto -> Baixo.
        Direction side = ctx.getSide();
        boolean upper = ctx.getHitPos().y - (double)ctx.getBlockPos().getY() > 0.5D;

        PositionEnum pos;
        if (side == Direction.UP) pos = PositionEnum.BOTTOM;
        else if (side == Direction.DOWN) pos = PositionEnum.TOP;
        else pos = upper ? PositionEnum.BOTTOM : PositionEnum.TOP; // Inversão lateral também

        return this.getDefaultState().with(POSITION, pos);
    }
}