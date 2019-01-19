package studio.xmatrix.coffee.ui.add;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.PreviewAllFragmentBinding;

public class PreviewAllFragment extends Fragment {
    AddActivity activity;
    PreviewAllFragmentBinding binding;
    public PreviewAllFragmentHandler handler;

    ArrayList<Bitmap> pics;
    int pos;

    public PreviewAllFragment() {

    }

    public PreviewAllFragment(AddActivity activity, ArrayList<Bitmap> pics, int pos) {
        this.activity = activity;
        this.pics = pics;
        this.pos = pos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.preview_all_fragment, null, false);
        handler = new PreviewAllFragmentHandler(activity, this, binding, pics, pos);
        binding.setHandler(handler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
}
