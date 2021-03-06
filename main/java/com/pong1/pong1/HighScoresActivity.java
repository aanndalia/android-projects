package com.pong1.pong1;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.pong1.pong1.R;

import java.util.ArrayList;
import java.util.Arrays;

public class HighScoresActivity extends Activity {

    TableLayout scoreTableLayout;
    TableLayout aiScoresTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        scoreTableLayout = (TableLayout) findViewById(R.id.scoresTableLayout);
        aiScoresTableLayout = (TableLayout) findViewById(R.id.aiScoresTableLayout);

        Spinner dropdown = (Spinner)findViewById(R.id.modeSpinner);
        String[] items = new String[]{"1P vs Wall", "1P vs AI"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 0) {
                    // Singles mode
                    scoreTableLayout.setVisibility(View.VISIBLE);
                    aiScoresTableLayout.setVisibility(View.GONE);
                }
                else if(position == 1) {
                    // AI mode
                    scoreTableLayout.setVisibility(View.GONE);
                    aiScoresTableLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        createScoresTable();
        createAiScoresTable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_scores, menu);
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

    public void createScoresTable() {
        // Get the TableLayout
        scoreTableLayout.setStretchAllColumns(true);

        // Create header row
        ArrayList<String> rowStrValues = new ArrayList<String>(Arrays.asList("Speed", "Score"));

        TableRow headerTR = populateRow(50, rowStrValues);
        scoreTableLayout.addView(headerTR, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        // Go through each item in the array
        for (int current = 0; current < MainGame.highScores.size(); current++)
        {
            rowStrValues = new ArrayList<String>(Arrays.asList(Integer.toString(current), Integer.toString(MainGame.highScores.get(current))));
            TableRow tr = populateRow(current, rowStrValues);

            // Add the TableRow to the TableLayout
            scoreTableLayout.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    public void createAiScoresTable() {
        // Get the TableLayout
        aiScoresTableLayout.setStretchAllColumns(true);

        // Create header row
        ArrayList<String> rowStrValues = new ArrayList<String>(Arrays.asList("Difficulty", "Wins", "Loss", "%Win"));
        TableRow headerTR = populateRow(50, rowStrValues);
        aiScoresTableLayout.addView(headerTR, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        // Go through each item in the array
        for (int current = 0; current < MainGame.aiHighScores.size(); current++)
        {
            int wins = MainGame.aiHighScores.get(current);
            int losses = MainGame.aiModeLosses.get(current);
            float winPercent = 0.00f;
            if(wins != 0 || losses != 0) {
                winPercent = 100 * ((float) wins) / (wins + losses);
            }

            String strWinPercent = String.format("%.2f", winPercent);

            rowStrValues = new ArrayList<String>(Arrays.asList(MainGame.AiDifficulty.valueFromInt(current), Integer.toString(wins), Integer.toString(losses), strWinPercent));
            TableRow tr = populateRow(current, rowStrValues);
            // Add the TableRow to the TableLayout
            aiScoresTableLayout.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    public TableRow populateRow(int index, ArrayList<String> rowStrValues){
        // Create a TableRow and give it an ID
        TableRow tr = new TableRow(this);
        tr.setId(100+index);
        tr.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        for(int i=0; i < rowStrValues.size(); i++) {
            // Create a TextView to house the name of the first
            TextView labelTV = new TextView(this);
            labelTV.setId(200 + 10*i + index);
            labelTV.setText(rowStrValues.get(i));
            labelTV.setTextColor(Color.WHITE);
            labelTV.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            tr.addView(labelTV);
        }
        return tr;
    }
}
