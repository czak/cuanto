package pl.czak.cuanto;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ControlsFragment extends Fragment
{
    public static final int LAYOUT_INTRO = R.layout.fragment_controls_intro;
    public static final int LAYOUT_QUIZ = R.layout.fragment_controls_quiz;

    private static final String KEY_LAYOUT = "layout";

    public static ControlsFragment newInstance(int layout) {
        ControlsFragment f = new ControlsFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_LAYOUT, layout);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getArguments().getInt(KEY_LAYOUT), container, false);
    }
}
