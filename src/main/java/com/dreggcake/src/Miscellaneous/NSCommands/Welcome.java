package com.dreggcake.src.Miscellaneous.NSCommands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;
import com.dreggcake.src.Utility.Channels;
import com.dreggcake.src.Config.Config;

public class Welcome extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();

        if (guild == null || member == null) return;

        TextChannel welcomeChannel = Channels.WELCOME;
        if (welcomeChannel == null) return;

        long joinTimestamp = member.getTimeJoined().toEpochSecond();
        long accountTimestamp = member.getUser().getTimeCreated().toEpochSecond();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription(
                "Welcome to **" + guild.getName() + "**, " + member.getAsMention() + "! 🎉\n" +
                        "*We're delighted to have you join our community.*\n\n" +

                        "🕒 **Join Date:** <t:" + joinTimestamp + ":F>\n" +
                        "⏳ **Account Created:** <t:" + accountTimestamp + ":F> (<t:" + accountTimestamp + ":R>)\n\n" +

                        "👥 **Members:** " + guild.getMemberCount() + "th member\n\n" +

                        "💻 **Java IP:** **"+Config.getString("messages.ip","your.server.ip")+"**\n" +
                        "🛒 **Store:** "+Config.getString("messages.store","https://your-store.example.com/")
        );

        embed.setFooter(guild.getName(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());
        embed.setColor(Color.BLUE);
        embed.setThumbnail(member.getEffectiveAvatarUrl());
        embed.setTimestamp(Instant.now());

        Button rules = Button
                .link(
                        "https://docs.google.com/document/d/1dHlB99Z8CH49B0DKjXySQEfV8fmOERc8hoqpTCuhuJg/edit?usp=sharing",
                        "Server Rules"
                )
                .withEmoji(Emoji.fromUnicode("📜"));

        Button store = Button
                .link(Config.getString("messages.store","https://your-store.example.com/"), "Server Store")
                .withEmoji(Emoji.fromUnicode("🛒"));

        welcomeChannel.sendMessage("Welcome to **" + guild.getName() + "**, " + member.getAsMention())
                .addEmbeds(embed.build())
                .setComponents(ActionRow.of(rules, store))
                .queue();
    }
}

