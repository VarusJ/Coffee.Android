package studio.xmatrix.coffee.ui.admin;

import android.content.Intent;
import studio.xmatrix.coffee.databinding.AdminActivityBinding;

public class AdminActivityHandler {

    private AdminActivity activity;
    private AdminActivityBinding binding;

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

}
