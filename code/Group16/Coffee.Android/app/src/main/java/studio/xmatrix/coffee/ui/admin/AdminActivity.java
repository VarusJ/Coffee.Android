package studio.xmatrix.coffee.ui.admin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.AdminActivityBinding;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity {

    AdminActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.admin_activity);
        binding.setHandler(new AdminActivityHandler(this, binding));
        Objects.requireNonNull(getSupportActionBar()).setTitle("用户登录");
    }
}
