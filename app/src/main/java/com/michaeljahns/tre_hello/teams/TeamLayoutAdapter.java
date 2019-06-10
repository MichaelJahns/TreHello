package com.michaeljahns.tre_hello.teams;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michaeljahns.tre_hello.R;
import com.michaeljahns.tre_hello.tasks.Task;
import com.michaeljahns.tre_hello.tasks.activities.SingleTaskActivity;
import com.michaeljahns.tre_hello.user.UserProfileActivity;

import java.util.List;

public class TeamLayoutAdapter extends RecyclerView.Adapter<TeamLayoutAdapter.TeamHolder> {

    public static class TeamHolder extends RecyclerView.ViewHolder {
        public TextView memberName;
        public TextView memberID;

        public TeamHolder(@NonNull View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.viewMemberName);
            memberID = itemView.findViewById(R.id.viewMemberID);
        }

        public void setMembers(String userName, final String userID) {
            memberName.setText(userName);
            memberID.setText(userID);
            memberName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, UserProfileActivity.class);
                    intent.putExtra("pageUserID", userID);
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<String> membersNames;
    private List<String> memberIDs;

    public TeamLayoutAdapter(List<String> memberNames, List<String> memberIDs) {
        this.membersNames = memberNames;
        this.memberIDs = memberIDs;
    }

    @NonNull
    @Override
    public TeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.team_fragment, parent, false);
        TeamHolder holder = new TeamHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamLayoutAdapter.TeamHolder holder, int position) {
        String memberName = membersNames.get(position);
        String memberID = memberIDs.get(position);
        holder.setMembers(memberName, memberID);
    }

    @Override
    public int getItemCount() {
        return membersNames.size();
    }
}
