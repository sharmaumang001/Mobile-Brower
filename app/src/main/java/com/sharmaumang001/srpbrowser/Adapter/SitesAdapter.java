package com.sharmaumang001.srpbrowser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sharmaumang001.srpbrowser.Model.Sites;
import com.sharmaumang001.srpbrowser.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SitesAdapter extends RecyclerView.Adapter<SitesAdapter.MyViewHolder> {
    Context context;
     RecyclerViewClicklistner mListener;
    List<Sites>sitesList;

    public SitesAdapter(Context context, RecyclerViewClicklistner mListener, List<Sites> sitesList) {
        this.context = context;
        this.mListener = mListener;
        this.sitesList = sitesList;
    }

    @NonNull
    @Override
    public SitesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.shortcut,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view,mListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SitesAdapter.MyViewHolder holder, final int position) {
        holder.circleImageView.setImageResource(sitesList.get(position).getImageid());
        holder.url.setText(sitesList.get(position).getUrl());
        holder.Title.setText(sitesList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return sitesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView circleImageView;
        TextView url,Title;
        private RecyclerViewClicklistner mListener;

        MyViewHolder(View itemView, RecyclerViewClicklistner listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            circleImageView=itemView.findViewById(R.id.sitesicon);
            url=itemView.findViewById(R.id.url);
            Title=itemView.findViewById(R.id.title);
        }
        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }
}
