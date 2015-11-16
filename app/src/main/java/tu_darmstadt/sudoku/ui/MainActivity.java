package tu_darmstadt.sudoku.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import tu_darmstadt.sudoku.game.GameType;
import tu_darmstadt.sudoku.ui.view.R;

public class MainActivity extends AppCompatActivity {

    GameType gameType = GameType.Default_9x9;
    int gameDifficulty = 1;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.scroller);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(1);

        Button continueButton = (Button)findViewById(R.id.continueButton);
        continueButton.setEnabled(false);

    }

    public void onClick(View view) {

        Intent i = null;

        if(view instanceof Button) {
            Button b = (Button)view;
            switch(b.getId()) {
                case R.id.aboutButton:
                    i = new Intent(this, AboutActivity.class);
                    break;
                case R.id.continueButton:
                    // TODO continue from file.
                    i = new Intent(this, GameActivity.class);
                    int levelNr = 0;
                    i.putExtra("loadLevel", levelNr);
                    break;
                case R.id.highscoreButton:
                    // TODO: create highscore screen
                    break;
                case R.id.settingsButton:
                    i = new Intent(this, SettingsActivity.class);
                    break;
                case R.id.helpButton:
                    // TODO: create help page.. what is supposed to be in there?!
                    break;
                case R.id.playButton:
                    gameType = GameType.getValidGameTypes().get(mViewPager.getCurrentItem());
                    RatingBar difficultyBar = (RatingBar)findViewById(R.id.difficultyBar);
                    gameDifficulty = difficultyBar.getProgress();

                    // send everything to game activity
                    i = new Intent(this, GameActivity.class);
                    i.putExtra("gameType", gameType);
                    i.putExtra("gameDifficulty", gameDifficulty);
                    break;
                default:
                    i = getIntent();
            }
        }
        if(i == null) {
            i = getIntent();
        }
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a GameTypeFragment (defined as a static inner class below).
            return GameTypeFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return GameType.getValidGameTypes().size();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class GameTypeFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static GameTypeFragment newInstance(int sectionNumber) {
            GameTypeFragment fragment = new GameTypeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public GameTypeFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);

            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(GameType.getValidGameTypes().get(getArguments().getInt(ARG_SECTION_NUMBER)).name());
            return rootView;
        }
    }
}