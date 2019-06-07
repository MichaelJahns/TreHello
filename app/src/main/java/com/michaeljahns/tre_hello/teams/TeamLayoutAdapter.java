package com.michaeljahns.tre_hello.teams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michaeljahns.tre_hello.R;
import com.michaeljahns.tre_hello.tasks.Task;

import java.util.List;

public class TeamLayoutAdapter extends RecyclerView.Adapter<TeamLayoutAdapter.TeamHolder> {

    public static class TeamHolder extends RecyclerView.ViewHolder {
        public TextView memberName;

        public TeamHolder(@NonNull View itemView) {
            super(itemView);

            memberName = itemView.findViewById(R.id.viewMemberName);
        }

        public void setMemberName(final String name) {
            memberName.setText(name);
        }
    }

    private List<String> members;

    public TeamLayoutAdapter(List<String> members) {
        this.members = members;
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
        String member = members.get(position);
        holder.setMemberName(member);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }
}
