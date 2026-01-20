package net.aakagure.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.BlockView;

public class Custom_GlassBlock_Pane extends ConnectNSEWBlock {
    public Custom_GlassBlock_Pane(Settings settings) {
        super(settings);
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    // O culling do painel é complexo, geralmente não escondemos as faces laterais finas,
    // mas escondemos faces se houver um bloco cheio do lado.
    // Vamos manter o padrão para garantir transparência correta.
}