package com.michaeljahns.tre_hello;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskLayoutAdapter extends RecyclerView.Adapter<TaskLayoutAdapter.TaskHolder> {

    public static class TaskHolder extends RecyclerView.ViewHolder {
        public TextView taskName;
        public TextView taskDescription;
        public TextView taskStatus;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            this.taskName = itemView.findViewById(R.id.viewTaskName);
            this.taskDescription = itemView.findViewById(R.id.viewTaskDescription);
            this.taskStatus = itemView.findViewById(R.id.viewTaskStatus);
        }

        public void setTask(Task task) {
            this.taskName.setText(task.getTask());
            this.taskDescription.setText(task.getDescription());
            this.taskStatus.setText(task.getStatus());
        }
    }

    private List<Task> tasks;

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