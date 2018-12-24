package andreyskakunenko.nytimesnewsreader.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import andreyskakunenko.nytimesnewsreader.Interface.ItemClickListener;
import andreyskakunenko.nytimesnewsreader.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public CircleImageView newsImage;
    public TextView newsName, newsSection, newsAuthor, newsDate;
    private ItemClickListener itemClickListener;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        newsImage = itemView.findViewById(R.id.news_picture);

        newsName = itemView.findViewById(R.id.news_title);
        newsSection = itemView.findViewById(R.id.news_section);
        newsAuthor = itemView.findViewById(R.id.news_author);
        newsDate = itemView.findViewById(R.id.news_date);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
