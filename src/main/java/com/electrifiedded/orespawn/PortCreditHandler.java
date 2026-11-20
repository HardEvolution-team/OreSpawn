package com.electrifiedded.orespawn;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/** Displays the port credits to each player when they join a world. */
public final class PortCreditHandler {
    private PortCreditHandler() {
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.sendMessage(new TextComponentString(TextFormatting.GOLD + "[OreSpawn] "
            + TextFormatting.AQUA + "Ported by " + TextFormatting.WHITE + "ElectrifiedDed"));
        event.player.sendMessage(link("Discord server", "https://discord.gg/UbVvXuAdM8", TextFormatting.LIGHT_PURPLE));
        event.player.sendMessage(link("Author's GitHub", "https://github.com/ElectrifiedDed?tab=repositories", TextFormatting.AQUA));
        event.player.sendMessage(link("Author's CurseForge", "https://www.curseforge.com/members/electrifiedded/projects", TextFormatting.GOLD));
    }

    private static TextComponentString link(String label, String url, TextFormatting color) {
        TextComponentString component = new TextComponentString(color + label + TextFormatting.GRAY + " - " + TextFormatting.UNDERLINE + url);
        component.setStyle(new Style()
            .setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
            .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Open " + label))));
        return component;
    }
}
