package studio.xmatrix.coffee.inject.component

import android.os.UserHandle
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import studio.xmatrix.coffee.App
import studio.xmatrix.coffee.inject.module.ServiceModule
import studio.xmatrix.coffee.inject.module.ViewModelModule
import studio.xmatrix.coffee.ui.admin.AdminActivity
import studio.xmatrix.coffee.ui.detail.DetailHandler
import studio.xmatrix.coffee.ui.square.SquareHandler
import studio.xmatrix.coffee.ui.admin.AdminActivityHandler
import studio.xmatrix.coffee.ui.home.HomeHandler
import studio.xmatrix.coffee.ui.notice.NoticeHandler
import studio.xmatrix.coffee.ui.person.PersonHandler
import studio.xmatrix.coffee.ui.user.UserHandler
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ServiceModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: App): Builder

        fun build(): AppComponent
    }

    fun inject(injectable: AdminActivity)

    fun inject(injectable: SquareHandler)

    fun inject(injectable: DetailHandler)

    fun inject(injectable: HomeHandler)

    fun inject(injectable: UserHandler)

    fun inject(injectable: NoticeHandler)

    fun inject(injectable: PersonHandler)

    fun inject(injectable: AdminActivityHandler)
}
