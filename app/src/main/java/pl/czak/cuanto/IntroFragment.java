package pl.czak.cuanto;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class IntroFragment extends Fragment
{
    private static final String KEY_PAGE_NUMBER = "pageNumber";

    String content;

    public static IntroFragment create(int n) {
        IntroFragment f = new IntroFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_PAGE_NUMBER, n);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int n = getArguments().getInt(KEY_PAGE_NUMBER, 0);
        content = getResources().getStringArray(R.array.intro_pages)[n];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        TextView textView = (TextView) view.findViewById(R.id.text_intro);
        textView.setText(content);

        return view;
    }
}
