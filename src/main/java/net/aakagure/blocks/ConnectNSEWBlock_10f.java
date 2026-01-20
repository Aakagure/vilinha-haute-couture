package net.aakagure.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ConnectNSEWBlock_10f extends ConnectNSEWBlock {
    public ConnectNSEWBlock_10f(Settings settings) { super(settings); }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F; // Luz total (sem sombra suave)
    }
}
