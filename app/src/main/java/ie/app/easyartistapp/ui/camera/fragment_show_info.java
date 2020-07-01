package ie.app.easyartistapp.ui.camera;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import ie.app.easyartistapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_show_info extends DialogFragment {

    public fragment_show_info() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_info, container, false);
    }
}
