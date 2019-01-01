package studio.xmatrix.coffee.inject.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import studio.xmatrix.coffee.App
import studio.xmatrix.coffee.inject.module.ServiceModule
import studio.xmatrix.coffee.ui.admin.AdminActivity
import studio.xmatrix.coffee.ui.admin.AdminActivityHandler
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ServiceModule::class
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
    fun inject(injectable: AdminActivityHandler)
}
