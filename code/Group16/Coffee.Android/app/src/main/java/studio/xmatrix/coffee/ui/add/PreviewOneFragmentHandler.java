package studio.xmatrix.coffee.ui.add;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import studio.xmatrix.coffee.databinding.PreviewOneFragmentBinding;


public class PreviewOneFragmentHandler {
    PreviewAllFragment parent;
    PreviewOneFragment fragment;
    PreviewOneFragmentBinding binding;
    Bitmap bitmap;
    int pos;
    int total;

    public PreviewOneFragmentHandler(PreviewAllFragment parent, PreviewOneFragment fragment, PreviewOneFragmentBinding binding, Bitmap bitmap, int pos, int total) {
        this.parent = parent;
        this.fragment = fragment;
        this.binding = binding;
        this.bitmap = bitmap;
        this.pos = pos + 1;
        this.total = total;
        initView();
    }

    private void initView() {
        binding.photoView.setImageDrawable(new BitmapDrawable(null, bitmap));
    }
}
