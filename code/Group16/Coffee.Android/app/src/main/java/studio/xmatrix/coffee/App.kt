package studio.xmatrix.coffee

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import com.lzy.ninegrid.NineGridView
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import studio.xmatrix.coffee.data.model.MyObjectBox
import studio.xmatrix.coffee.ui.NightModeConfig
import studio.xmatrix.coffee.inject.AppInjector
import timber.log.Timber
import android.graphics.Bitmap
import android.widget.ImageView
import studio.xmatrix.coffee.data.store.DefaultSharedPref


class App : Application() {

    lateinit var boxStore: BoxStore
        private set

    override fun onCreate() {
        super.onCreate()

        // 设置夜间模式
        val isNightAuto = NightModeConfig.getInstance().isNightAuto(this)
        if (!isNightAuto) {
            val isNightMode = NightModeConfig.getInstance().getNightMode(this)
            if (isNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
        }

        NineGridView.setImageLoader(MyImageLoader())

        boxStore = MyObjectBox.builder().androidContext(this).build()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            AndroidObjectBrowser(boxStore).start(this)
        }

        DefaultSharedPref.init(this)
        AppInjector.init(this)
    }

    private inner class MyImageLoader : NineGridView.ImageLoader {

        override fun onDisplayImage(context: Context, imageView: ImageView, url: String) {
            imageView.setImageDrawable(getDrawable(R.drawable.ic_like))
        }

        override fun getCacheImage(url: String): Bitmap? {
            return null
        }
    }
}
