package com.example.shmbrowser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shmbrowser.MainActivity;
import com.example.shmbrowser.Model.Sites;
import com.example.shmbrowser.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SitesAdapter extends RecyclerView.Adapter<SitesAdapter.MyViewHolder> {
    Context context;
    List<Sites>sitesList;

    public SitesAdapter(Context context, List<Sites> sitesList) {
        this.context = context;
        this.sitesList = sitesList;
    }

    @NonNull
    @Override
    public SitesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.shortcut,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SitesAdapter.MyViewHolder holder, final int position) {
        holder.circleImageView.setImageResource(sitesList.get(position).getImageid());
        holder.url.setText(sitesList.get(position).getUrl());
        holder.Title.setText(sitesList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT,sitesList.get(position).getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sitesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView url,Title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.sitesicon);
            url=itemView.findViewById(R.id.url);
            Title=itemView.findViewById(R.id.title);

        }
    }
}
