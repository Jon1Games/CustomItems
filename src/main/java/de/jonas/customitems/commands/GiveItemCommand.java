package de.jonas.customitems.commands;

import de.jonas.customitems.CustomItems;
import de.jonas.customitems.DataBasePool;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.sql.*;
import de.tr7zw.changeme.nbtapi.NBT;

public class GiveItemCommand {

    MiniMessage mm = MiniMessage.miniMessage();

    public GiveItemCommand() {
        new CommandAPICommand("CustomItem:get")
                .withAliases("ci:get")
                .withPermission("CustomItems.give")
                .withSubcommand(new CommandAPICommand("byName")
                        .withArguments(new StringArgument("Name"))
                        .executesPlayer((player, arsg) -> {
                            String searchItemName = (String) arsg.get("Name");

                            ItemStack itemStack = getTableFromDB(CustomItems.INSTANCE.dbPool, searchItemName);
                            if (itemStack.getType() == Material.BARRIER) {
                                player.sendMessage(mm.deserialize("<red>An item with this name doesnt exists."));
                                return;
                            }
                            player.getInventory().addItem(itemStack);
                        })
                )
                .withSubcommand(new CommandAPICommand("byID")
                        .withArguments(new IntegerArgument("ItemID"))
                        .executesPlayer((player, args) -> {
                            Integer searchItemID = (Integer) args.get("ItemID");
                            if (searchItemID == null) {
                                player.sendMessage(mm.deserialize("<red>Du must eine ID eingeben!</red>"));
                                return;
                            }

                            ItemStack itemStack =getTableFromDBiD(CustomItems.INSTANCE.dbPool, searchItemID);
                            if (itemStack.getType() == Material.BARRIER) {
                                player.sendMessage(mm.deserialize("<red>An item with this ID doesnt exists."));
                                return;
                            }
                            player.getInventory().addItem(itemStack);
                        })
                )
                .register();
    }

    public ItemStack getTableFromDB(DataBasePool pool, String searchItemName) {
        String querry = "SELECT `item`.`itemName`, `item`.`blob` FROM `item` WHERE `item`.`itemName` = ?;";

        try {
            Connection con = pool.getConnection();
            PreparedStatement sel = con.prepareStatement(querry);
            sel.setObject(1, searchItemName);
            ResultSet res = sel.executeQuery();
            res.first();
            Blob blob = res.getBlob("blob");
            ItemStack item = NBT.itemStackFromNBT(new NBTContainer(blob.getBinaryStream()));
            sel.close();
            con.close();
            return item;
        } catch (SQLException e) {
            return new ItemStack(Material.BARRIER);
        }
    }

    public ItemStack getTableFromDBiD(DataBasePool pool, int searchItemID) {
        String querry = "SELECT `item`.`itemID`, `item`.`blob` FROM `item` WHERE `item`.`itemID` = ?;";

        try {
            Connection con = pool.getConnection();
            PreparedStatement sel = con.prepareStatement(querry);
            sel.setObject(1, searchItemID);
            ResultSet res = sel.executeQuery();
            res.first();
            Blob blob = res.getBlob("blob");
            ItemStack item = NBT.itemStackFromNBT(new NBTContainer(blob.getBinaryStream()));
            sel.close();
            con.close();
            return item;
        } catch (SQLException e) {
            return new ItemStack(Material.BARRIER);
        }
    }
}