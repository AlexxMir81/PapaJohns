package com.example.papajohns;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.papajohns.adapters.OrderAdapter;
import com.example.papajohns.adapters.ProductCategoryAdapter;
import com.example.papajohns.dbRepository.OrderRepository;
import com.example.papajohns.models.Order;
import com.example.papajohns.models.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    ListView listView;
    List<Order> list;
    OrderAdapter orderAdapter;
    int totalSum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Log.i("ProductSQL", "onCreate OrderActivity");
        OrderRepository orderRepository = new OrderRepository(this);

        TextView total = findViewById(R.id.total_amount);
        listView = findViewById(R.id.listview);
        list = new ArrayList<>();
        list = orderRepository.getAll();
        Log.i("ProductSQL", "before create Adapter in OrderActivity");
        orderAdapter = new OrderAdapter(this, list, R.layout.order_item);
        for (Order order: list){
            totalSum+=(order.getCost()*order.getCount());
        }
        total.setText(getString(R.string.total_amount)+": "+totalSum);
        listView.setAdapter(orderAdapter);

    }
}