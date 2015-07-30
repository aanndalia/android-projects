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

        TableRow headerTR = populateRow(50, "Speed", "Score");
        scoreTableLayout.addView(headerTR, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        // Go through each item in the array
        for (int current = 0; current < MainGame.highScores.size(); current++)
        {
            /*
            // Create a TableRow and give it an ID
            TableRow tr = new TableRow(this);
            tr.setId(100+current);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Create a TextView to house the name of the province
            TextView labelTV = new TextView(this);
            labelTV.setId(200+current);
            labelTV.setText(Integer.toString(current));
            labelTV.setTextColor(Color.WHITE);
            labelTV.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            tr.addView(labelTV);

            // Create a TextView to house the value of the after-tax income
            TextView valueTV = new TextView(this);
            valueTV.setId(current);

            valueTV.setText(Integer.toString(MainGame.highScores.get(current)));
            valueTV.setTextColor(Color.WHITE);
            valueTV.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            tr.addView(valueTV);
            */

            TableRow tr = populateRow(current, Integer.toString(current), Integer.toString(MainGame.highScores.get(current)));
            // Add the TableRow to the TableLayout
            scoreTableLayout.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    public void createAiScoresTable() {
        // Get the TableLayout
        aiScoresTableLayout.setStretchAllColumns(true);

        TableRow headerTR = populateRow(50, "Difficulty", "Wins");
        aiScoresTableLayout.addView(headerTR, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        // Go through each item in the array
        for (int current = 0; current < MainGame.aiHighScores.size(); current++)
        {
            TableRow tr = populateRow(current, MainGame.AiDifficulty.valueFromInt(current), Integer.toString(MainGame.aiHighScores.get(current)));
            // Add the TableRow to the TableLayout
            aiScoresTableLayout.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    public TableRow populateRow(int index, String left, String right){
        // Create a TableRow and give it an ID
        TableRow tr = new TableRow(this);
        tr.setId(100+index);
        tr.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        // Create a TextView to house the name of the province
        TextView labelTV = new TextView(this);
        labelTV.setId(200+index);
        labelTV.setText(left);
        labelTV.setTextColor(Color.WHITE);
        labelTV.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(labelTV);

        // Create a TextView to house the value of the after-tax income
        TextView valueTV = new TextView(this);
        valueTV.setId(index);
        valueTV.setText(right);
        valueTV.setTextColor(Color.WHITE);
        valueTV.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(valueTV);

        return tr;
    }
}
