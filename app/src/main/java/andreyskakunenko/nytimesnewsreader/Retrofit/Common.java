package andreyskakunenko.nytimesnewsreader.Retrofit;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {
    private static final String BASE_URL = "http://api.nytimes.com/svc/mostpopular/v2/";
    private  static final String PARAMS = "/all-sections/30.json?api-key=";
    private static final String API_KEY = "6275f074f3af419dbce16209ce5d0a6c";


    public static String getAPIUrl(String source)
    {
        return BASE_URL+source+PARAMS+API_KEY;
    }

    /*public static Observable<Boolean> isInternetOn(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return Observable.just(activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }*/

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isConnected();
    }

}
