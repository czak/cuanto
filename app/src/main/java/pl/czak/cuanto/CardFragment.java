package pl.czak.cuanto;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CardFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        TextView card = (TextView) view.findViewById(R.id.card);
        card.setText(String.valueOf(getArguments().getInt("pos")));

        return view;
    }
}
