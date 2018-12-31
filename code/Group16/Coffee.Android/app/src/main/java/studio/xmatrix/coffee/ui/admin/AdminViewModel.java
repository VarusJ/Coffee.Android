package studio.xmatrix.coffee.ui.admin;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import studio.xmatrix.coffee.App;
import studio.xmatrix.coffee.data.common.network.Resource;
import studio.xmatrix.coffee.data.repository.UserRepository;
import studio.xmatrix.coffee.data.service.response.CommonResponse;

import javax.inject.Inject;

public class AdminViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    @Inject
    AdminViewModel(App app, UserRepository userRepository) {
        super(app);
        this.userRepository = userRepository;
    }

    LiveData<Resource<CommonResponse>> login(String name, String password) {
        return userRepository.login(name, password);
    }

    LiveData<Resource<CommonResponse>> register(String name, String email, String password) {
        return userRepository.register(name, email, password);
    }

    LiveData<Resource<CommonResponse>> getEmailValidCode() {
        return userRepository.getEmailValidCode();
    }

    LiveData<Resource<CommonResponse>> validEmail(String vcode) {
        return userRepository.validEmail(vcode);
    }
}
