package andreyskakunenko.nytimesnewsreader.Tabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import andreyskakunenko.nytimesnewsreader.Adapters.MyViewHolder;
import andreyskakunenko.nytimesnewsreader.DetailActivity;
import andreyskakunenko.nytimesnewsreader.Interface.ItemClickListener;
import andreyskakunenko.nytimesnewsreader.PdfViewActivity;
import andreyskakunenko.nytimesnewsreader.Retrofit.Common;
import andreyskakunenko.nytimesnewsreader.data.FavLab;
import andreyskakunenko.nytimesnewsreader.Model.Article;
import andreyskakunenko.nytimesnewsreader.R;

public class TabFavorites extends Fragment {

    private RecyclerView mFavRecyclerView;
    TextView mTextView;
    View mView;
    FavLab favLab;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView =  inflater.inflate(R.layout.fragment_tab_favorites, container, false);
        mFavRecyclerView = mView.findViewById(R.id.lstNewsFav);
        mFavRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTextView = mView.findViewById(R.id.no_favorites);
        if(FavLab.get(getActivity()).getArticles().size() <= 0) {
            mTextView.setVisibility(View.VISIBLE);
        } else {
            updateUI();
        }
        return mView;
    }

    public void updateUI() {
        favLab = FavLab.get(getActivity());
        List<Article> articles = favLab.getArticles();
        mFavRecyclerView.setAdapter(new FavAdapter(articles));

    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class FavAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<Article> mArticles;

        FavAdapter(List<Article> articles) {
            mArticles = articles;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item,parent,false);
             return  new MyViewHolder(view);
    }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Article article = mArticles.get(position);

            holder.newsName.setText(article.getTitle());
            holder.newsAuthor.setText(article.getAuthor());
            holder.newsSection.setText(article.getSection());
            holder.newsDate.setText(article.getDate());


            Picasso.get()
                    .load(article.getPicURL())
                    .error(R.drawable.article)
                    .placeholder(R.drawable.progress)
                    .into(holder.newsImage);

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if(Common.isNetworkAvailable(Objects.requireNonNull(getContext()))) {
                        Intent detail = new Intent(getActivity(), DetailActivity.class);
                        detail.putExtra("webURL", mArticles.get(position).getWebURL());
                        detail.putExtra("id",mArticles.get(position).getId().toString());
                        detail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(detail);
                    } else{
                        Intent intentPdf = new Intent(getActivity(),PdfViewActivity.class);
                        intentPdf.putExtra("id",mArticles.get(position).getId().toString());
                        intentPdf.putExtra("content",mArticles.get(position).getContent());
                        startActivity(intentPdf);

                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mArticles.size();
        }



    }

}
