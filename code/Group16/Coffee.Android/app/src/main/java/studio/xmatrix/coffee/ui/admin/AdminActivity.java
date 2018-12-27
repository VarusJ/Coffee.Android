package studio.xmatrix.coffee.ui.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.data.repository.UserRepository;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;
import timber.log.Timber;

import javax.inject.Inject;

public class AdminActivity extends AppCompatActivity implements Injectable {

    @Inject
    UserRepository userRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.admin_activity);

        AppInjector.Companion.inject(this);
        userRepository.login("MegaShow", "123456c").observe(this, res -> {
            Timber.d("CoffeeAAA: %s, %s", res.getMessage(), res.getStatus().toString());
            switch (res.getStatus()) {
                case SUCCESS:
                    if (res.getData() != null) {
                        Toast.makeText(this, "State: " + res.getData().getState(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Data: " + res.getData().getData(), Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
