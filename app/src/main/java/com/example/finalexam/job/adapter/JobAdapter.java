package com.example.finalexam.job.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalexam.R;
import com.example.finalexam.job.model.Job;
import com.example.finalexam.my_interface.IClickItemJobListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    private ArrayList<Job> listJob;
    IClickItemJobListener iClickItemJobListener;

    public JobAdapter(ArrayList<Job> listJob, IClickItemJobListener listener) {
        this.listJob = listJob;
        this.iClickItemJobListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Job job=listJob.get(position);
        if(job==null){
            return;
        }
        else{
//            holder.ivCompany();
            holder.tvJobTitle.setText(job.getTitle());
            holder.tvCompany.setText(job.getCompany());
            holder.tvLocation.setText(job.getShortAddress());
            holder.tvExperience.setText(job.getExp());
            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemJobListener.onClickItemJob(job);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if(listJob!=null) return listJob.size();
        return 0;
    }
    public void setJobs(ArrayList<Job> jobs) {
        this.listJob = jobs;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutItem;
        private ImageView ivCompany;
        private TextView tvJobTitle;
        private TextView tvCompany;
        private TextView tvLocation;
        private TextView tvExperience;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            layoutItem=itemView.findViewById(R.id.layoutItem);
            ivCompany = itemView.findViewById(R.id.ivCompany);
            tvJobTitle = itemView.findViewById(R.id.tvJobTitle);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvExperience = itemView.findViewById(R.id.tvExperience);
        }

    }
}




