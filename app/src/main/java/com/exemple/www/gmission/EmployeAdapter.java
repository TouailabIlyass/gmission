package com.exemple.www.gmission;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import models.Employe;

public class EmployeAdapter extends RecyclerView.Adapter<EmployeAdapter.EmployeViewHolder> {

    private Context context;
    private ArrayList<Employe> list;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public EmployeAdapter(Context context , ArrayList<Employe> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EmployeAdapter.EmployeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.employe_layout, viewGroup, false);
        return new EmployeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeAdapter.EmployeViewHolder employeViewHolder, int i) {

        employeViewHolder.txtCin.setText(list.get(i).getCin());
        employeViewHolder.txtNom.setText(list.get(i).getNom()+" "+list.get(i).getPrenom());
        employeViewHolder.txtEtat.setText("etat");
    }

    public String getItemCin(int position) {
        return list.get(position).getCin();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EmployeViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView txtCin,txtNom,txtEtat;

        public EmployeViewHolder(View itemView)
        {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            txtCin = itemView.findViewById(R.id.txtCin);
            txtNom = itemView.findViewById(R.id.txtNom);
            txtEtat = itemView.findViewById(R.id.txtEtat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }
}
