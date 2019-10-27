package com.exemple.www.gmission;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import models.Employe;

public class EmployeSpinnerAdapter  extends BaseAdapter implements SpinnerAdapter {

    ArrayList<Employe> list;
    Context context;
    String[] colors = {"#13edea","#e20ecd","#15ea0d"};
    String[] colorsback = {"#FF000000","#FFF5F1EC","#ea950d"};

    public EmployeSpinnerAdapter(Context context, ArrayList<Employe> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    public String getItemCin(int position) {
        return list.get(position).getCin();
    }
    @Override
    public long getItemId(int position) {
        return Long.parseLong(position+"");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  View.inflate(context, R.layout.spinneremp_main, null);
        TextView textView = (TextView) view.findViewById(R.id.main);
        textView.setText(list.get(position).getNom()+" "+list.get(position).getPrenom());
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view;
        view =  View.inflate(context, R.layout.spinner_employe, null);
        final TextView textView = (TextView) view.findViewById(R.id.dropdown);
        textView.setText(list.get(position).getNom()+" "+list.get(position).getPrenom());
        return view;
    }
}