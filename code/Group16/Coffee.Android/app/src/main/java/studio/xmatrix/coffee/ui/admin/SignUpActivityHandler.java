package studio.xmatrix.coffee.ui.admin;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.SignupActivityBinding;

public class SignUpActivityHandler {
    private SignUpActivity activity;
    private SignupActivityBinding binding;
    private boolean passwordVisibility;
    private boolean confirmVisibility;

    SignUpActivityHandler(SignUpActivity activity, SignupActivityBinding binding){
        this.activity = activity;
        this.binding = binding;

        initView();
    }

    private void initView() {
        this.binding.signupButton.setOnClickListener(v -> signupEvent());
        passwordVisibility = false;
        confirmVisibility = false;
        binding.signupPasswordVisibility.setOnClickListener(v -> handlerPasswordVisibility());
        binding.signupConfirmVisibility.setOnClickListener(v -> handlerConfirmVisibility());
    }

    private void signupEvent() {
        // TODO
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
