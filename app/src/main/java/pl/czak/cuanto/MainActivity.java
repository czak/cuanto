package pl.czak.cuanto;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.util.Locale;

import pl.czak.cuanto.models.Quiz;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends Activity implements TextToSpeech.OnInitListener {
    private static final String KEY_SEED = "seed";
    private static final String KEY_FIRST_RUN = "firstRun";
    private static final int NUM_INTRO_PAGES = 1;

    Quiz quiz;

    TextToSpeech tts;

    boolean firstRun = false;

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
        quiz = new Quiz();
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_SEED)) {
            quiz.setSeed(savedInstanceState.getLong(KEY_SEED));
        }

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        if (prefs.getBoolean(KEY_FIRST_RUN, true)) {
            // To będzie wykonane tylko za pierwszym uruchomieniem
            firstRun = true;

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(KEY_FIRST_RUN, false);
            editor.commit();
        }
        else if (savedInstanceState != null && savedInstanceState.containsKey(KEY_FIRST_RUN)) {
            firstRun = savedInstanceState.getBoolean(KEY_FIRST_RUN);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControls(0);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new QuizPagerAdapter());
        pager.setOnPageChangeListener(new QuizPageChangeListener());

        tts = new TextToSpeech(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_SEED, quiz.getSeed());
        outState.putBoolean(KEY_FIRST_RUN, firstRun);
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
    public void onInit(int status) {
        tts.setLanguage(new Locale("es"));
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

        Log.i("Activity", "setControls: " + page + ", tag: " + tag);

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
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        FragmentStatePagerAdapter a = (FragmentStatePagerAdapter) pager.getAdapter();
        CardFragment f = (CardFragment) a.instantiateItem(pager, pager.getCurrentItem());
        f.showAnswer();
    }

    public void speak(View view) {
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        String text = quiz.getCard(pager.getCurrentItem()).getAnswer();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void nextPage(View view) {
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setCurrentItem(pager.getCurrentItem() + 1);
    }
}
