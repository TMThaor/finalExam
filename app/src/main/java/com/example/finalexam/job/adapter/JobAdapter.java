package com.example.finalexam.job.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.job.model.Job;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    private List<Job> jobs;
    private OnJobClickListener onJobClickListener;

    public JobAdapter(List<Job> jobs, OnJobClickListener listener) {
        this.jobs = jobs;
        this.onJobClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job job = jobs.get(position);
        holder.bind(job);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onJobClickListener != null) {
                    onJobClickListener.onJobClick(job); // Truyền job được nhấn vào onJobClick
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textJobTitle;
        TextView textCompany;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textJobTitle = itemView.findViewById(R.id.text_job_title);
            textCompany = itemView.findViewById(R.id.text_company);
            itemView.setOnClickListener(this);
        }

        public void bind(Job job) {
            textJobTitle.setText(job.getTitle());
            textCompany.setText(job.getCompany());
        }

        @Override
        public void onClick(View v) {
            if (onJobClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onJobClickListener.onJobClick(jobs.get(position));
                }
            }
        }
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
        notifyDataSetChanged();
    }


    public interface OnJobClickListener {
        void onJobClick(Job job);
    }
}
