package com.exemple.www.gmission;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import models.Mission;

public class MissionAdapter extends RecyclerView.Adapter<MissionAdapter.MissionViewHolder> {

    private Context context;
    private ArrayList<Mission> list;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public MissionAdapter(Context context , ArrayList<Mission> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MissionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.mission_layout, viewGroup, false);
        return new MissionAdapter.MissionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MissionViewHolder missionViewHolder, int i) {

        missionViewHolder.txtType.setText(list.get(i).getType());
        missionViewHolder.txtDate.setText(list.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MissionViewHolder extends RecyclerView.ViewHolder {

        public TextView txtType,txtDate;

        public MissionViewHolder(View itemView)
        {
            super(itemView);
            txtType = itemView.findViewById(R.id.txtType);
            txtDate = itemView.findViewById(R.id.txtDate);

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
