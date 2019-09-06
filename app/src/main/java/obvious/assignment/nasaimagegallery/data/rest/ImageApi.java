package obvious.assignment.nasaimagegallery.data.rest;

import obvious.assignment.nasaimagegallery.data.model.ImageDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageApi {

    @GET("/planetary/apod?api_key=4fZl1ekUFuSsVTYxFoxoZ34azNwS3NkF9gkF4wN4")
    Call<ImageDetails> getApodImage(@Query("date") String date);
}
