package onl.toth.apps.everylife.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val taskListApp: Application) {

    @Provides
    @Singleton
    fun provideContext(): Application = taskListApp
}