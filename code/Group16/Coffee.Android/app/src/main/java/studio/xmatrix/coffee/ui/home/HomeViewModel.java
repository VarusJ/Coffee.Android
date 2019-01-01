package studio.xmatrix.coffee.ui.home;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import studio.xmatrix.coffee.App;
import studio.xmatrix.coffee.data.common.network.Resource;
import studio.xmatrix.coffee.data.repository.ContentRepository;
import studio.xmatrix.coffee.data.service.resource.ContentResource;

import javax.inject.Inject;

public class HomeViewModel extends AndroidViewModel {

    private ContentRepository contentRepository;

    @Inject
    HomeViewModel(App app, ContentRepository contentRepository) {
        super(app);
        this.contentRepository = contentRepository;
    }

    LiveData<Resource<ContentResource>> getDetail(String id) {
        return contentRepository.getContentById(id);
    }
}

