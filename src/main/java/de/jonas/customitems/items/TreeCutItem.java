package de.jonas.customitems.items;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.ArrayList;
import java.util.List;

public class TestItem {
    public ItemStack testItem;
    public static final NamespacedKey TEST_ITEM = new NamespacedKey("ci", "test_item");
    public static final int TestItem = 0;
    public TestItem() {
        // make minimessage usable as mm
        var mm = MiniMessage.miniMessage();

        // set the item lore
        List<String> lore = new ArrayList<>();
        lore.add("Line1");
        lore.add("Line2");

        // create item and set the item meta
        ItemStack item = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(mm.deserialize("<gold>An Test Item</gold>"));
            meta.setLore(lore);
            // more meta

            // give the Item ID for listener
            meta.getPersistentDataContainer().set(TEST_ITEM, PersistentDataType.INTEGER, TestItem);

            // give the item the item meta and give the public variable the data
            item.setItemMeta(meta);
            testItem = item;
        }
    }
}
