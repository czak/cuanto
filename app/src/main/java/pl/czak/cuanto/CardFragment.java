package pl.czak.cuanto;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import pl.czak.num2words.Translator;

public class CardFragment extends Fragment
{
    public static final String KEY_POSITION = "position";
    public static final String KEY_IS_ANSWERED = "isAnswered";

    boolean isAnswered = false;

    MainActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            isAnswered = savedInstanceState.getBoolean(KEY_IS_ANSWERED, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        TextView card = (TextView) view.findViewById(R.id.card);
        card.setText(String.valueOf(getNumber()));

        TextView answer = (TextView) view.findViewById(R.id.answer);
        answer.setText(getAnswer());
        answer.setVisibility(isAnswered ? View.VISIBLE : View.INVISIBLE);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_ANSWERED, isAnswered);
    }

    private int getNumber() {
        int seed = activity.getSeed();
        int position = getArguments().getInt(KEY_POSITION);
        return new Random(seed + position).nextInt(1001);
    }

    private String getAnswer() {
        return new Translator().translate(getNumber());
    }

    public void showAnswer() {
        TextView answer = (TextView) getView().findViewById(R.id.answer);
        answer.setVisibility(View.VISIBLE);
        isAnswered = true;
    }
}
