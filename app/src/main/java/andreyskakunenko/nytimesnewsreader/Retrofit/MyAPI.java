package andreyskakunenko.nytimesnewsreader.Retrofit;

import andreyskakunenko.nytimesnewsreader.Model.News;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MyAPI {
    @GET
    Observable<News> getResults(@Url String url);
}