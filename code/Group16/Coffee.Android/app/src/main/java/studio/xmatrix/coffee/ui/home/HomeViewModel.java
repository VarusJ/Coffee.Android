package studio.xmatrix.coffee.ui.home;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.graphics.Bitmap;
import studio.xmatrix.coffee.App;
import studio.xmatrix.coffee.data.common.network.Resource;
import studio.xmatrix.coffee.data.repository.ContentRepository;
import studio.xmatrix.coffee.data.repository.LikeRepository;
import studio.xmatrix.coffee.data.repository.UserRepository;
import studio.xmatrix.coffee.data.service.LikeService;
import studio.xmatrix.coffee.data.service.resource.CommonResource;
import studio.xmatrix.coffee.data.service.resource.LikeResource;
import studio.xmatrix.coffee.data.service.resource.UserResource;

import javax.inject.Inject;

public class HomeViewModel extends AndroidViewModel {

    private ContentRepository contentRepository;
    private LikeRepository likeRepository;
    private UserRepository userRepository;

    @Inject
    HomeViewModel(App app, ContentRepository contentRepository, LikeRepository likeRepository, UserRepository userRepository) {
        super(app);
        this.contentRepository = contentRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    public LiveData<Resource<LikeResource>> getLikes() {
        return likeRepository.getLikes();
    }

    public LiveData<Resource<CommonResource>> like(String id, LikeService.LikeType type) {
        return likeRepository.likeById(id, type);
    }

    public LiveData<Resource<CommonResource>> unlike(String id,LikeService.LikeType type) {
        return likeRepository.unlikeById(id, type);
    }

    public LiveData<Resource<UserResource>> getUserInfo(String id) {
        return userRepository.getInfoById(id);
    }

    public LiveData<Resource<Bitmap>> getUserAvatar(String url) {
        return userRepository.getAvatarByUrl(url);
    }


}

