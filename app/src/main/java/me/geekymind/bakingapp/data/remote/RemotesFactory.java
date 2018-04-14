package me.geekymind.bakingapp.data.remote;

import com.google.gson.GsonBuilder;
import me.geekymind.bakingapp.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohamed Ibrahim on 4/13/18.
 */
public class RemotesFactory {

  private RemotesFactory() {
  }

  public static RecipesRemote newRecipesRemote() {
    Retrofit retrofit = new Retrofit.Builder().client(getOkHttpClient())
        .baseUrl("http://google.com.eg/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
        .build();
    return retrofit.create(RecipesRemote.class);
  }

  private static OkHttpClient getOkHttpClient() {
    final OkHttpClient.Builder clientBuilder =
        new OkHttpClient.Builder().addInterceptor(loggingInterceptor());
    return clientBuilder.build();
  }

  private static HttpLoggingInterceptor loggingInterceptor() {
    return new HttpLoggingInterceptor().setLevel(
        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
  }
}
