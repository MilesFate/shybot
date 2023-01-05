package org.breaddy;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.EnumSet;
import java.util.Objects;

import static net.dv8tion.jda.api.interactions.commands.OptionType.*;

public class SlashBot extends ListenerAdapter{

    public static void main(String[] args){
        // These commands might take a few minutes to be active after creation/update/delete
        Secret s = new Secret();
        String Token = s.getSecret();
        JDA jda = JDABuilder.createLight(Token, EnumSet.noneOf(GatewayIntent.class)).addEventListeners(new SlashBot()).build();
        CommandListUpdateAction commands = jda.updateCommands();

        // Moderation commands with required options
        commands.addCommands(
                Commands.slash("ban", "Ban a user from this server. Requires permission to ban users.")
                        .addOptions(new OptionData(USER, "user", "The user to ban")
                                .setRequired(true))
                        .addOptions(new OptionData(INTEGER, "del_days", "Delete messages from the past days.")
                                .setRequiredRange(0, 7))
                        .addOptions(new OptionData(STRING, "reason", "The ban reason to use (default: Banned by <user>)"))
                        .setGuildOnly(true)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS))
        );

        // Commands without any inputs
        commands.addCommands(
                Commands.slash("leave", "Make the bot leave the server")
                        .setGuildOnly(true)
                        .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
        );

        commands.addCommands(
                Commands.slash("prune", "Prune messages from this channel")
                        .addOption(INTEGER, "amount", "How many messages to prune (Default 100)")
                        .setGuildOnly(true)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE))
        );

        // No Permissions needed
        commands.addCommands(
                Commands.slash("say", "Makes the bot say what you tell it to")
                        .addOption(STRING, "content", "What the bot should say", true)
        );

        commands.addCommands(
                Commands.slash("die", "Roll Die")
        );

        commands.addCommands(
                Commands.slash("azir", "Get A Random Photo of my Favorite League of Legends Champ")
        );

        commands.addCommands(
                Commands.slash("legoshi", "Beastars MC spooks his love")
        );

        commands.addCommands(
                Commands.slash("yamit", "All you Yamit")
        );

        commands.queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        AdminCommands ac = new AdminCommands();
        UserCommands uc = new UserCommands();

        if (event.getGuild() == null)
            return;
        switch (event.getName()){
            case "ban" -> {
                Member member = Objects.requireNonNull(event.getOption("user")).getAsMember();
                User user = Objects.requireNonNull(event.getOption("user")).getAsUser();
                ac.ban(event, user, member);
            }
            case "prune" -> ac.prune(event);
            case "leave" -> ac.leave(event);
            // below are the non admin commands
            case "say" -> uc.say(event, Objects.requireNonNull(event.getOption("content")).getAsString());
            case "die" -> uc.die(event);
            case "yamit" -> uc.yamit(event);
            case "legoshi" -> uc.legoshi(event);
            case "azir" -> uc.azir(event);
            default -> event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event){
        String[] id = event.getComponentId().split(":"); // this is the custom id we specified in our button
        String authorId = id[0];
        String type = id[1];
        // Check that the button is for the user that clicked it, otherwise just ignore the event (let interaction fail)
        if (!authorId.equals(event.getUser().getId()))
            return;
        event.deferEdit().queue(); // acknowledge the button was clicked, otherwise the interaction will fail

        MessageChannel channel = event.getChannel();
        switch (type){
            case "prune":
                int amount = Integer.parseInt(id[2]);
                event.getChannel().getIterableHistory()
                        .skipTo(event.getMessageIdLong())
                        .takeAsync(amount)
                        .thenAccept(channel::purgeMessages);
                // fallthrough delete the prompt message with our buttons
            case "delete":
                event.getHook().deleteOriginal().queue();
        }
    }
}