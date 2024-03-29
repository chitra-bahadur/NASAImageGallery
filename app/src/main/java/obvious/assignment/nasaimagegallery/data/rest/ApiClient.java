package obvious.assignment.nasaimagegallery.data.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://api.nasa.gov";

    private static Retrofit mRetrofit;

    public static Retrofit getClient() {
        if(mRetrofit == null) {
            mRetrofit =
                    new Retrofit.Builder()
                    .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                            .build();
        }
        return mRetrofit;
    }
}
