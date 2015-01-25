package pl.czak.cuanto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Locale;

import pl.czak.cuanto.models.Quiz;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends Activity {
    private static final String KEY_SEED = "seed";
    private static final String KEY_FIRST_RUN = "firstRun";
    private static final String KEY_LAST_PAGE = "lastPage";
    private static final int NUM_INTRO_PAGES = 3;

    // Aktywna "talia" kart
    Quiz quiz = new Quiz();

    // Czy jest to pierwsze uruchomienie aplikacji?
    boolean firstRun = false;

    ViewPager pager;

    public Quiz getQuiz() {
        return quiz;
    }

    class QuizPagerAdapter extends FragmentStatePagerAdapter {
        public QuizPagerAdapter() {
            super(getFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            if (firstRun && position < NUM_INTRO_PAGES)
                return IntroFragment.create(position);
            else
                return CardFragment.create(position);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
    }

    class QuizPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int page) {
            setControls(page);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ostatnia odwiedzona strona
        int lastPage = 0;

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        if (prefs.getBoolean(KEY_FIRST_RUN, true)) {
            // To będzie wykonane tylko za pierwszym uruchomieniem
            firstRun = true;

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(KEY_FIRST_RUN, false);
            editor.commit();
        }

        if (savedInstanceState != null) {
            firstRun = savedInstanceState.getBoolean(KEY_FIRST_RUN, false);
            lastPage = savedInstanceState.getInt(KEY_LAST_PAGE, 0);

            if (savedInstanceState.containsKey(KEY_SEED)) {
                quiz.setSeed(savedInstanceState.getLong(KEY_SEED));
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControls(lastPage);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new QuizPagerAdapter());
        pager.setOnPageChangeListener(new QuizPageChangeListener());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_SEED, quiz.getSeed());
        outState.putBoolean(KEY_FIRST_RUN, firstRun);
        outState.putInt(KEY_LAST_PAGE, pager.getCurrentItem());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                new AboutDialogFragment().show(getFragmentManager(), "About");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Ustawia UI odpowiednie dla wskazanej strony
     * @param page Numer strony
     */
    private void setControls(int page) {
        String tag;
        if (firstRun && page < NUM_INTRO_PAGES)
            tag = ControlsFragment.TAG_INTRO;
        else
            tag = ControlsFragment.TAG_QUIZ;

        FragmentManager fragmentManager = getFragmentManager();

        // Jeśli docelowy fragment już jest na miejscu to nic nie robimy
        if (fragmentManager.findFragmentByTag(tag) != null)
            return;

        // W przeciwnym razie podmianka
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_controls,
                ControlsFragment.newInstance(tag), tag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    public void showAnswer(View view) {
        FragmentStatePagerAdapter a = (FragmentStatePagerAdapter) pager.getAdapter();
        CardFragment f = (CardFragment) a.instantiateItem(pager, pager.getCurrentItem());
        f.showAnswer();
    }

    public void speak(View view) {
        String text = quiz.getCard(pager.getCurrentItem()).getAnswer();
        MyApplication app = (MyApplication) getApplication();
        app.getTts().speak(text + ".", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void nextPage(View view) {
        pager.setCurrentItem(pager.getCurrentItem() + 1);
    }
}
