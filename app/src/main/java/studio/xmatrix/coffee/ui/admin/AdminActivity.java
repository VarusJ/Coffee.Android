package studio.xmatrix.coffee.ui.admin;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.AdminActivityBinding;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        AdminActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.admin_activity);
        binding.setHandler(new AdminActivityHandler(this, binding));
    }
}
