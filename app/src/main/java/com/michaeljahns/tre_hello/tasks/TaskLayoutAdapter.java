package com.michaeljahns.tre_hello.tasks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michaeljahns.tre_hello.R;
import com.michaeljahns.tre_hello.tasks.activities.SingleTaskActivity;

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
        }

        public void setTask(final Task task) {
            final Task farts = task;
            this.taskName.setText(task.getTask());
            this.taskDescription.setText(task.getDescription());
            this.taskStatus.setText("Status: " + task.getStatus());
            this.taskPeer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, SingleTaskActivity.class);
                    intent.putExtra("taskID", task.getTaskID());
                    context.startActivity(intent);
                }
            });
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