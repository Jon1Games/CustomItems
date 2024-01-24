package de.jonas.customitems.commands;

import de.jonas.customitems.items.TreeCutItem;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;

public class GiveItemCommand {

    public GiveItemCommand() {
        // Create new Command
        new CommandAPICommand("customitem")
                .withAliases("customitems", "ci")
                .withPermission(CommandPermission.OP)
                .withSubcommand(new CommandAPICommand("TestItem")
                        .executesPlayer((player, args) -> {
                            TreeCutItem item = new TreeCutItem();
                            player.getInventory().addItem(item.testItem);
                        })
                )
                .register();
    }
}
