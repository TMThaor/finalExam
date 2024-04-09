package com.example.finalexam.Company.adapter;

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
import com.example.finalexam.Company.model.Company;
import com.example.finalexam.R;
import com.example.finalexam.job.adapter.JobAdapter;
import com.example.finalexam.job.model.Job;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder>{

    public ArrayList<Company> listCompany;
    Context mContext;

    public CompanyAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_company_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Company com=listCompany.get(position);
        if(com==null){
            return;
        }
        else {
            StorageReference storef= FirebaseStorage.getInstance().getReference();
            storef.child("logos/"+com.getLogo()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(mContext).load(uri).into(holder.ivLogo);
                }
            });
            holder.tvCompanyName.setText(com.getCompanyName());
            holder.tvCategory.setText((com.getCategory()));
        }
    }

    @Override
    public int getItemCount() {
        if(listCompany!=null) return listCompany.size();
        return 0;
    }
    public void setCompanies(ArrayList<Company> companies) {
        this.listCompany = companies;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivLogo;
        private TextView tvCompanyName;
        private TextView tvCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLogo=itemView.findViewById(R.id.ivLogo);
            tvCompanyName=itemView.findViewById(R.id.tvCompanyName);
            tvCategory=itemView.findViewById(R.id.tvCategory);
        }
    }
}
