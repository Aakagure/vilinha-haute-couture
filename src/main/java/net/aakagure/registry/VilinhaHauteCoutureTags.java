package net.aakagure.registry;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class VilinhaHauteCoutureTags {
    public static class Blocks {
        public static final TagKey<Block> SHELF_BOOK_CLASSIC = createTag("shelf_book_classic");
        public static final TagKey<Block> SHELF_BOOK_CLASSIC_VSLAB = createTag("shelf_book_classic_vslab");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of("vilinha_haute_couture", name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of("vilinha_haute_couture", name));
        }
    }
}