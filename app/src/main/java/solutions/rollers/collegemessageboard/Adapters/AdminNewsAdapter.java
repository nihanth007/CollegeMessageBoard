package solutions.rollers.collegemessageboard.Adapters;

import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import solutions.rollers.collegemessageboard.AdminNewsFeed;
import solutions.rollers.collegemessageboard.CMB;
import solutions.rollers.collegemessageboard.Models.AdminNewsItem;
import solutions.rollers.collegemessageboard.R;

/**
 * Created by Nihanth Charan Mutluru on 22-06-2017.
 */


public class AdminNewsAdapter extends RecyclerView.Adapter<AdminNewsAdapter.ViewHolder> {

    private List<AdminNewsItem> newsItemList;

    public AdminNewsAdapter(List<AdminNewsItem> newsItemList) {
        this.newsItemList = newsItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_news_card,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            final AdminNewsItem newsItem = newsItemList.get(position);
            holder.title.setText(newsItem.getTitle());
            holder.message.setText(newsItem.getMessage());
            holder.date_time.setText(newsItem.getDate_time());
            final int msg_id = newsItem.getMsg_id();
            holder.progressBar.setProgress((int) newsItem.getProgress());
            holder.progressBar.setMax(100);
            holder.opened.setText(String.valueOf(newsItem.getOpened()));
            holder.sent.setText(String.valueOf(newsItem.getSent()));
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CMB.delete_message(msg_id,newsItem.context);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,message,date_time,sent,opened;
        public ProgressBar progressBar;
        public ImageButton delete;
        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.news_title);
            message = (TextView) itemView.findViewById(R.id.news_message);
            date_time = (TextView) itemView.findViewById(R.id.news_date);
            sent = (TextView) itemView.findViewById(R.id.sent);
            opened = (TextView) itemView.findViewById(R.id.opened);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progress);
            delete = (ImageButton)itemView.findViewById(R.id.delete_button);

        }
    }
}