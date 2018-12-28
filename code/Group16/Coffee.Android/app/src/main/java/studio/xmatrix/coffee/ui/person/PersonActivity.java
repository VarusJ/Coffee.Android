package studio.xmatrix.coffee.ui.person;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.PersonActivityBinding;
import studio.xmatrix.coffee.ui.BaseActionBarActivity;

import java.util.Objects;

public class PersonActivity extends BaseActionBarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PersonActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.person_activity);
        Objects.requireNonNull(getSupportActionBar()).setTitle("个人设置");
    }
}
