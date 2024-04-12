package com.example.finalexam.job.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.job.model.Experience;

import java.util.ArrayList;

public class ExperienceCVDetailAdapter extends RecyclerView.Adapter<ExperienceCVDetailAdapter.ViewHolder> {

    private ArrayList<Experience> exps;

    public ExperienceCVDetailAdapter(ArrayList<Experience> exps) {
        this.exps = exps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_exp_cv_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.companyName.setText(exps.get(position).getCompanyName());
        holder.role.setText(exps.get(position).getRole());
        holder.startDate.setText(exps.get(position).getTimeStart());
        holder.endDate.setText(exps.get(position).getTimeEnd());
        holder.Des.setText(exps.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return exps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView companyName,role,endDate,startDate,Des;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName=itemView.findViewById(R.id.listExpCVDetailCompanyName);
            role=itemView.findViewById(R.id.listExpCVDetailRole);
            endDate=itemView.findViewById(R.id.listExpCVDetailEndDate);
            startDate=itemView.findViewById(R.id.listExpCVDetailStartDate);
            Des=itemView.findViewById(R.id.listExpCVDetailDes);
        }
    }
    public void setData(ArrayList<Experience> list){
        this.exps=list;
        notifyDataSetChanged();
    }
}
