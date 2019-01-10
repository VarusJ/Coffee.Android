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
import studio.xmatrix.coffee.data.service.resource.ContentsResource;
import studio.xmatrix.coffee.data.service.resource.LikeResource;
import studio.xmatrix.coffee.data.service.resource.UserResource;
import studio.xmatrix.coffee.ui.nav.ImageViewModel;

import javax.inject.Inject;

public class HomeViewModel extends AndroidViewModel implements ImageViewModel {

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

    LiveData<Resource<LikeResource>> getLikes() {
        return likeRepository.getLikes();
    }

    LiveData<Resource<CommonResource>> like(String id) {
        return likeRepository.likeById(id, LikeService.LikeType.Content);
    }

    LiveData<Resource<CommonResource>> unlike(String id) {
        return likeRepository.unlikeById(id, LikeService.LikeType.Content);
    }

    LiveData<Resource<ContentsResource>> getSelfText() {
        return contentRepository.getTexts();
    }

    LiveData<Resource<ContentsResource>> getSelfAlbum() {
        return contentRepository.getAlbums();
    }

    LiveData<Resource<ContentsResource>> getText(String id) {
        return contentRepository.getTextsByUserId(id);
    }

    LiveData<Resource<ContentsResource>> getAlbum(String id) {
        return contentRepository.getAlbumsByUserId(id);
    }

    public LiveData<Resource<UserResource>> getUserInfo(String id) {
        return userRepository.getInfoById(id);
    }

    public LiveData<Resource<Bitmap>> getUserAvatar(String url) {
        return userRepository.getAvatarByUrl(url);
    }

    public LiveData<Resource<Bitmap>> getThumb(String file) {
        return contentRepository.getThumbByFilename(file);
    }

    public LiveData<Resource<Bitmap>> getImage(String id, String path) { return contentRepository.getImageByIdAndPath(id, path); }

}

