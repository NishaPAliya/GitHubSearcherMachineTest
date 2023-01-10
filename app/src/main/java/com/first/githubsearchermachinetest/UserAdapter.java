package com.first.githubsearchermachinetest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.holder> {
    @NonNull
    ArrayList<UserModel> userList;
    Context context;

    public UserAdapter(@NonNull ArrayList<UserModel> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }
    public  void setfilteredlist(ArrayList<UserModel> filteredlist)
    {
        this.userList = filteredlist;
        notifyDataSetChanged();
    }

    @Override
    public UserAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_user_layout,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.holder holder, int position) {
        UserModel userModel = userList.get(position);
        holder.name.setText(userModel.getLogin());
        holder.repo.setText(userModel.getRepos_url());

        Glide.with(context)
                .load(userModel.getAvatar_url())
                .into(holder.profileImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SecondScreen.class);
                intent.putExtra("name",holder.name.getText().toString());
                intent.putExtra("avatarimage",userModel.getAvatar_url());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView name,repo;
        public holder(@NonNull View itemView) {
            super(itemView);
            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            name = (TextView) itemView.findViewById(R.id.name);
            repo = (TextView) itemView.findViewById(R.id.fork);
        }
    }
}
