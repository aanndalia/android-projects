package com.pong1.pong1;

import com.pong1.framework.Image;
import com.pong1.framework.Music;
import com.pong1.framework.Sound;

/**
 * Created by stree_001 on 7/18/2015.
 */
public class Assets {
    public static Image menu, splash, paddle, ball, button;
    public static Sound collision;
    public static Music theme;

    public static void load(MainGame game) {
        // TODO Auto-generated method stub
        theme = game.getAudio().createMusic("pong_music_1.mp3");
        theme.setLooping(true);
        theme.setVolume(0.50f);
        theme.play();

        collision = game.getAudio().createSound("ping_pong_8bit_beeep.ogg");
        //collision.play(0.75f);

    }
}
