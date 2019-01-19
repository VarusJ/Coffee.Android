package studio.xmatrix.coffee.ui.add;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.PreviewOneFragmentBinding;

public class PreviewOneFragment extends Fragment {
    PreviewAllFragment parent;
    PreviewOneFragmentBinding binding;
    public PreviewOneFragmentHandler handler;

    Bitmap pic;
    int pos;
    int total;


    public PreviewOneFragment() {

    }

    public PreviewOneFragment(PreviewAllFragment parent, Bitmap pic, int pos, int total) {
        this.parent = parent;
        this.pic = pic;
        this.pos = pos;
        this.total = total;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.preview_one_fragment, null, false);
        handler = new PreviewOneFragmentHandler(parent, this, binding, pic, pos, total);
        binding.setHandler(handler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
}
