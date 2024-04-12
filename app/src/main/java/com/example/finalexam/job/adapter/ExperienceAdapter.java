package com.example.finalexam.job.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.job.model.Experience;
import com.example.finalexam.my_interface.IRemoveUserExp;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ViewHolder>{

    ArrayList<Experience> expList;
    IRemoveUserExp iRemoveUserExp;

    public ExperienceAdapter(ArrayList<Experience> expList, IRemoveUserExp iRemoveUserExp) {
        this.expList = expList;
        this.iRemoveUserExp = iRemoveUserExp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_exp,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Experience e=expList.get(position);
        holder.tvRole.setText(e.getRole());
        holder.tvCompany.setText(e.getCompanyName());
        holder.tvTimeStart.setText(e.getTimeStart().toString());
        holder.tvTimeEnd.setText(e.getTimeEnd().toString());
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iRemoveUserExp.removeUserExp(e);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(expList.size()!=0) return expList.size();
        return 0;
    }

    public void setData(ArrayList<Experience> list){
        this.expList=list;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{


        private TextView tvRole;
        private TextView tvCompany;
        private TextView tvTimeStart;
        private TextView tvTimeEnd;
        private TextView tvDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRole=itemView.findViewById(R.id.tvLstExpRole);
            tvCompany=itemView.findViewById(R.id.tvLstExpCompany);
            tvTimeStart=itemView.findViewById(R.id.tvLstExpTimeStart);
            tvTimeEnd=itemView.findViewById(R.id.tvLstExpTimeEnd);
            tvDelete=itemView.findViewById(R.id.tvDeleteItemExp);

        }
    }

}
