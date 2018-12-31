package studio.xmatrix.coffee.ui.admin;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.AdminActivityBinding;

public class AdminActivityHandler {

    private AdminActivity activity;
    private AdminActivityBinding binding;
    private boolean passwordVisibility;

    AdminActivityHandler(AdminActivity activity, AdminActivityBinding binding){
        this.activity = activity;
        this.binding = binding;

        initView();
    }

    private void initView() {
        binding.loginButton.setOnClickListener(v -> loginEvent());

        binding.loginForget.setClickable(true);
        binding.loginForget.setOnClickListener(v -> forgetEvent());

        binding.loginSignup.setClickable(true);
        binding.loginSignup.setOnClickListener(v -> gotoSignUp());
        passwordVisibility = false;
        binding.loginPasswordVisibility.setOnClickListener(v -> handlerPasswordVisibility());
    }

    // 登录
    private void loginEvent() {

    }

    // 忘记密码
    private void forgetEvent() {
        // TODO
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
