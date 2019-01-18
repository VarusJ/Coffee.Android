package studio.xmatrix.coffee.ui.add;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.graphics.Bitmap;
import studio.xmatrix.coffee.App;
import studio.xmatrix.coffee.data.common.network.Resource;
import studio.xmatrix.coffee.data.repository.ContentRepository;
import studio.xmatrix.coffee.data.service.resource.CommonResource;
import studio.xmatrix.coffee.data.service.resource.ContentResource;

import javax.inject.Inject;
import java.util.List;

public class AddViewModel extends AndroidViewModel {

    private ContentRepository contentRepository;

    @Inject
    AddViewModel(App app, ContentRepository contentRepository) {
        super(app);
        this.contentRepository = contentRepository;
    }

    LiveData<Resource<ContentResource>> getDetail(String id) {
        return contentRepository.getContentById(id);
    }

    LiveData<Resource<CommonResource>> addText(String title, String detail, List<String> tags, Boolean isPublic) {
        return contentRepository.addText(title, detail, tags, isPublic);
    }

    LiveData<Resource<CommonResource>> update(String id, String title, String detail, List<String> tags, Boolean isPublic) {
        return contentRepository.updateContentById(id, title, detail, tags, isPublic);
    }

    LiveData<Resource<CommonResource>> addAlbum(String title, String detail, List<String> tags, Boolean isPublic, List<Bitmap> images) {
        return contentRepository.addAlbum(title, detail, tags, isPublic, images);
    }


}
