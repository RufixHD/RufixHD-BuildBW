package net.Spieleoase.Rufix.BuildBw.APIs;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class TitleAPI {
    public static void sendTitle(Player p, String title, String subtitle) {
        PlayerConnection con = ((CraftPlayer)p).getHandle().playerConnection;
        IChatBaseComponent titlemsg = IChatBaseComponent.ChatSerializer.a("{'text':'" + title + "'}");
        IChatBaseComponent subtitlemsg = IChatBaseComponent.ChatSerializer.a("{'text':'" + subtitle + "'}");
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titlemsg);
        PacketPlayOutTitle titlePacket2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitlemsg);
        con.sendPacket(titlePacket);
        con.sendPacket(titlePacket2);
    }

    public static void sendActionBar(Player player, String msg) {
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\" : \"" + msg + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc,(byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
    }
}
