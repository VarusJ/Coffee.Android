package studio.xmatrix.coffee.ui.admin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.data.repository.ContentRepository;
import studio.xmatrix.coffee.data.repository.UserRepository;
import studio.xmatrix.coffee.data.service.ContentService;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;
import timber.log.Timber;

import javax.inject.Inject;
import java.io.IOException;

public class AdminActivity extends AppCompatActivity implements Injectable {

    @Inject
    ContentService service;

    @Inject
    ContentRepository repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.admin_activity);

        AppInjector.Companion.inject(this);
        repository.getPublic().observe(this, res -> {});
    }
}
