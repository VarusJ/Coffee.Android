package studio.xmatrix.coffee.inject.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import studio.xmatrix.coffee.inject.key.ViewModelKey
import studio.xmatrix.coffee.ui.admin.AdminViewModel
import studio.xmatrix.coffee.ui.common.ViewModelFactory

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AdminViewModel::class)
    abstract fun bindAdminViewModel(adminViewModel: AdminViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
