package com.example.papajohns;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    OrderRepository orderRepository;
    int totalSum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        orderRepository = new OrderRepository(this);
        TextView total = findViewById(R.id.total_amount);
        Button cardButton = findViewById(R.id.card_button);
        listView = findViewById(R.id.listview);
        list = new ArrayList<>();
        list = orderRepository.getAll();
        Log.i("ProductSQL", "before create Adapter in OrderActivity");
        orderAdapter = new OrderAdapter(this, list, R.layout.order_item);
        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pay(listView);
                list.clear();
            }
        });
        total.setText(getString(R.string.total_amount)+" "+totalSum);
        listView.setAdapter(orderAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        BackMainActivity();
        return true;
    }
    public void BackMainActivity() {
        Intent intent = new Intent();
        // intent.putExtra("count",count);
        setResult(RESULT_CANCELED,intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_cart);
        item.setIcon(R.drawable.cart_not);
        SubMenu subMenuFile = menu.addSubMenu("О Компании");
        subMenuFile.add(Menu.NONE,3,Menu.NONE,"Контакты");
        subMenuFile.add(Menu.NONE,4,Menu.NONE,"Справка");
        return true;
    }

    public void Pay(ListView listView){
        orderRepository.deleteAll();
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));;
    }
}