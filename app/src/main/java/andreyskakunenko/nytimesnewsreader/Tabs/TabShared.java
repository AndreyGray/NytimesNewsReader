package andreyskakunenko.nytimesnewsreader.Tabs;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import andreyskakunenko.nytimesnewsreader.Adapters.MyRecyclerAdapter;
import andreyskakunenko.nytimesnewsreader.Retrofit.Common;
import andreyskakunenko.nytimesnewsreader.Model.News;
import andreyskakunenko.nytimesnewsreader.Model.Result;
import andreyskakunenko.nytimesnewsreader.R;
import andreyskakunenko.nytimesnewsreader.Retrofit.MyAPI;
import andreyskakunenko.nytimesnewsreader.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class TabShared extends Fragment {
    View view;
    MyAPI mAPI;
    RecyclerView mRecyclerView;
    CompositeDisposable mCompositeDisposable;
    TextView noConnect;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Common.isNetworkAvailable(getContext())){
            mCompositeDisposable = new CompositeDisposable();
            Retrofit retrofit = RetrofitClient.getInstance();
            mAPI = retrofit.create(MyAPI.class);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_shared, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_shared);
        mRecyclerView.setHasFixedSize(true);
        noConnect = view.findViewById(R.id.no_internet_sh);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (Common.isNetworkAvailable(getContext())) {
            fetchData();
        }else {
            noConnect.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void fetchData() {
        mCompositeDisposable.add(mAPI.getResults(Common.getAPIUrl("mostshared"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<News>() {
                    @Override
                    public void accept(News news) throws Exception {
                        displayData(news.getResults());
                    }
                })
        );
    }

    private void displayData(List<Result> results) {
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(getContext(),results);
        mRecyclerView.setAdapter(adapter);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mCompositeDisposable!=null)mCompositeDisposable.clear();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
