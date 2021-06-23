package onl.toth.apps.everylife.di

import dagger.Component
import onl.toth.apps.everylife.ui.MainActivity
import onl.toth.apps.everylife.TaskApiModule
import onl.toth.apps.everylife.ViewModelModule
import onl.toth.apps.everylife.data.DatabaseModule
import onl.toth.apps.everylife.ui.tasks.TaskListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, TaskApiModule::class,
    ViewModelModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(movieListFragment: TaskListFragment)
}