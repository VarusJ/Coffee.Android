package studio.xmatrix.coffee.ui.admin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.SignupActivityBinding;

import javax.annotation.Nullable;

public class SignUpActivity extends AppCompatActivity {

    SignupActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.signup_activity);
        binding.setHandler(new SignUpActivityHandler(this, binding));
    }
}
