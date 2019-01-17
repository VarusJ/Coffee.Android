package studio.xmatrix.coffee.ui.add;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import studio.xmatrix.coffee.databinding.PreviewActivityBinding;

public class PreviewHandler {
    private AddActivity.PreviewActivity activity;
    private PreviewActivityBinding binding;

    public PreviewHandler(AddActivity.PreviewActivity activity, PreviewActivityBinding binding, ArrayList<Bitmap> pics, int pos) {
        this.activity = activity;
        this.binding = binding;

        initView(pics, pos);
    }

    private void initView(ArrayList<Bitmap> pics, int pos) {
        binding.viewPager.setAdapter(new FragmentPagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return new PreviewFragment(pics.get(i));
            }

            @Override
            public int getCount() {
                return pics.size();
            }
        });
        binding.viewPager.setCurrentItem(pos);
    }
}
