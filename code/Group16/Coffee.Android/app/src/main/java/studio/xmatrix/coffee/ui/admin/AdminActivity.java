package studio.xmatrix.coffee.ui.admin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
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
        userRepository.login("MegaShow", "1234561  ").observe(this, res -> {
            Timber.d("CoffeeAAA: %s, %s", res.getMessage(), res.getStatus().toString());
            switch (res.getStatus()) {
                case SUCCESS:
                    if (res.getData() != null) {
                        Toast.makeText(this, "State: " + res.getData().getState(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Data: " + res.getData().getData(), Toast.LENGTH_SHORT).show();
                    }
                    userRepository.getInfo().observe(this, res1 -> {
                        Timber.d("CoffeeAAA: %s, %s", res1.getMessage(), res1.getStatus().toString());
                        switch (res1.getStatus()) {
                            case SUCCESS:
                                if (res1.getData() != null) {
                                    Toast.makeText(this, "User: " + res1.getData().getName(), Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
            }
        });
    }
}
