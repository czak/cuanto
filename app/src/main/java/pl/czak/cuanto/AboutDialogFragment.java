package pl.czak.cuanto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

/**
 * Created by czak on 26.01.2015.
 */
public class AboutDialogFragment extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SpannableString msg = new SpannableString(getString(R.string.about_message));
        Linkify.addLinks(msg, Linkify.WEB_URLS);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.app_name)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();

        // To pozwala na kliknięcie w link w dialogu
        TextView view = (TextView) getDialog().findViewById(android.R.id.message);
        view.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
