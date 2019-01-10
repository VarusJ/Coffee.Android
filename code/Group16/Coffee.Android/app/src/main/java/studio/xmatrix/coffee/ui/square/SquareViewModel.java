package studio.xmatrix.coffee.ui.square;

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
import studio.xmatrix.coffee.data.service.resource.ContentsResource;
import studio.xmatrix.coffee.data.service.resource.LikeResource;

import javax.inject.Inject;

public class SquareViewModel extends AndroidViewModel {

    private ContentRepository contentRepository;
    private LikeRepository likeRepository;
    private UserRepository userRepository;

    @Inject
    SquareViewModel(App app, ContentRepository contentRepository, LikeRepository likeRepository, UserRepository userRepository) {
        super(app);
        this.contentRepository = contentRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    LiveData<Resource<ContentsResource>> getPublic(int page, int eachPage) {
        return contentRepository.getPublicContentsByPage(page, eachPage);
    }


    LiveData<Resource<LikeResource>> getLikes() {
        return likeRepository.getLikes();
    }

    LiveData<Resource<CommonResource>> like(String id) {
        return likeRepository.likeById(id, LikeService.LikeType.Content);
    }

    LiveData<Resource<CommonResource>> unlike(String id) {
        return likeRepository.unlikeById(id, LikeService.LikeType.Content);
    }

    LiveData<Resource<Bitmap>> getUserAvatar(String url) {
        return userRepository.getAvatarByUrl(url);
    }
}
