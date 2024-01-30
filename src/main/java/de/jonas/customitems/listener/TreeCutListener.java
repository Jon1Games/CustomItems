package de.jonas.customitems.listener;


import de.jonas.customitems.items.TreeCutItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;

public class TreeCutListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        // Check if the broken block is a log block
        if (block.getType() == Material.OAK_LOG || block.getType() == Material.BIRCH_LOG ||
                block.getType() == Material.SPRUCE_LOG || block.getType() == Material.JUNGLE_LOG ||
                block.getType() == Material.ACACIA_LOG || block.getType() == Material.DARK_OAK_LOG) {

            //
            if (player.getInventory().getItemInMainHand() == null) return;
            if (player.getInventory().getItemInMainHand().getItemMeta()== null) return;
            if (!player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(TreeCutItem.TEST_ITEM, PersistentDataType.INTEGER)) return;
            // Check if the used tool is an spezified tool
            if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(TreeCutItem.TEST_ITEM, PersistentDataType.INTEGER) == 0) {
                // Cancel the event to prevent normal block breaking
                event.setCancelled(true);

                // Break the entire tree
                breakTree(block);
            }
        }
    }

    private void breakTree(Block block) {
        // Break the initial log block
        block.breakNaturally();

        // Recursively break connected log blocks above, below, and around
        breakTreeRecursive(block.getRelative(0, 1, 0)); // Above
        breakTreeRecursive(block.getRelative(0, -1, 0)); // Below
        breakTreeRecursive(block.getRelative(1, 0, 0)); // East
        breakTreeRecursive(block.getRelative(-1, 0, 0)); // West
        breakTreeRecursive(block.getRelative(0, 0, 1)); // South
        breakTreeRecursive(block.getRelative(0, 0, -1)); // North
    }

    private void breakTreeRecursive(Block block) {
        // Break the log block and continue breaking connected log blocks
        if (block.getType() == Material.OAK_LOG || block.getType() == Material.BIRCH_LOG ||
                block.getType() == Material.SPRUCE_LOG || block.getType() == Material.JUNGLE_LOG ||
                block.getType() == Material.ACACIA_LOG || block.getType() == Material.DARK_OAK_LOG) {

            block.breakNaturally();

            breakTreeRecursive(block.getRelative(0, 1, 0)); // Above
            breakTreeRecursive(block.getRelative(0, -1, 0)); // Below
            breakTreeRecursive(block.getRelative(1, 0, 0)); // East
            breakTreeRecursive(block.getRelative(-1, 0, 0)); // West
            breakTreeRecursive(block.getRelative(0, 0, 1)); // South
            breakTreeRecursive(block.getRelative(0, 0, -1)); // North
        }
    }
}
