package com.exemple.www.gmission;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import models.Transport;

public class TransportAdapter extends RecyclerView.Adapter<TransportAdapter.TransportViewHolder> {
    private Context context;
    private ArrayList<Transport> list;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public TransportAdapter(Context context , ArrayList<Transport> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TransportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.transport_layout, viewGroup, false);
        return new TransportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransportViewHolder transportViewHolder, int i) {

        Transport current =  list.get(i);
        if(current.getType()==1)
        {transportViewHolder.txtDestination.setText(current.getAvion().getDestination());
        transportViewHolder.txtNumbon.setText(current.getAvion().getNumBon());
        }
        else if(current.getType()==2) {
            transportViewHolder.txtDestination.setText(current.getTrain().getDestination());
            transportViewHolder.txtNumbon.setText(current.getTrain().getNumBon());
        }
       else if(current.getType()==3) {
            transportViewHolder.txtDestination.setText(current.getCtm().getDestination());
            transportViewHolder.txtNumbon.setText(current.getCtm().getNumBon());
        }
       else if(current.getType()==4) {
            transportViewHolder.txtDestination.setText(current.getVehicule().getDestination());
            transportViewHolder.txtMarque.setText(current.getVehicule().getMarque());
            transportViewHolder.txtDistance.setText(current.getVehicule().getDistance());
            transportViewHolder.txtNumbon.setVisibility(View.GONE);
            transportViewHolder.txtMarque.setVisibility(View.VISIBLE);
            transportViewHolder.txtDistance.setVisibility(View.VISIBLE);
        }
        //transportViewHolder.txtDate.setText(list.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TransportViewHolder extends RecyclerView.ViewHolder {

        public TextView txtNumbon,txtDestination,txtDistance,txtMarque;

        public TransportViewHolder(View itemView)
        {
            super(itemView);
            txtNumbon = itemView.findViewById(R.id.txtNumbon);
            txtDestination = itemView.findViewById(R.id.txtDestination);
            txtDistance = itemView.findViewById(R.id.txtDistance);
            txtMarque = itemView.findViewById(R.id.txtMarque);
            //txtDate = itemView.findViewById(R.id.txtDate);

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
