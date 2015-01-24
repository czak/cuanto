package pl.czak.cuanto;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class CardFragment extends Fragment
{
    public static final String KEY_POSITION = "position";

    MainActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        TextView card = (TextView) view.findViewById(R.id.card);
        card.setText(String.valueOf(getNumber()));

        return view;
    }

    private int getNumber() {
        int seed = activity.getSeed();
        int position = getArguments().getInt(KEY_POSITION);
        return new Random(seed + position).nextInt(1001);
    }
}
