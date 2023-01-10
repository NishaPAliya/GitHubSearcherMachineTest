package com.first.githubsearchermachinetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.repoholder> {
    @NonNull
    ArrayList<RepoModel> repoList;
    Context context;

    public RepoAdapter(@NonNull ArrayList<RepoModel> repoList, Context context) {
        this.repoList = repoList;
        this.context = context;
    }
    public  void setfilteredlist(ArrayList<RepoModel> filteredlist)
    {
        this.repoList = filteredlist;
        notifyDataSetChanged();
    }

    @Override
    public repoholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_repos_layout,parent,false);
        return new RepoAdapter.repoholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull repoholder holder, int position) {
        RepoModel repoModel = repoList.get(position);
        holder.repo.setText("Repo Name : "+repoModel.getReponame());
        holder.fork.setText(repoModel.getRepoforks()+" Forks");
        holder.star.setText(repoModel.getRepostars()+" Stars");

    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }
    class repoholder extends RecyclerView.ViewHolder
    {
        TextView repo,fork,star;

        public repoholder(@NonNull View itemView) {
            super(itemView);
            repo = (TextView) itemView.findViewById(R.id.reponame);
            fork = (TextView) itemView.findViewById(R.id.fork);
            star = (TextView) itemView.findViewById(R.id.stars);
        }
    }
}
