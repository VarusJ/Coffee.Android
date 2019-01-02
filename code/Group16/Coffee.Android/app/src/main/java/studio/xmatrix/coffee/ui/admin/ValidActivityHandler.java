package studio.xmatrix.coffee.ui.admin;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.data.common.network.Status;
import studio.xmatrix.coffee.databinding.ValidActivityBinding;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;

import javax.inject.Inject;
import java.util.Timer;
import java.util.TimerTask;

public class ValidActivityHandler implements Injectable {
    private ValidActivity activity;
    private ValidActivityBinding binding;
    private String email;
    private static final int WAIT_TIME = 10;
    private int time = WAIT_TIME;

    private static class errMsg{
        static String errNetwork = "请检查网络连接";
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private AdminViewModel viewModel;

    ValidActivityHandler(ValidActivity activity, ValidActivityBinding binding, String email){
        this.activity = activity;
        this.binding = binding;
        this.email = email;

        AppInjector.Companion.inject(this);
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(AdminViewModel.class);

        initView();
    }

    private void initView() {
        binding.validEmail.setText(email);

        binding.validButton.setOnClickListener(v -> verification());
        binding.validButtonCode.setOnClickListener(v->getCode());

        binding.validButton.getBackground().setAlpha(50);
        binding.validButtonCardView.getBackground().setAlpha(50);
        binding.validCardView.getBackground().setAlpha(50);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void getCode() {
        binding.validButtonCode.setBackground(activity.getResources().getDrawable(R.drawable.round_button_unclickable));
        binding.validButtonCode.setTextColor(R.color.colorHint);
        binding.validButtonCode.setClickable(false);
        if (timer == null && timertask == null){
            timertask = new TimerTask() {
                @Override
                public void run() {
                    time--;
                    Message message = new Message();
                    message.what = 0;
                    handler.sendMessage(message);
                }
            };
            timer = new Timer();
            timer.schedule(timertask, 1000, 1000);
        }
        viewModel.getEmailValidCode().observe(activity, res->{
            assert res != null;
            if (res.getStatus() == Status.SUCCESS){
                //
            } else if (res.getStatus() == Status.ERROR){
                Toast.makeText(activity, errMsg.errNetwork, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verification() {

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @SuppressLint({"DefaultLocale", "ResourceAsColor"})
        public void handleMessage(Message message){
            switch (message.what){
                case 0:
                    if (time > 0){
                        binding.validButtonCode.setText(String.format("重新获取(%ds)", time));
                    } else {
                        timer.cancel();
                        timertask.cancel();
                        timer = null;
                        timertask = null;
                        time = WAIT_TIME;
                        binding.validButtonCode.setText("获取验证码");
                        binding.validButtonCode.setBackground(activity.getResources().getDrawable(R.drawable.round_button));
                        binding.validButtonCode.setTextColor(R.color.colorGreen);
                        binding.validButtonCode.setClickable(true);
                    }
                    break;
            }
        }
    };

    private Timer timer = null;
    private TimerTask timertask = null;
}
