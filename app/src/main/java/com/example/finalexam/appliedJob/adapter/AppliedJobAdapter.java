package com.example.finalexam.appliedJob.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalexam.R;
import com.example.finalexam.job.model.Job;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AppliedJobAdapter extends RecyclerView.Adapter<AppliedJobAdapter.ViewHolder> {

    private List<Job> appliedJobs;
    private OnJobClickListener onJobClickListener;

    Context mContext;

    public AppliedJobAdapter(List<Job> appliedJobs, OnJobClickListener listener, Context mContext) {
        this.appliedJobs = appliedJobs;
        this.onJobClickListener = listener;
        this.mContext = mContext;
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
        ImageView imgCompany;
        TextView textJobTitle, textCompany, textLocation, textExp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCompany = itemView.findViewById(R.id.img_company);
            textJobTitle = itemView.findViewById(R.id.text_job_title);
            textCompany = itemView.findViewById(R.id.text_company);
            textLocation = itemView.findViewById(R.id.text_location);
            textExp = itemView.findViewById(R.id.text_exp);
            itemView.setOnClickListener(this);
        }

        public void bind(Job appliedJob) {
            textJobTitle.setText(appliedJob.getTitle());
            textCompany.setText(appliedJob.getCompany());
            textLocation.setText(appliedJob.getShortAddress());
            textExp.setText(appliedJob.getExp());
            loadCompanyLogo(appliedJob.getCompanyLogo(), imgCompany);
        }
        private void loadCompanyLogo(String logoUrl, ImageView imageView) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("logos/" + logoUrl);
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(mContext).load(uri).into(imageView);
                }
            });
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
