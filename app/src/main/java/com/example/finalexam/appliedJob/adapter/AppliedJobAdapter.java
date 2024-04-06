package com.example.finalexam.appliedJob.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.job.model.Job;

import java.util.List;

public class AppliedJobAdapter extends RecyclerView.Adapter<AppliedJobAdapter.ViewHolder> {

    private List<Job> appliedJobs;
    private OnJobClickListener onJobClickListener;

    public AppliedJobAdapter(List<Job> jobs, OnJobClickListener listener) {
        this.appliedJobs = jobs;
        this.onJobClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_appliedjob, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job appliedJob = appliedJobs.get(position);
        holder.bind(appliedJob);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onJobClickListener != null) {
                    onJobClickListener.onAppliedJobClick(appliedJob); // Truyền appliedJob được nhấn vào onJobClick
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appliedJobs.size();
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

        public void bind(Job appliedJob) {
            textJobTitle.setText(appliedJob.getTitle());
            textCompany.setText(appliedJob.getCompany());
        }

        @Override
        public void onClick(View v) {
            if (onJobClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onJobClickListener.onAppliedJobClick(appliedJobs.get(position));
                }
            }
        }
    }

    public void setAppliedJobs(List<Job> appliedJobs) {
        this.appliedJobs = appliedJobs;
        notifyDataSetChanged();
    }

    public interface OnJobClickListener {
        void onAppliedJobClick(Job appliedJob);
    }
}
