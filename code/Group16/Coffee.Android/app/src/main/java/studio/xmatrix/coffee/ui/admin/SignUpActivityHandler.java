package studio.xmatrix.coffee.ui.admin;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Toast;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.data.common.network.Status;
import studio.xmatrix.coffee.databinding.SignupActivityBinding;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;

import javax.inject.Inject;
import java.util.Objects;

import static studio.xmatrix.coffee.data.service.resource.CommonResource.StatusError;
import static studio.xmatrix.coffee.data.service.resource.CommonResource.StatusSuccess;

public class SignUpActivityHandler implements Injectable {
    private SignUpActivity activity;
    private SignupActivityBinding binding;
    private boolean passwordVisibility;
    private boolean confirmVisibility;

    private static class errMsg{
        static String nullEmail = "请输入邮箱";
        static String nullUsername = "请输入用户名";
        static String nullPassword = "请输入密码";
        static String errConfirm = "两次输入密码不一致";
        static String errNetwork = "请检查网络连接";

    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    AdminViewModel viewModel;

    SignUpActivityHandler(SignUpActivity activity, SignupActivityBinding binding){
        this.activity = activity;
        this.binding = binding;

        AppInjector.Companion.inject(this);
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(AdminViewModel.class);

        initView();
    }

    private void initView() {
        binding.signupButton.setOnClickListener(v -> signupEvent());
        binding.signupButton.getBackground().setAlpha(50);

        passwordVisibility = false;
        confirmVisibility = false;
        binding.signupPasswordVisibility.setOnClickListener(v -> handlerPasswordVisibility());
        binding.signupConfirmVisibility.setOnClickListener(v -> handlerConfirmVisibility());

        binding.signupCardView.getBackground().setAlpha(50);
    }

    private void signupEvent() {
        String email = binding.signupEmail.getText().toString();
        String username = binding.signupUsername.getText().toString();
        String password = binding.signupPassword.getText().toString();
        String confirm = binding.signupConfirm.getText().toString();
        if (email.length() == 0){
            Toast.makeText(activity, errMsg.nullEmail, Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.length() == 0){
            Toast.makeText(activity, errMsg.nullUsername, Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() == 0){
            Toast.makeText(activity, errMsg.nullPassword, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirm)){
            Toast.makeText(activity, errMsg.errConfirm, Toast.LENGTH_SHORT).show();
            return;
        }
        viewModel.register(username, email, password).observe(activity, res -> {
            assert res != null;
            if (res.getStatus() == Status.SUCCESS){
                if (Objects.requireNonNull(res.getData()).getState().equals(StatusSuccess)){
                    activity.finish();
                } else if (res.getData().getState().equals(StatusError)){
                    Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
                }
            } else if (res.getStatus() == Status.ERROR){
                Toast.makeText(activity, errMsg.errNetwork, Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = new Intent(activity.getBaseContext(), ValidActivity.class);
        activity.startActivity(intent);
    }

    private void handlerPasswordVisibility() {
        passwordVisibility = !passwordVisibility;
        if (passwordVisibility){
            binding.signupPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.signupPasswordVisibility.setImageResource(R.drawable.ic_password_visible);
        } else {
            binding.signupPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.signupPasswordVisibility.setImageResource(R.drawable.ic_password_invisible);
        }
        binding.signupPassword.setSelection(binding.signupPassword.getText().length());
    }

    private void handlerConfirmVisibility() {
        confirmVisibility = !confirmVisibility;
        if (confirmVisibility){
            binding.signupConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.signupConfirmVisibility.setImageResource(R.drawable.ic_password_visible);
        } else {
            binding.signupConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.signupConfirmVisibility.setImageResource(R.drawable.ic_password_invisible);
        }
        binding.signupConfirm.setSelection(binding.signupConfirm.getText().length());
    }
}
