package studio.xmatrix.coffee.ui.admin;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.widget.Toast;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.data.common.network.Status;
import studio.xmatrix.coffee.databinding.AdminActivityBinding;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;

import javax.inject.Inject;

public class AdminActivityHandler implements Injectable {

    private AdminActivity activity;
    private AdminActivityBinding binding;
    private boolean passwordVisibility;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    AdminViewModel viewModel;

    AdminActivityHandler(AdminActivity activity, AdminActivityBinding binding){
        this.activity = activity;
        this.binding = binding;

        AppInjector.Companion.inject(activity);
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(AdminViewModel.class);

        initView();
    }

    private void initView() {

        binding.loginButton.setOnClickListener(v -> loginEvent());
        binding.loginButton.getBackground().setAlpha(150);

        binding.loginForget.setClickable(true);
        SpannableString ss = new SpannableString(binding.loginForget.getText().toString());
        ss.setSpan(new URLSpan("https://oauth.xmatrix.studio/reset"),0,binding.loginForget.getText().length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.WHITE), 0,binding.loginForget.getText().length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.loginForget.setText(ss);

        binding.loginForget.setMovementMethod(LinkMovementMethod.getInstance());

        binding.loginSignup.setClickable(true);
        binding.loginSignup.setOnClickListener(v -> gotoSignUp());
        passwordVisibility = false;
        binding.loginPasswordVisibility.setOnClickListener(v -> handlerPasswordVisibility());

        binding.loginCardView.setCardBackgroundColor(Color.TRANSPARENT);

    }

    // 登录
    private void loginEvent() {
        String username = binding.loginUsername.getText().toString();
        String password = binding.loginPassword.getText().toString();
        viewModel.login(username, password).observe(activity, res -> {
            assert res != null;
            if (res.getStatus() == Status.SUCCESS){
                activity.finish();
            } else {
                Toast.makeText(activity, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 跳转注册
    private void gotoSignUp(){
        Intent intent = new Intent(activity.getBaseContext(), SignUpActivity.class);
        activity.startActivity(intent);
    }

    private void handlerPasswordVisibility() {
        passwordVisibility = !passwordVisibility;
        if (passwordVisibility){
            binding.loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.loginPasswordVisibility.setImageResource(R.drawable.ic_password_visible);
        } else {
            binding.loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.loginPasswordVisibility.setImageResource(R.drawable.ic_password_invisible);
        }
        binding.loginPassword.setSelection(binding.loginPassword.getText().length());
    }

}
