/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.scorekeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.BreakIterator;

/**
 * Implements a basic score keeper with plus and minus buttons for 2 teams.
 * - Clicking the plus button increments the score by 1.
 * - Clicking the minus button decrements the score by 1.
 */
public class MainActivity extends AppCompatActivity {
    private int mScore1;
    private int mScore2;

    private TextView mShowCountTextView;
    private TextView mShowCount1TextView;

    // Member variables for the two score TextView elements
    private TextView mScoreText1;
    private TextView mScoreText2;

    // Tags to be used as the keys in OnSavedInstanceState
    static final String STATE_SCORE_1 = "Team 1 Score";
    static final String STATE_SCORE_2 = "Team 2 Score";

    //Shared preference object
    private SharedPreferences mPreference;

    //Name of shared preference file
    private String sharedPrefFile =
            "com.example.android.scorekeeper";
    private Thread view;
    private Menu menu;
    private Bundle outState;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
        // Change the label of the menu based on the state of the app.
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else{
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the TextView by ID
        mScoreText1 = (TextView) findViewById(R.id.score_1);
        mScoreText2 = (TextView) findViewById(R.id.score_2);

        // Restores the scores if there is savedInstanceState.
        if (savedInstanceState != null) {
            mScore1 = savedInstanceState.getInt(STATE_SCORE_1);
            mScore2 = savedInstanceState.getInt(STATE_SCORE_2);

            mPreference = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

            //Set the score text views
            mScoreText1.setText(String.valueOf(mScore1));
            mScoreText2.setText(String.valueOf(mScore2));
        }
        /**
         * Handles the onClick of both the decrement buttons.
         *
         * @param view The button view that was clicked
         */
        public void decreaseScore (View this.view){
            // Get the ID of the button that was clicked.
            int viewID = (int) this.view.getId();
            switch (viewID) {
                // If it was on Team 1:
                case R.id.decreaseTeam1:
                    // Decrement the score and update the TextView.
                    mScore1--;
                    mScoreText1.setText(String.valueOf(mScore1));
                    break;
                // If it was Team 2:
                case R.id.decreaseTeam2:
                    // Decrement the score and update the TextView.
                    mScore2--;
                    mScoreText2.setText(String.valueOf(mScore2));
            }
        }

        /**
         * Handles the onClick of both the increment buttons.
         *
         * @param view The button view that was clicked
         */
        public void increaseScore (View this.view){
            // Get the ID of the button that was clicked.
            int viewID = (int) this.view.getId();
            switch (viewID) {
                // If it was on Team 1:
                case R.id.increaseTeam1:
                    // Increment the score and update the TextView.
                    mScore1++;
                    mScoreText1.setText(String.valueOf(mScore1));
                    break;
                // If it was Team 2:
                case R.id.increaseTeam2:
                    // Increment the score and update the TextView.
                    mScore2++;
                    mScoreText2.setText(String.valueOf(mScore2));
            }
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.main_menu, menu);
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
            } else {
                menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
            }
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Check if the correct item was clicked.
            if (item.getItemId() == R.id.night_mode) {
                // Get the night mode state of the app.
                return true;
            }
            if(item.getItemId()==R.id.night_mode){
                // Get the night mode state of the app.
                int nightMode = AppCompatDelegate.getDefaultNightMode();
                //Set the theme mode for the restarted activity
                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode
                            (AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode
                            (AppCompatDelegate.MODE_NIGHT_YES);
                }
// Recreate the activity for the theme change to take effect.
                recreate();
            }
        }

        @Override
        protected void onSaveInstanceState (Bundle outState){
            // Save the scores.
            outState.putInt(STATE_SCORE_1, mScore1);
            outState.putInt(STATE_SCORE_2, mScore2);
            super.onSaveInstanceState(outState);
        }

        public void reset (View this.view){
            // Reset count
            mScore2 = 0;
            mShowCountTextView.setText(String.format("%s", mScore1));

            // Clear preferences
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.clear();
            preferencesEditor.apply();

            static final String STATE_SCORE_1 = "Team 1 Score";
            static final String STATE_SCORE_2 = "Team 2 Score";

            if (savedInstanceState != null) {
                mScore1 = savedInstanceState.getInt(STATE_SCORE_1);
                mScore2 = savedInstanceState.getInt(STATE_SCORE_2);

                //Set the score text views
                mScoreText1.setText(String.valueOf(mScore1));
                mScoreText2.setText(String.valueOf(mScore2));
            }
            }
        }

        public void reset1 (View final Thread view = this.view;){
            // Reset count
            mScore1 = 0;
            mShowCount1TextView.setText(String.format("%s", mScore1));

            // Clear preferences
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.clear();
            preferencesEditor.apply();
        }

        @Override
        protected void onPause() {
            super.onPause();

            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putInt(STATE_SCORE_1, mScore1);
            preferencesEditor.putInt(STATE_SCORE_2, mScore2);
            preferencesEditor.apply();
        }


    }
}
