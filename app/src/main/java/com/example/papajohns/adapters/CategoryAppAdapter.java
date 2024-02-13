package com.example.papajohns.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.papajohns.R;
import com.example.papajohns.models.CategoryApp;

import java.util.List;

public class CategoryAppAdapter extends BaseAdapter {
    private Context context;
    private List<CategoryApp> data;
    private int teplatelayout;
    private LayoutInflater inflater;

    public CategoryAppAdapter(Context context, List<CategoryApp> data, int teplatelayout) {
        this.context = context;
        this.data = data;
        this.teplatelayout = teplatelayout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // activity= (Activity)context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View rowView = inflater.inflate(teplatelayout, viewGroup, false);
        TextView name = rowView.findViewById(R.id.name_text);
        ImageView imageCategory = rowView.findViewById(R.id.img_view);

        CategoryApp categoryApp = data.get(position);
        name.setText(categoryApp.getName());
        imageCategory.setImageResource(categoryApp.getImageCategory());
        return rowView;
    }
}
