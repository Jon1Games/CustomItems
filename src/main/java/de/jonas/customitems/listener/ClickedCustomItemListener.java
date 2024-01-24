package de.jonas.customitems.listener;

import de.jonas.customitems.items.TreeCutItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

public class ClickedCustomItemListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        var itemData = e.getItem().getItemMeta().getPersistentDataContainer();
        if (itemData.has(TreeCutItem.TEST_ITEM, PersistentDataType.INTEGER) && e.getAction().isRightClick() == true) {
            switch (itemData.get(TreeCutItem.TEST_ITEM, PersistentDataType.INTEGER)) {
                default -> {
                    return;
                }
            }
        }
    }
}
