package studio.xmatrix.coffee.ui.person;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import studio.xmatrix.coffee.App;
import studio.xmatrix.coffee.data.common.network.Resource;
import studio.xmatrix.coffee.data.repository.UserRepository;
import studio.xmatrix.coffee.data.service.resource.UserResource;

import javax.inject.Inject;

public class PersonViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    @Inject
    PersonViewModel(App app, UserRepository userRepository) {
        super(app);
        this.userRepository = userRepository;
    }

    LiveData<Resource<UserResource>> getInfo() {
        return userRepository.getInfo();
    }
}

