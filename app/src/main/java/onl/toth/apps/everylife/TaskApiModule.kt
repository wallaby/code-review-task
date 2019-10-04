package onl.toth.apps.everylife

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import onl.toth.apps.everylife.network.TaskApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


private const val BASE_URL = "https://adam-deleteme.s3.amazonaws.com/"

@Module
class TaskApiModule {

    @Provides
    @Singleton
    fun provideTaskApiClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, randomUserApiClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(randomUserApiClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideTaskApi(retrofit: Retrofit): TaskApiService {
        return retrofit.create(TaskApiService::class.java)
    }
}