package com.example.firstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.PostsViewHolder> {

    Context context;
    ArrayList<Chatposts> chatposts;

    public Adapter() {

    }

    public Adapter(Context c, ArrayList<Chatposts> chat) {
        context = c;
        chatposts = chat;
    }

    @NonNull
    @Override
    public Adapter.PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostsViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_chats,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.PostsViewHolder holder, int position) {

        holder.time.setText(chatposts.get(position).getTime());
        holder.useremail.setText(chatposts.get(position).getUsershop());
        holder.messages.setText(chatposts.get(position).getMessage());
    }
    @Override
    public int getItemCount() {
        return chatposts.size();
    }


    public class PostsViewHolder extends RecyclerView.ViewHolder{

        TextView time, messages, useremail;

        public PostsViewHolder(View itemView) {

            super(itemView);

            useremail = itemView.findViewById(R.id.useremail);
            time = itemView.findViewById(R.id.time);
            messages = itemView.findViewById(R.id.message);
        }
    }
}
