package solutions.rollers.collegemessageboard.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import solutions.rollers.collegemessageboard.Models.NewsItem;
import solutions.rollers.collegemessageboard.R;

/**
 * Created by Nihanth Charan Mutluru on 22-06-2017.
 */


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsItem> newsItemList;

    public NewsAdapter(List<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsItem newsItem = newsItemList.get(position);
        holder.title.setText(newsItem.getTitle());
        holder.message.setText(newsItem.getMessage());
        holder.date_time.setText(newsItem.getDate_time());
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,message,date_time;
        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.news_title);
            message = (TextView) itemView.findViewById(R.id.news_message);
            date_time = (TextView) itemView.findViewById(R.id.news_date);
        }
    }
}