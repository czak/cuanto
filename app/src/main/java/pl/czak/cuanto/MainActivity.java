package pl.czak.cuanto;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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
            return CardFragment.create(position);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
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

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new QuizPagerAdapter());

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
