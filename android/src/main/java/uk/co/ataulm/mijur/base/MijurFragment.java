package uk.co.ataulm.mijur.base;

import android.app.Fragment;
import android.widget.Toast;

public abstract class MijurFragment extends Fragment {

    protected void toast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void toast(int messageResId) {
        Toast.makeText(getActivity(), messageResId, Toast.LENGTH_SHORT).show();
    }

}
