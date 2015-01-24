package pl.czak.cuanto;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.czak.cuanto.models.Quiz;

public class CardFragment extends Fragment
{
    private static final String KEY_POSITION = "position";
    private static final String KEY_IS_ANSWERED = "isAnswered";

    TextView questionView;
    TextView answerView;

    Quiz.Card card;

    MainActivity activity;

    public static CardFragment create(int position) {
        CardFragment f = new CardFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int position = getArguments() != null ? getArguments().getInt(KEY_POSITION) : 0;
        card = activity.getQuiz().getCard(position);

        if (savedInstanceState != null) {
            card.setAnswered(savedInstanceState.getBoolean(KEY_IS_ANSWERED, false));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        questionView = (TextView) view.findViewById(R.id.question);
        questionView.setText(String.valueOf(card.getQuestion()));

        answerView = (TextView) view.findViewById(R.id.answer);
        answerView.setText(card.getAnswer());
        answerView.setVisibility(card.isAnswered() ? View.VISIBLE : View.INVISIBLE);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_ANSWERED, card.isAnswered());
    }

    public void showAnswer() {
        answerView.setVisibility(View.VISIBLE);
        card.setAnswered(true);
    }
}
