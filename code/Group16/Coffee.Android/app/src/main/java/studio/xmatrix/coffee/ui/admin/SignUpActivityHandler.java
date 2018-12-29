package studio.xmatrix.coffee.ui.admin;

import android.content.Intent;
import studio.xmatrix.coffee.databinding.SignupActivityBinding;

public class SignUpActivityHandler {
    private SignUpActivity activity;
    private SignupActivityBinding binding;

    SignUpActivityHandler(SignUpActivity activity, SignupActivityBinding binding){
        this.activity = activity;
        this.binding = binding;

        initView();
    }

    private void initView() {
        this.binding.signupButton.setOnClickListener(v -> signupEvent());
    }

    private void signupEvent() {
        // TODO
        Intent intent = new Intent(activity.getBaseContext(), ValidActivity.class);
        activity.startActivity(intent);
    }
}
