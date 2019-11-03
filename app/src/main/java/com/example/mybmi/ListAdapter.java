package com.example.mybmi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter<RecordBean> {
    private int resource;
    public ListAdapter(@NonNull Context context, int resource, @NonNull List<RecordBean> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RecordBean bean = getItem(position);
        String result = String.valueOf(bean.getResult());
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        TextView bmi_content = view.findViewById(R.id.bmi_content);
        TextView bmi_time= view.findViewById(R.id.bmi_time);
        bmi_content.setText(result);
        bmi_time.setText(bean.getTime());
        return view;

    }
}
