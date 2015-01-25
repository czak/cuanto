package pl.czak.cuanto;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ControlsFragment extends Fragment
{
    public static final String TAG_INTRO = "INTRO";
    public static final String TAG_QUIZ = "QUIZ";

    private static final String KEY_TAG = "tag";

    public static ControlsFragment newInstance(String tag) {
        ControlsFragment f = new ControlsFragment();

        Bundle args = new Bundle();
        args.putString(KEY_TAG, tag);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    private int getLayoutId() {
        String tag = getArguments().getString(KEY_TAG);
        if (tag.equals(TAG_INTRO))
            return R.layout.fragment_controls_intro;
        else // if (tag == TAG_QUIZ)
            return R.layout.fragment_controls_quiz;
    }
}
