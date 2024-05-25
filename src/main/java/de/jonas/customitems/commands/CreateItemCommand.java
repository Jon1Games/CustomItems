package de.jonas.customitems.commands;

import de.jonas.customitems.CustomItems;
import de.jonas.customitems.DataBasePool;
import de.tr7zw.changeme.nbtapi.NBT;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.sql.*;

public class CreateItemCommand {

    MiniMessage mm = MiniMessage.miniMessage();

    public CreateItemCommand() {

        new CommandAPICommand("CustomItems:create")
                .withAliases("ci:create")
                .withPermission("CustomItems.create")
                .withArguments(new StringArgument("Name"))
                .executesPlayer(((player, commandArguments) -> {
                    String itemName = (String) commandArguments.get("Name");

                    ItemStack i = player.getInventory().getItemInMainHand();

                    byte[] blob = itemToBlob(i);

                    setTableFromDB(CustomItems.INSTANCE.dbPool, itemName, blob);

                    int id = getTableFromDB(CustomItems.INSTANCE.dbPool, itemName);

                    Component message =mm.deserialize("You created the item <item>" +
                                            " with name \"<green><name></green>\"" +
                                            " and the ID: <green><id></green>.",
                                            Placeholder.component("item", i.displayName()),
                                            Placeholder.component("id", Component.text(id)),
                                            Placeholder.component("name", Component.text(itemName)));

                    player.sendMessage(message);
                }))
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

    public int getTableFromDB(DataBasePool pool, String searchItemName) {
        String querry = "SELECT `item`.`itemID` FROM `item` WHERE `item`.`itemName` = ?;";

        try {
            Connection con = pool.getConnection();
            PreparedStatement sel = con.prepareStatement(querry);
            sel.setObject(1, searchItemName);
            ResultSet res = sel.executeQuery();
            res.first();
            int id = res.getInt("itemID");
            sel.close();
            con.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
