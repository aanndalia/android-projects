package com.pong1.pong1;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.pong1.framework.Screen;
import com.pong1.framework.implementation.AndroidGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by stree_001 on 7/18/2015.
 */
public class MainGame extends AndroidGame {
    // Option defaults
    public static int optionsBallSpeed = 5;
    public static boolean optionsIsSinglePlayer = false;
    public static boolean optionsSoundOn = true;
    public static int optionsPlayTo = 3;
    public static boolean optionsEasyTouchMode = false;
    public static boolean optionsUseAI = false;

    enum Mode {
        TWO_PLAYER_MODE, AI_MODE, SINGLE_MODE
    }

    public static Mode gameMode = Mode.TWO_PLAYER_MODE;

    public static ArrayList<Integer> highScores;
    //public static String highScoresPath = "C:/Users/stree_001/AndroidStudioProjects/Pong1";
    public static String highScoresFileName = "highscores.bin";
    //public static String highScoresFileName = "C:/high_scores.dat";

    public static Context appContext;

    @Override
    public Screen getInitScreen() {
        Assets.load(this);
        appContext = getApplicationContext();
        deserializeHighScores();
        return new SplashLoadingScreen(this, appContext);
    }

    @Override
    public void onBackPressed() {
        getCurrentScreen().backButton();
    }

    public void deserializeHighScores() {
        //String fullPath = highScoresPath + "/" + highScoresFileName;
        String fullPath = "";
        try
        {
            //File dir = new File(highScoresPath);
            appContext.getFilesDir().mkdirs();
            fullPath = appContext.getFilesDir().getPath() + "/" + highScoresFileName;
            File file = new File(fullPath);
            if(!file.exists()){
                Log.w("MainGame", "deserializeHighScores: " + fullPath + " does not exist, creating new file");
                if(file.createNewFile()){
                    Log.w("MainGame", "deserializeHighScores: " + fullPath + " was created");
                }
                else {
                    Log.w("MainGame", "deserializeHighScores: " + fullPath + " error occurred");
                }
            }

            Log.d("MainGame", "deserializeHighScores - opening stream at " + fullPath);
            FileInputStream fis = new FileInputStream(fullPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Log.d("MainGame", "deserializeHighScores - deserializing object");
            highScores = (ArrayList<Integer>) ois.readObject();
            Log.d("MainGame", "deserializeHighScores - closing file and object streams");
            ois.close();
            fis.close();
        }catch(IOException ioe){
            Log.w("MainGame", "deserializeHighScores: " + fullPath + " had an IOException");
            ioe.printStackTrace();
            highScores = new ArrayList<Integer>(Collections.nCopies(10, 0));
        }catch(ClassNotFoundException c){
            System.out.println("Class not found");
            Log.w("MainGame", "deserializeHighScores: " + fullPath + " had a ClassNotFoundException");
            c.printStackTrace();
            highScores = new ArrayList<Integer>(Collections.nCopies(10, 0));
        }
    }
}
