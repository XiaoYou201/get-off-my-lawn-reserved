package draylar.goml.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;
import org.jetbrains.annotations.Nullable;

/**
 * Server-side only "Claim Core" item. It is displayed to vanilla clients as a
 * Blaze Rod (via Polymer) but is a distinct registered item, so vanilla blaze
 * rods can never be substituted for it in claim anchor recipes.
 *
 * These items have no crafting recipe and are intended to be handed out by
 * admins (via /give or the creative tab).
 */
public class ClaimCoreItem extends Item implements PolymerItem {

    public ClaimCoreItem(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        // Fake enchantment glint, matching the "毛世界" augment look.
        return true;
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
        return Items.BLAZE_ROD;
    }

    @Override
    public @Nullable Identifier getPolymerItemModel(ItemStack stack, PacketContext context, HolderLookup.Provider lookup) {
        return null;
    }
}
