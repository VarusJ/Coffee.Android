package studio.xmatrix.coffee.ui.detail;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import studio.xmatrix.coffee.App;
import studio.xmatrix.coffee.data.common.network.Resource;
import studio.xmatrix.coffee.data.repository.CommentRepository;
import studio.xmatrix.coffee.data.repository.ContentRepository;
import studio.xmatrix.coffee.data.service.resource.CommentsResource;
import studio.xmatrix.coffee.data.service.resource.ContentResource;

import javax.inject.Inject;

public class DetailViewModel extends AndroidViewModel {

    private ContentRepository contentRepository;
    private CommentRepository commentRepository;

    @Inject
    DetailViewModel(App app, ContentRepository contentRepository, CommentRepository commentRepository) {
        super(app);
        this.contentRepository = contentRepository;
        this.commentRepository = commentRepository;
    }

    LiveData<Resource<ContentResource>> getDetail(String id) {
        return contentRepository.getContentById(id);
    }

    LiveData<Resource<CommentsResource>> getComments(String id) {
        return commentRepository.getCommentsByContentId(id);
    }



}
