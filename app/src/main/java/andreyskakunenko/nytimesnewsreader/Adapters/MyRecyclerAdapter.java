package andreyskakunenko.nytimesnewsreader.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import andreyskakunenko.nytimesnewsreader.DetailActivity;
import andreyskakunenko.nytimesnewsreader.Interface.ItemClickListener;
import andreyskakunenko.nytimesnewsreader.Model.Result;
import andreyskakunenko.nytimesnewsreader.R;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>  {

    private Context mContext;
    private List<Result> mResults;


    public MyRecyclerAdapter(Context context, List<Result> results) {
        mContext = context;
        mResults = results;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.newsName.setText(mResults.get(i).getTitle());
        holder.newsAuthor.setText(mResults.get(i).getByline());
        holder.newsSection.setText(mResults.get(i).getSection());
        holder.newsDate.setText(mResults.get(i).getPublishedDate());


        String urlImage = mResults.get(i).getMedia().get(0).getMediaMetadata().get(1).getUrl();

        Picasso.get()
                .load(urlImage)
                .error(R.drawable.article)
                .placeholder(R.drawable.progress)
                .into(holder.newsImage);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent detail = new Intent(mContext,DetailActivity.class);
                detail.putExtra("webURL",mResults.get(position).getUrl());
                detail.putExtra("title",mResults.get(position).getTitle());
                detail.putExtra("author",mResults.get(position).getPublishedDate());
                detail.putExtra("section",mResults.get(position).getSection());
                detail.putExtra("date",mResults.get(position).getPublishedDate());
                detail.putExtra("pictureUrl",mResults.get(position).getMedia().get(0).getMediaMetadata().get(1).getUrl());
                detail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(detail);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}
