package studio.xmatrix.coffee.ui.add;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.PreviewFragmentBinding;

public class PreviewFragment extends Fragment {
    PreviewFragmentBinding binding;
    Bitmap pic;
    public PreviewFragmentHandler handler;

    public PreviewFragment() {

    }

    public PreviewFragment(Bitmap pic) {
        this.pic = pic;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.preview_fragment, null, false);
        handler = new PreviewFragmentHandler(getActivity(), binding, pic);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
}
