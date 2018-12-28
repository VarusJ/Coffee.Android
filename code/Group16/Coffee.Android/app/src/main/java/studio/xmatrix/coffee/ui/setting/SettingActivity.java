package studio.xmatrix.coffee.ui.setting;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.SettingActivityBinding;
import studio.xmatrix.coffee.ui.BaseActionBarActivity;

import java.util.Objects;

public class SettingActivity extends BaseActionBarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.setting_activity);
        Objects.requireNonNull(getSupportActionBar()).setTitle("应用设置");
    }
}
