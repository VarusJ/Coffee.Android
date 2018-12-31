package studio.xmatrix.coffee.ui.add;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.AddActivityBinding;
import studio.xmatrix.coffee.ui.BaseActionBarActivity;
import studio.xmatrix.coffee.ui.detail.DetailHandler;

import java.util.Objects;

public class AddActivity extends BaseActionBarActivity {
    AddActivityBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.add_activity);
        AddHandler handler = new AddHandler(this, binding);
        binding.setHandler(handler);
        Objects.requireNonNull(getSupportActionBar()).setTitle("发布");
    }
}
