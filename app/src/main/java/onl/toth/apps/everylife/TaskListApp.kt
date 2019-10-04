package onl.toth.apps.everylife

import android.app.Application
import onl.toth.apps.everylife.di.AppComponent
import onl.toth.apps.everylife.di.AppModule
import onl.toth.apps.everylife.di.DaggerAppComponent

class TaskListApp : Application() {

    lateinit var appComponent: AppComponent

    private fun initAppComponent(app: TaskListApp): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .taskApiModule(TaskApiModule()).build()
    }

    companion object {
        @get:Synchronized
        lateinit var application: TaskListApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        appComponent = initAppComponent(this)
    }
}