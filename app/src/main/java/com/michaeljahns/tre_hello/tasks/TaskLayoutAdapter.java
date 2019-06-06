package com.michaeljahns.tre_hello.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michaeljahns.tre_hello.R;

import java.util.List;

public class TaskLayoutAdapter extends RecyclerView.Adapter<TaskLayoutAdapter.TaskHolder> {

    public static class TaskHolder extends RecyclerView.ViewHolder {
        public TextView taskName;
        public TextView taskDescription;
        public TextView taskStatus;
        public ImageView taskPeer;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.viewTaskName);
            taskDescription = itemView.findViewById(R.id.viewTaskDescription);
            taskStatus = itemView.findViewById(R.id.viewTaskStatus);
            taskPeer = itemView.findViewById(R.id.viewTask_Image);

            taskPeer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: Create Activity for single Task view
                    // Todo: on that view allow users to self assign tasks using their userID
                    // Todo: Display Assigned status in a way that makes sense
                }
            });

        }

        public void setTask(Task task) {
            this.taskName.setText(task.getTask());
            this.taskDescription.setText(task.getDescription());
            this.taskStatus.setText("Status: " + task.getStatus());
        }
    }

    private List<Task> tasks;

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    public TaskLayoutAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.task_model, parent, false);
        TaskHolder holder = new TaskHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task task = tasks.get(position);
        holder.setTask(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}