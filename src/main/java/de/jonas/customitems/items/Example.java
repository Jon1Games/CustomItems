package de.jonas.customitems.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Example {

    public ItemStack example() {

        ItemStack i = new ItemStack(Material.COAL);
        // more i.*
        ItemMeta m = i.getItemMeta();
        // more item Meta
        i.setItemMeta(m);

        return i;
    }

}
