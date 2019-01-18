package studio.xmatrix.coffee.ui.add;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import studio.xmatrix.coffee.databinding.PreviewAllFragmentBinding;

public class PreviewAllFragmentHandler {
    private AddActivity activity;
    private PreviewAllFragment fragment;
    private PreviewAllFragmentBinding binding;

    public PreviewAllFragmentHandler(AddActivity activity, PreviewAllFragment fragment, PreviewAllFragmentBinding binding, ArrayList<Bitmap> pics, int pos) {
        this.activity = activity;
        this.fragment = fragment;
        this.binding = binding;

        initView(pics, pos);
    }

    private void initView(ArrayList<Bitmap> pics, int pos) {
        binding.previewPager.setAdapter(new FragmentStatePagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return new PreviewOneFragment(fragment, pics.get(i), i, pics.size());
            }

            @Override
            public int getCount() {
                return pics.size();
            }
        });
        binding.previewPager.setCurrentItem(pos);
        binding.previewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                updateHeader(i, pics.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        updateHeader(pos, pics.size());
    }

    public void updateHeader(int pos, int total) {
        ++pos;
        String str = pos + "/" + total;
        binding.order.setText(str);
    }
}
