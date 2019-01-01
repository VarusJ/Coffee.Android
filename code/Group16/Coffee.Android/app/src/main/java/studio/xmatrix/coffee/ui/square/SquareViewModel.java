package studio.xmatrix.coffee.ui.square;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import studio.xmatrix.coffee.App;
import studio.xmatrix.coffee.data.common.network.Resource;
import studio.xmatrix.coffee.data.repository.ContentRepository;
import studio.xmatrix.coffee.data.repository.UserRepository;
import studio.xmatrix.coffee.data.service.resource.CommonResource;
import studio.xmatrix.coffee.data.service.resource.ContentsResource;

import javax.inject.Inject;

public class SquareViewModel extends AndroidViewModel {

    private ContentRepository contentRepository;

    @Inject
    SquareViewModel(App app, ContentRepository contentRepository) {
        super(app);
        this.contentRepository = contentRepository;
    }

    LiveData<Resource<ContentsResource>> getPublic(int page, int eachPage) {
        return contentRepository.getPublicContentsByPage(page, eachPage);
    }

}
