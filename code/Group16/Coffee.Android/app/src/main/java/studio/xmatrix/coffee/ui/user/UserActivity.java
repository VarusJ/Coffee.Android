package studio.xmatrix.coffee.ui.user;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.UserActivityBinding;
import studio.xmatrix.coffee.ui.BaseActionBarActivity;

public class UserActivity extends BaseActionBarActivity {
    UserActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_activity);
        UserHandler handler = new UserHandler(this, binding);
    }
}
