package studio.xmatrix.coffee.ui.square;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import studio.xmatrix.coffee.databinding.SquareFragmentBinding;

class SquareHandler {
    private FragmentActivity activity;
    private SquareFragmentBinding binding;


    SquareHandler(FragmentActivity activity, SquareFragmentBinding binding) {
        this.activity = activity;
        this.binding = binding;
        initView();
    }

    private void initView () {
        binding.squareList.setLayoutManager(new LinearLayoutManager(activity));
        binding.squareList.setAdapter(new SquareAdapter(activity));
    }

}
