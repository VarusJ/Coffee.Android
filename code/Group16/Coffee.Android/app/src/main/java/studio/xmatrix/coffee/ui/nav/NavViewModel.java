package studio.xmatrix.coffee.ui.nav;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.graphics.Bitmap;
import studio.xmatrix.coffee.App;
import studio.xmatrix.coffee.data.common.network.Resource;
import studio.xmatrix.coffee.data.repository.UserRepository;
import studio.xmatrix.coffee.data.service.resource.CommonResource;
import studio.xmatrix.coffee.data.service.resource.UserResource;

import javax.inject.Inject;

public class NavViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    @Inject
    NavViewModel(App app, UserRepository userRepository) {
        super(app);
        this.userRepository = userRepository;
    }

    LiveData<Resource<UserResource>> getUserInfo() {
        return userRepository.getInfo();
    }

    LiveData<Resource<Bitmap>> getUserAvatar(String url) {
        return userRepository.getAvatarByUrl(url);
    }

    LiveData<Resource<CommonResource>> logout() {
        return userRepository.logout();
    }
}
