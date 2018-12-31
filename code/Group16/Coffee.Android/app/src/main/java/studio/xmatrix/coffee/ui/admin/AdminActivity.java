package studio.xmatrix.coffee.ui.admin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.data.repository.ContentRepository;
import studio.xmatrix.coffee.data.service.ContentService;
import studio.xmatrix.coffee.databinding.AdminActivityBinding;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;

import javax.inject.Inject;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity implements Injectable {

    @Inject
    ContentService service;
    @Inject
    ContentRepository repository;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdminActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.admin_activity);
        binding.setHandler(new AdminActivityHandler(this, binding));

        AppInjector.Companion.inject(this);
        repository.getPublicByPage(1, 7).observe(this, res -> {
        });
        Objects.requireNonNull(getSupportActionBar()).setTitle("用户登录");
    }
}
