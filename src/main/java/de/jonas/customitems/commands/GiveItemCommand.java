package de.jonas.customitems.commands;

import de.jonas.customitems.CustomItems;
import de.jonas.customitems.DataBasePool;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.sql.*;

import de.tr7zw.changeme.nbtapi.NBT;

public class GiveItemCommand {

    public GiveItemCommand() {
        new CommandAPICommand("CustomItem")
                .withAliases("CItem")
                .withSubcommand(new CommandAPICommand("new")
                        .withArguments(new StringArgument("Name"))
                        .executesPlayer((player, args) -> {
                            String itemName = (String) args.get("Name");

                            byte[] blob = itemToBlob(player.getInventory().getItemInMainHand());

                            setTableFromDB(CustomItems.INSTANCE.dbPool, itemName, blob);
                        })
                )
                .withSubcommand(new CommandAPICommand("getByName")
                        .withArguments(new StringArgument("Name"))
                        .executesPlayer((player, arsg) -> {
                            String searchItemName = (String) arsg.get("Name");

                            player.getInventory().addItem((getTableFromDB(CustomItems.INSTANCE.dbPool, searchItemName)));
                        })
                )
                .withSubcommand(new CommandAPICommand("getByID")
                        .withArguments(new IntegerArgument("ItemID"))
                        .executesPlayer((player, args) -> {
                            int searchItemID = (int) args.get("itemID");

                            player.getInventory().addItem(getTableFromDBiD(CustomItems.INSTANCE.dbPool, searchItemID));
                        })
                )
                .register();
    }

    public static void setTableFromDB(DataBasePool pool, String itemName, byte[] blob) {
        String querry = "INSERT INTO `item` (`itemName`, `blob`) VALUES (?, ?);";

        try {
            Connection con = pool.getConnection();
            PreparedStatement sel = con.prepareStatement(querry);
            sel.setObject(1, itemName);
            sel.setObject(2, blob);
            sel.executeUpdate();
            sel.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    byte[] itemToBlob(ItemStack i) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        NBT.itemStackToNBT(i).writeCompound(bos);
        return bos.toByteArray();
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
            e.printStackTrace();
            return null;
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
            e.printStackTrace();
            return null;
        }
    }
}