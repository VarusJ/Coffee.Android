package studio.xmatrix.coffee.inject

import studio.xmatrix.coffee.App
import studio.xmatrix.coffee.inject.component.AppComponent
import studio.xmatrix.coffee.inject.component.DaggerAppComponent
import studio.xmatrix.coffee.ui.admin.AdminActivity
import studio.xmatrix.coffee.ui.detail.DetailHandler
import studio.xmatrix.coffee.ui.square.SquareHandler

class AppInjector private constructor() {

    companion object {

        private lateinit var component: AppComponent

        fun init(app: App) {
            component = DaggerAppComponent.builder().app(app).build()
        }

        fun inject(injectable: Injectable) {
            if (injectable is AdminActivity) {
                component.inject(injectable)
            } else if (injectable is SquareHandler) {
                component.inject(injectable)
            }  else if (injectable is DetailHandler) {
                component.inject(injectable)
            } else {
                throw IllegalArgumentException("Class not found in AppComponent")
            }
        }
    }
}