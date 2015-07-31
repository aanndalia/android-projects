package com.pong1.pong1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.pong1.pong1.R;

public class OptionsActivity2 extends Activity {
    CheckBox volumeCheckbox;
    CheckBox singlePlayerCheckbox;
    CheckBox easyTouchCheckbox;
    EditText playToEditText;
    EditText ballSpeedEditText;
    TextView highScoreValueTextView;
    CheckBox aiCheckbox;
    Spinner aiDifficultyDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_activity2);
        System.out.println("onCreate");

        aiDifficultyDropdown = (Spinner)findViewById(R.id.aiDifficultySpinner);
        String[] items = new String[]{"Easy", "Moderate", "Hard"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        aiDifficultyDropdown.setAdapter(adapter);

        aiDifficultyDropdown.setSelection(MainGame.aiDifficulty.ordinal());

        volumeCheckbox = (CheckBox) findViewById(R.id.volumeCheckbox);
        volumeCheckbox.setChecked(MainGame.optionsSoundOn);

        singlePlayerCheckbox = (CheckBox) findViewById(R.id.singlePlayerCheckbox);
        singlePlayerCheckbox.setChecked(MainGame.optionsIsSinglePlayer);

        playToEditText = (EditText) findViewById(R.id.playToInput);
        playToEditText.setText(Integer.toString(MainGame.optionsPlayTo));

        ballSpeedEditText = (EditText) findViewById(R.id.ballSpeedInput);
        ballSpeedEditText.setText(Integer.toString(MainGame.optionsBallSpeed));

        highScoreValueTextView = (TextView) findViewById(R.id.highScoreValueTextView);
        highScoreValueTextView.setText(Integer.toString(MainGame.highScores.get(MainGame.optionsBallSpeed)));

        easyTouchCheckbox = (CheckBox) findViewById(R.id.easyTouchCheckbox);
        easyTouchCheckbox.setChecked(MainGame.optionsEasyTouchMode);

        aiCheckbox = (CheckBox) findViewById(R.id.aiCheckbox);
        aiCheckbox.setChecked(MainGame.optionsUseAI);
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
        MainGame.optionsEasyTouchMode = easyTouchCheckbox.isChecked();
        MainGame.optionsUseAI = aiCheckbox.isChecked();

        if(MainGame.optionsPlayTo == 0)
            MainGame.optionsPlayTo = 1;

        MainGame.aiDifficulty = MainGame.AiDifficulty.fromInteger(aiDifficultyDropdown.getSelectedItemPosition());

        // debug options data
        System.out.println("In saved handler");
        System.out.println("play to: " + Integer.toString(MainGame.optionsPlayTo));
        System.out.println("ball speed: " + Integer.toString(MainGame.optionsBallSpeed));
        System.out.print("single player: ");
        System.out.println(MainGame.optionsIsSinglePlayer);
        System.out.print("volume on: ");
        System.out.println(MainGame.optionsSoundOn);
        System.out.print("easy touch mode on: ");
        System.out.println(MainGame.optionsEasyTouchMode);
        System.out.print("AI mode on: ");
        System.out.println(MainGame.optionsUseAI);
        System.out.print("AI Difficulty: ");
        System.out.println(Integer.toString(aiDifficultyDropdown.getSelectedItemPosition()));

        Assets.theme.setVolume(MainGame.optionsSoundOn == true ? 0.50f : 0.0f);

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

        easyTouchCheckbox.setChecked(false);

        aiCheckbox.setChecked(false);

        System.out.println("EASY ordinal " + Integer.toString(MainGame.AiDifficulty.EASY.ordinal()) + ", " + Integer.toString(MainGame.AiDifficulty.HARD.ordinal()));
        aiDifficultyDropdown.setSelection(MainGame.AiDifficulty.MODERATE.ordinal());
    }
}
