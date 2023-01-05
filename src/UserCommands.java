package org.breaddy;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;
import java.util.Random;

public class UserCommands {
    public String AzirImage(){
        ArrayList<String> list = new ArrayList<>();
        Random rand = new Random();
        list.add("https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Azir_0.jpg");
        list.add("https://www.earlygame.com/uploads/images/_1200x630_crop_center-center_82_none/lol-best-azir-skins.jpeg?mtime=1620721045");
        list.add("https://cdn.vox-cdn.com/thumbor/GF3W04sFplRrlXAYrEo8bV4PyXY=/0x0:1215x717/1200x800/filters:focal(746x45:940x239)/cdn.vox-cdn.com/uploads/chorus_image/image/56911409/azir.0.jpg");
        list.add("https://www.mobafire.com/images/champion/skins/landscape/azir-elderwood-762x.jpg");
        list.add("https://cdn1.dotesports.com/wp-content/uploads/2018/08/12032013/Azir_1.jpg");
        list.add("https://www.wallpapertip.com/wmimgs/95-959071_project-azir.jpg");
        list.add("https://i.pinimg.com/originals/a9/53/1c/a9531cd632439a6e8772c73c96e270a7.jpg");
        list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDzs9BmSbzd7YniA6MMKZwV72jR2BL0w2axw&usqp=CAU");
        list.add("https://i.pinimg.com/550x/30/10/81/301081477a74cbea970d4724d7feffe0.jpg");
        list.add("https://i.pinimg.com/474x/26/51/ef/2651efb2803cbe2ae457934b4c31c409.jpg");
        list.add("https://bit.ly/3oRuKGG");
        list.add("https://pm1.narvii.com/5803/3be41cf7281283dc5e4941a8f7c2021006d5adaa_00.jpg");
        list.add("https://ih1.redbubble.net/image.1488306037.0688/bg,f8f8f8-flat,750x,075,f-pad,750x1000,f8f8f8.jpg");
        list.add("https://pbs.twimg.com/media/EgxGZUcWkAA92Gn.jpg");
        list.add("https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Azir_14.jpg");
        int value = rand.nextInt(0,list.size());
        return list.get(value);
    }

    public void legoshi(SlashCommandInteractionEvent event){
        String content = "uwu\n https://cdn.discordapp.com/attachments/649254714038419467/896944297297989702/video0.mov";
        event.reply(content).queue();
    }

    public void yamit(SlashCommandInteractionEvent event){
        String content = "https://www.twitch.tv/yamatosdeath1\n" + "https://youtu.be/rr9fYWCC3-w";
        event.reply(content).queue();
    }

    public void azir(SlashCommandInteractionEvent event){
        String content = AzirImage();
        event.reply(content).queue();
    }

    public void say(SlashCommandInteractionEvent event, String content){
        event.reply(content).queue();
    }

    public void die(SlashCommandInteractionEvent event){
        Random rand = new Random();
        String content = String.valueOf(rand.nextInt(0,6));
        event.reply("Your Die Rolled " + content).queue();
    }
}
