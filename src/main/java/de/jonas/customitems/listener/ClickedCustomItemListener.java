package de.jonas.customitems.listener;

import de.jonas.customitems.items.TestItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

public class TestItemListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        var itemData = e.getItem().getItemMeta().getPersistentDataContainer();
        if (itemData.has(TestItem.TEST_ITEM, PersistentDataType.INTEGER) && e.getAction().isRightClick() == true) {
            switch (itemData.get(TestItem.TEST_ITEM, PersistentDataType.INTEGER))
        }


            e.getPlayer().sendMessage("Yeah");
        }
    }
}
