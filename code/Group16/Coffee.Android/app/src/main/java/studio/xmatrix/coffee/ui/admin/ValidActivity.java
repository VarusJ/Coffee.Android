package studio.xmatrix.coffee.ui.admin;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.ValidActivityBinding;

import java.util.Objects;

public class ValidActivity extends AppCompatActivity {
    ValidActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        String email = bundle.getString("email");
        this.binding = DataBindingUtil.setContentView(this, R.layout.valid_activity);
        binding.setHandler(new ValidActivityHandler(this, binding, email));
        Objects.requireNonNull(getSupportActionBar()).setTitle("邮箱验证");
    }

}
