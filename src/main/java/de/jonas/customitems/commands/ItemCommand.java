package de.jonas.customitems.commands;

import de.jonas.customitems.items.Example;
import dev.jorel.commandapi.CommandAPICommand;

public class ItemCommand {

    public ItemCommand() {

        new CommandAPICommand("CustomItems:items")
                .withPermission("CustomItems.item")
                .withAliases("ci:items")
                .withSubcommand(new CommandAPICommand("example")
                        .executesPlayer(((player, commandArguments) -> {
                            player.getInventory().addItem(new Example().example());
                        }))
                )
                .register();

    }

}
