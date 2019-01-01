package studio.xmatrix.coffee.ui.square;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import studio.xmatrix.coffee.App;
import studio.xmatrix.coffee.data.common.network.Resource;
import studio.xmatrix.coffee.data.repository.ContentRepository;
import studio.xmatrix.coffee.data.repository.LikeRepository;
import studio.xmatrix.coffee.data.service.LikeService;
import studio.xmatrix.coffee.data.service.resource.CommonResource;
import studio.xmatrix.coffee.data.service.resource.ContentsResource;
import studio.xmatrix.coffee.data.service.resource.LikeResource;

import javax.inject.Inject;

public class SquareViewModel extends AndroidViewModel {

    private ContentRepository contentRepository;
    private LikeRepository likeRepository;

    @Inject
    SquareViewModel(App app, ContentRepository contentRepository, LikeRepository likeRepository) {
        super(app);
        this.contentRepository = contentRepository;
        this.likeRepository = likeRepository;
    }

    LiveData<Resource<ContentsResource>> getPublic(int page, int eachPage) {
        return contentRepository.getPublicContentsByPage(page, eachPage);
    }


    LiveData<Resource<LikeResource>> getLikes() {
        return likeRepository.getLikes();
    }

    LiveData<Resource<CommonResource>> like(String id, LikeService.LikeType type) {
        return likeRepository.likeById(id, type);
    }

    LiveData<Resource<CommonResource>> unlike(String id,LikeService.LikeType type) {
        return likeRepository.unlikeById(id, type);
    }

}
