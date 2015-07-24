package com.pong1.pong1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.pong1.pong1.R;

public class OptionsActivity2 extends Activity {
    CheckBox volumeCheckbox;
    CheckBox singlePlayerCheckbox;
    EditText playToEditText;
    EditText ballSpeedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_activity2);
        System.out.println("onCreate");

        volumeCheckbox = (CheckBox) findViewById(R.id.volumeCheckbox);
        volumeCheckbox.setChecked(MainGame.optionsSoundOn);

        singlePlayerCheckbox = (CheckBox) findViewById(R.id.singlePlayerCheckbox);
        singlePlayerCheckbox.setChecked(MainGame.optionsIsSinglePlayer);

        playToEditText = (EditText) findViewById(R.id.playToInput);
        playToEditText.setText(Integer.toString(MainGame.optionsPlayTo));

        ballSpeedEditText = (EditText) findViewById(R.id.ballSpeedInput);
        ballSpeedEditText.setText(Integer.toString(MainGame.optionsBallSpeed));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void saveClickedHandler(View view) {
        System.out.println("Saving settings");
        MainGame.optionsBallSpeed = Integer.parseInt(ballSpeedEditText.getText().toString());
        MainGame.optionsIsSinglePlayer = singlePlayerCheckbox.isChecked();
        MainGame.optionsSoundOn = volumeCheckbox.isChecked();
        MainGame.optionsPlayTo = Integer.parseInt(playToEditText.getText().toString());

        if(MainGame.optionsPlayTo == 0)
            MainGame.optionsPlayTo = 1;

        // debug options data
        System.out.println("In saved handler");
        System.out.println("play to: " + Integer.toString(MainGame.optionsPlayTo));
        System.out.println("ball speed: " + Integer.toString(MainGame.optionsBallSpeed));
        System.out.print("single player: ");
        System.out.println(MainGame.optionsIsSinglePlayer);
        System.out.print("volume on: ");
        System.out.println(MainGame.optionsSoundOn);

        Assets.theme.setVolume(MainGame.optionsSoundOn == true ? 0.50f : 0.0f);
        Assets.theme.play();

        finish();
    }

    public void defaultsClickedHandler(View view) {
        System.out.println("Setting defaults");
        volumeCheckbox = (CheckBox) findViewById(R.id.volumeCheckbox);
        volumeCheckbox.setChecked(true);

        singlePlayerCheckbox = (CheckBox) findViewById(R.id.singlePlayerCheckbox);
        singlePlayerCheckbox.setChecked(false);

        playToEditText = (EditText) findViewById(R.id.playToInput);
        playToEditText.setText("3");

        ballSpeedEditText = (EditText) findViewById(R.id.ballSpeedInput);
        ballSpeedEditText.setText("5");
    }
}
