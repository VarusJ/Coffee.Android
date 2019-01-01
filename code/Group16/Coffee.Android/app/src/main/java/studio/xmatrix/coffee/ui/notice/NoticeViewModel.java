package studio.xmatrix.coffee.ui.notice;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import studio.xmatrix.coffee.App;
import studio.xmatrix.coffee.data.common.network.Resource;
import studio.xmatrix.coffee.data.repository.ContentRepository;
import studio.xmatrix.coffee.data.repository.NotificationRepository;
import studio.xmatrix.coffee.data.service.resource.CommonResource;
import studio.xmatrix.coffee.data.service.resource.ContentResource;
import studio.xmatrix.coffee.data.service.resource.NotificationsResource;

import javax.inject.Inject;

public class NoticeViewModel extends AndroidViewModel {

    private NotificationRepository notificationRepository;

    @Inject
    NoticeViewModel(App app, NotificationRepository notificationRepository) {
        super(app);
        this.notificationRepository = notificationRepository;
    }

    LiveData<Resource<NotificationsResource>> getNotice() {
        return notificationRepository.getNotifications();
    }

    LiveData<Resource<CommonResource>> readNotice(String id) {
        return notificationRepository.readNotificationById(id);
    }

    LiveData<Resource<CommonResource>> deleteNotice(String id) {
        return notificationRepository.deleteNotificationById(id);
    }
}
