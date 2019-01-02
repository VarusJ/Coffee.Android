package studio.xmatrix.coffee.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.DetailActivityBinding;
import studio.xmatrix.coffee.ui.BaseActionBarActivity;

import java.util.Objects;

public class DetailActivity extends BaseActionBarActivity {
    public static int REQUEST_CODE = 886;
    DetailActivityBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.detail_activity);
        DetailHandler handler = new DetailHandler(this, binding);
    }

    public static void start(Activity activity, String id, Bundle bundle) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra("id", id);
        activity.startActivityForResult(intent, REQUEST_CODE, bundle);
    }

}
