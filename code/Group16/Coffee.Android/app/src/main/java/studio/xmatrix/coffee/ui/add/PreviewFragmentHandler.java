package studio.xmatrix.coffee.ui.add;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;

import studio.xmatrix.coffee.databinding.PreviewFragmentBinding;

public class PreviewFragmentHandler {
    FragmentActivity activity;
    PreviewFragmentBinding binding;
    Bitmap bitmap;

    public PreviewFragmentHandler(FragmentActivity activity, PreviewFragmentBinding binding, Bitmap bitmap) {
        this.activity = activity;
        this.binding = binding;
        this.bitmap = bitmap;
        initView();
    }

    private void initView() {
        binding.photoView.setImageDrawable(new BitmapDrawable(null, bitmap));
    }
}
