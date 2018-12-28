package studio.xmatrix.coffee.ui.add;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.AddActivityBinding;
import studio.xmatrix.coffee.ui.BaseActionBarActivity;

import java.util.Objects;

public class AddActivity extends BaseActionBarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.add_activity);
        Objects.requireNonNull(getSupportActionBar()).setTitle("发布");
    }
}
