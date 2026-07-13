package draylar.goml.registry;

import draylar.goml.GetOffMyLawn;
import draylar.goml.block.ClaimAnchorBlock;
import draylar.goml.item.ClaimCoreItem;
import draylar.goml.item.GogglesItem;
import draylar.goml.item.UpgradeKitItem;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;

public class GOMLItems {
    public static List<Item> BASE_ITEMS = new ArrayList<>();

    private static final String[] ROMAN = {"I", "II", "III", "IV", "V", "VI"};

    // Non-craftable, admin-only "Claim Core" items. Displayed to clients as an enchanted Blaze Rod,
    // but registered as distinct items so vanilla blaze rods can't be used in claim anchor recipes.
    public static final Item MAKESHIFT_CORE = registerCore("makeshift_core", 1);
    public static final Item REINFORCED_CORE = registerCore("reinforced_core", 2);
    public static final Item GLISTENING_CORE = registerCore("glistening_core", 3);
    public static final Item CRYSTAL_CORE = registerCore("crystal_core", 4);
    public static final Item EMERADIC_CORE = registerCore("emeradic_core", 5);
    public static final Item WITHERED_CORE = registerCore("withered_core", 6);

    public static final Item REINFORCED_UPGRADE_KIT = registerUpgradeKit("reinforced_upgrade_kit", GOMLBlocks.MAKESHIFT_CLAIM_ANCHOR.getFirst(), GOMLBlocks.REINFORCED_CLAIM_ANCHOR.getFirst(), Items.IRON_INGOT);
    public static final Item GLISTENING_UPGRADE_KIT = registerUpgradeKit("glistening_upgrade_kit", GOMLBlocks.REINFORCED_CLAIM_ANCHOR.getFirst(), GOMLBlocks.GLISTENING_CLAIM_ANCHOR.getFirst(), Items.GOLD_INGOT);
    public static final Item CRYSTAL_UPGRADE_KIT = registerUpgradeKit("crystal_upgrade_kit", GOMLBlocks.GLISTENING_CLAIM_ANCHOR.getFirst(), GOMLBlocks.CRYSTAL_CLAIM_ANCHOR.getFirst(), Items.DIAMOND);
    public static final Item EMERADIC_UPGRADE_KIT = registerUpgradeKit("emeradic_upgrade_kit", GOMLBlocks.CRYSTAL_CLAIM_ANCHOR.getFirst(), GOMLBlocks.EMERADIC_CLAIM_ANCHOR.getFirst(), Items.EMERALD);
    public static final Item WITHERED_UPGRADE_KIT = registerUpgradeKit("withered_upgrade_kit", GOMLBlocks.EMERADIC_CLAIM_ANCHOR.getFirst(), GOMLBlocks.WITHERED_CLAIM_ANCHOR.getFirst(), Items.NETHER_STAR);

    public static final Item GOGGLES = register("goggles", GogglesItem::new);

    private static UpgradeKitItem registerUpgradeKit(String name, ClaimAnchorBlock from, ClaimAnchorBlock to, Item item) {
        return register(name, (s) -> new UpgradeKitItem(s, from, to, item));
    }

    private static Item registerCore(String name, int level) {
        // Fake "毛世界" enchantment: a gray, non-italic lore line rendered like a real enchantment.
        var enchantLore = Component.translatable("enchantment.goml.mao_world")
                .append(" ")
                .append(ROMAN[level - 1])
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(false));

        // "领地核心" display name, non-italic so it doesn't look like a player-renamed item.
        var displayName = Component.translatable("item.goml.claim_core")
                .setStyle(Style.EMPTY.withItalic(false));

        return register(name, (s) -> new ClaimCoreItem(s
                .component(DataComponents.CUSTOM_NAME, displayName)
                .component(DataComponents.LORE, new ItemLore(List.of(enchantLore)))));
    }

    private static <T extends Item> T register(String name, Function<Item.Properties, T> item) {
        var id = GetOffMyLawn.id(name);
        var value = item.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)));
        BASE_ITEMS.add(value);
        return Registry.register(BuiltInRegistries.ITEM, id, value);
    }

    public static void init() {
        // NO-OP
    }

    private GOMLItems() {
        // NO-OP
    }
}
