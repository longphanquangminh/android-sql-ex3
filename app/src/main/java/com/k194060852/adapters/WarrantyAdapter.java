package com.k194060852.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.k194060852.models.Warranty;
import com.k194060852.sqlite_ex3.MainActivity;
import com.k194060852.sqlite_ex3.R;

import java.util.List;

public class WarrantyAdapter extends BaseAdapter {
    Activity activity;
    int item_layout;
    List<Warranty> warranties;

    public WarrantyAdapter(MainActivity activity, int item_layout, List<Warranty> warranties) {
        this.activity = activity;
        this.item_layout = item_layout;
        this.warranties = warranties;
    }

    @Override
    public int getCount() {
        return warranties.size();
    }

    @Override
    public Object getItem(int i) {
        return warranties.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_layout, null);

            holder.txtName = view.findViewById(R.id.txtName);
            holder.txtDes = view.findViewById(R.id.txtDes);
            holder.imvPhoto = view.findViewById(R.id.imvPhoto);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //Binding data
        Warranty w = warranties.get(i);
        holder.txtName.setText(w.getName());
        holder.txtDes.setText(w.getDes());
        byte[] dat = w.getPhoto();

        return view;
    }

    public static class ViewHolder {
        TextView txtName, txtDes;
        ImageView imvPhoto;
    }


}
