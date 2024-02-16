package com.example.papajohns.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.papajohns.R;
import com.example.papajohns.dbRepository.OrderRepository;
import com.example.papajohns.models.Order;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<Order> data;
    private int teplatelayout;
    private LayoutInflater inflater;
    private int currentCount = 0;
    int totalSum;
    private Activity activity;
    public OrderAdapter(Context context, List<Order> data, int teplatelayout) {
        this.context = context;
        this.data = data;
        this.teplatelayout = teplatelayout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        activity= (Activity)context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        //View rowView = inflater.inflate(teplatelayout, parent, false);
        ViewHolder holder;
        if (convertView == null) {
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(teplatelayout, parent, false);
            holder = new ViewHolder();
            holder.recycleButton = convertView.findViewById(R.id.recycle_button);
            holder.plusButton = convertView.findViewById(R.id.plus_button);
            holder.minusButton = convertView.findViewById(R.id.minus_button);
            holder.spinner = convertView.findViewById(R.id.type_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String[] items = new String[] {
                activity.getString(R.string.item1),
                activity.getString(R.string.item2),
                activity.getString(R.string.item3)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter);
        TextView name = convertView.findViewById(R.id.name_text);
        EditText count = convertView.findViewById(R.id.count_text);
        TextView sum = convertView.findViewById(R.id.sum_text);
        ImageView image = convertView.findViewById(R.id.img_view);
        TextView totalAmount = (TextView) activity.findViewById(R.id.total_amount);
        totalSum = 0;
        for (Order order: data){
            totalSum+=(order.getCost()*order.getCount());
        }
        totalAmount.setText(activity.getString(R.string.total_amount)+" "+ totalSum);

        Order order = data.get(position);

        name.setText(order.getName());
        holder.spinner.setSelection(order.getTypeChoice());

        count.setText(String.valueOf(order.getCount()));
        sum.setText(String.valueOf(order.getCount()*order.getCost()));

        image.setImageResource(order.getImage());
        OrderRepository orderRepository = new OrderRepository(context);
        convertView.setTag(holder);
        holder.recycleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderRepository.deleteOrder(data.get(position));
                data.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCount = Integer.parseInt(count.getText().toString());
                currentCount++;
                sum.setText(String.valueOf(order.getCost()*currentCount));
                count.setText(String.valueOf(currentCount));
                order.setCount(Integer.parseInt(count.getText().toString()));

                updateOrder(order);
            }
        });
        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCount = Integer.parseInt(count.getText().toString());
                if (currentCount > 0) {
                    currentCount--;
                    sum.setText(String.valueOf(order.getCost()*currentCount));
                    count.setText(String.valueOf(currentCount));
                    order.setCount(Integer.parseInt(count.getText().toString()));
                    totalAmount.setText("asfgsdfgsdfgsdfg");
                    updateOrder(order);
                }
            }
        });

        holder.recycleButton.setTag(position);
        holder.plusButton.setTag(position);
        holder.minusButton.setTag(position);
        return convertView;
    }

    public void updateOrder(Order order){
        OrderRepository orderRepository = new OrderRepository(context);
        orderRepository.updateOrder(order);
    }
    private static class ViewHolder {
        Button recycleButton;
        Button plusButton;
        Button minusButton;
        Spinner spinner;
    }
}
