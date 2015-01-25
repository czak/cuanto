package pl.czak.cuanto;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ToggleButton;

import java.util.Locale;
import java.util.Random;

import pl.czak.cuanto.models.Quiz;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends Activity implements TextToSpeech.OnInitListener {
    private static final String SEED = "seed";

    Quiz quiz;

    TextToSpeech tts;

    public Quiz getQuiz() {
        return quiz;
    }

    class QuizPagerAdapter extends FragmentStatePagerAdapter {
        public QuizPagerAdapter() {
            super(getFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            if (position < 1)
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
        public void onPageSelected(int position) {
            FragmentManager fragmentManager = getFragmentManager();

            // Jeśli jest już docelowy fragment to nie potrzeba podmieniać
            if (position == 0) {
                if (fragmentManager.findFragmentByTag("CONTROLS_INTRO") != null)
                    return;
            }
            else {
                if (fragmentManager.findFragmentByTag("CONTROLS_QUIZ") != null)
                    return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (position == 0) {
                fragmentTransaction.replace(R.id.container_controls,
                        ControlsFragment.newInstance(ControlsFragment.LAYOUT_INTRO),
                        "CONTROLS_INTRO");
            }
            else {
                fragmentTransaction.replace(R.id.container_controls,
                        ControlsFragment.newInstance(ControlsFragment.LAYOUT_QUIZ),
                        "CONTROLS_QUIZ");
            }

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        quiz = new Quiz();
        if (savedInstanceState != null && savedInstanceState.containsKey(SEED)) {
            quiz.setSeed(savedInstanceState.getLong(SEED));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_controls, ControlsFragment.newInstance(ControlsFragment.LAYOUT_INTRO));
        fragmentTransaction.commit();

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
        outState.putLong(SEED, quiz.getSeed());
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
}
