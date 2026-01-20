package net.aakagure;

import net.aakagure.registry.VilinhaHauteCoutureBlocks;
import net.aakagure.registry.VilinhaHauteCoutureItemGroups;
import net.aakagure.registry.VilinhaHauteCoutureKitchenBlocks;
import net.aakagure.registry.VilinhaHauteCoutureTags;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VilinhaHauteCouture implements ModInitializer {
    public static final String MOD_ID = "vilinha_haute_couture";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        VilinhaHauteCoutureBlocks.registerModBlocks();
        VilinhaHauteCoutureKitchenBlocks.registerModBlocks();

        VilinhaHauteCoutureItemGroups.registerItemGroups();

    }
}