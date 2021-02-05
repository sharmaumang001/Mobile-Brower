package com.sharmaumang001.srpbrowser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sharmaumang001.srpbrowser.R;
import com.sharmaumang001.srpbrowser.model.Sites;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SitesAdapter extends RecyclerView.Adapter<SitesAdapter.MyViewHolder> {
    final Context context;
    final RecyclerViewClickListener mListener;
    final List<Sites> sitesList;

    public SitesAdapter(Context context, RecyclerViewClickListener mListener, List<Sites> sitesList) {
        this.context = context;
        this.mListener = mListener;
        this.sitesList = sitesList;
    }

    @NonNull
    @Override
    public SitesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shortcut, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SitesAdapter.MyViewHolder holder, final int position) {
        holder.circleImageView.setImageResource(sitesList.get(position).getImageId());
        holder.url.setText(sitesList.get(position).getUrl());
        holder.Title.setText(sitesList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return sitesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final CircleImageView circleImageView;
        final TextView url;
        final TextView Title;
        private final RecyclerViewClickListener mListener;

        MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            circleImageView = itemView.findViewById(R.id.sites_icon);
            url = itemView.findViewById(R.id.url);
            Title = itemView.findViewById(R.id.title);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }
}
