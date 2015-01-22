package pl.czak.cuanto;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment
{
    private static final int[] colors = {
      0xff00ffff, 0xffffff00, 0xffff00ff,
      0xff0fff0f, 0xff1cfbaf, 0xfff0ac1b
    };

    public CardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);

/*
        View card = view.findViewById(R.id.card);
        card.setBackgroundColor(colors[getArguments().getInt("pos")]);
*/

        return view;
    }


}
