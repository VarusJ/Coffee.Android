package studio.xmatrix.coffee.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.DetailActivityBinding;
import studio.xmatrix.coffee.ui.BaseActionBarActivity;

public class DetailActivity extends BaseActionBarActivity {
    public static int REQUEST_CODE = 886;
    DetailActivityBinding binding;
    DetailHandler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.detail_activity);
        handler = new DetailHandler(this, binding);
    }

    public static void start(Activity activity, String id, Bundle bundle) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra("id", id);
        if (bundle != null) {
            activity.startActivityForResult(intent, REQUEST_CODE, bundle);
        } else {
            activity.startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.setImageLoader();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getBooleanExtra("update", false)) {
                handler.refreshData();
                Intent intent = new Intent();
                intent.putExtra("update", true);
                this.setResult(RESULT_OK, intent);
            }
        }
    }
}
