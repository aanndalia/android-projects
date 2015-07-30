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
import java.lang.reflect.Array;
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

    public enum Mode {
        TWO_PLAYER_MODE, AI_MODE, SINGLE_MODE
    }

    public enum AiDifficulty {
        EASY, MODERATE, HARD;

        public static AiDifficulty fromInteger(int x) {
            switch(x) {
                case 0:
                    return EASY;
                case 1:
                    return MODERATE;
                case 2:
                    return HARD;
            }
            return EASY;
        }

        public static String valueFromInt(int x) {
            switch(x) {
                case 0:
                    return "Easy";
                case 1:
                    return "Moderate";
                case 2:
                    return "Hard";
            }
            return "";
        }
    }

    public static Mode gameMode = Mode.TWO_PLAYER_MODE;
    public static AiDifficulty aiDifficulty = AiDifficulty.EASY;

    public static ArrayList<Integer> highScores;
    public static ArrayList<Integer> aiHighScores;
    //public static String highScoresPath = "C:/Users/stree_001/AndroidStudioProjects/Pong1";
    public static String highScoresFileName = "highscores.bin";
    public static String ai_highScoresFileName = "aihighscores.bin";
    //public static String highScoresFileName = "C:/high_scores.dat";

    public static Context appContext;

    @Override
    public Screen getInitScreen() {
        Assets.load(this);
        appContext = getApplicationContext();
        highScores = deserializeHighScores(highScoresFileName, 10);
        aiHighScores = deserializeHighScores(ai_highScoresFileName, 3);
        return new SplashLoadingScreen(this, appContext);
    }

    @Override
    public void onBackPressed() {
        getCurrentScreen().backButton();
    }

    public ArrayList<Integer> deserializeHighScores(String fileName, int numLevels) {
        //String fullPath = highScoresPath + "/" + highScoresFileName;
        String fullPath = "";
        try
        {
            //File dir = new File(highScoresPath);
            appContext.getFilesDir().mkdirs();
            fullPath = appContext.getFilesDir().getPath() + "/" + fileName;
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
            ArrayList<Integer> highScores = (ArrayList<Integer>) ois.readObject();
            Log.d("MainGame", "deserializeHighScores - closing file and object streams");
            ois.close();
            fis.close();
            return highScores;
        }catch(IOException ioe){
            Log.w("MainGame", "deserializeHighScores: " + fullPath + " had an IOException");
            ioe.printStackTrace();
            return new ArrayList<Integer>(Collections.nCopies(numLevels, 0));
        }catch(ClassNotFoundException c){
            System.out.println("Class not found");
            Log.w("MainGame", "deserializeHighScores: " + fullPath + " had a ClassNotFoundException");
            c.printStackTrace();
            return new ArrayList<Integer>(Collections.nCopies(numLevels, 0));
        }
    }
}
