package com.example.testwe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    Context context;
    List<User> users;
    public UserListAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    public void adapFilterList(ArrayList<User> filterList) {
        users = filterList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.username.setText(user.getName() + " " + user.getLname());
        holder.lastMessage.setText(user.getLastMessage());
        holder.profileImage.setImageResource(R.drawable.ic_launcher_background);

        FirebaseDatabase.getInstance().getReference().child("chats").child(FirebaseAuth.getInstance().getUid() + user.getId())
                .orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        holder.lastMessage.setText(snapshot1.child("message").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            });

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false);
        return new UserListAdapter.UserViewHolder(view);
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView username, lastMessage;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.username);
            lastMessage = itemView.findViewById(R.id.lastMsg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),chatDetailActivity.class);
                    intent.putExtra("userId", users.get(getAdapterPosition()).getId());
                    intent.putExtra("profilePic", users.get(getAdapterPosition()).getPicture());
                    intent.putExtra("userName", users.get(getAdapterPosition()).getName());

                    itemView.getContext().startActivity(intent);
                }
            });
        }

    }
}
