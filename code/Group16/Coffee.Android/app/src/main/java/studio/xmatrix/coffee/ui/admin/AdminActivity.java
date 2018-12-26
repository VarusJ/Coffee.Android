package studio.xmatrix.coffee.ui.admin;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.data.service.UserService;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;
import timber.log.Timber;

import javax.inject.Inject;
import java.io.IOException;

public class AdminActivity extends AppCompatActivity implements Injectable {

    @Inject
    UserService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.admin_activity);

        AppInjector.Companion.inject(this);
        service.loginByPassword().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Timber.d("onResponse: %s", new String(response.body().bytes()));
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Timber.d("onFailure: %s", t.toString());
            }
        });
    }
}
