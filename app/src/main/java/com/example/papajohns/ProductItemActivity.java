package com.example.papajohns;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.papajohns.dbRepository.OrderRepository;
import com.example.papajohns.models.Order;

public class ProductItemActivity extends AppCompatActivity {
    private int currentValue = 1;
    private Spinner spinner;
    int sum;
    int costPerOne;
    int id;
    TextView name;
    TextView composition;
    TextView cost;
    ImageView image;
    EditText count;
    OrderRepository orderRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.i("ProductSQL", "create ProductItemActivity");
        orderRepository = new OrderRepository(this);
        name = findViewById(R.id.name_text);
        composition = findViewById(R.id.composition_text);
        cost = findViewById(R.id.cost_text);
        image = findViewById(R.id.img_view);
        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        composition.setText(intent.getStringExtra("composition"));

        costPerOne = intent.getIntExtra("cost", 0);
        sum = intent.getIntExtra("cost", 0);
        cost.setText(String.valueOf(sum));
        image.setImageResource(intent.getIntExtra("image", 0));
        image.setTag(intent.getIntExtra("image", 0));
        Log.i("ProductSQL", "after create ProductItemActivity");

        spinner = findViewById(R.id.type_select);
        String[] items = new String[]{
                getString(R.string.item1),
                getString(R.string.item2),
                getString(R.string.item3)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button plusButton = findViewById(R.id.plus_button);
        Button minusButton = findViewById(R.id.minus_button);
        count = findViewById(R.id.count_text);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentValue++;
                sum += costPerOne;
                count.setText(String.valueOf(currentValue));
                cost.setText(String.valueOf(sum));
                ;
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentValue > 0) {
                    currentValue--;
                    sum -= costPerOne;
                    count.setText(String.valueOf(currentValue));
                    cost.setText(String.valueOf(sum));
                    ;
                }
            }
        });

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProductItemActivity.this)
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                BackMainActivity(v);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        if (intent.getIntExtra("category", 0)==3) {
            LinearLayout layout1 = findViewById(R.id.count_layout);
            layout1.setVisibility(View.GONE);
            LinearLayout layout2 = findViewById(R.id.buttoms_layout);
            layout2.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            cost.setVisibility(View.GONE);
        }
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
    public boolean makeOrder(){
        int orderId = -1;
        try {
            if (orderRepository.getCurrentOrder()!=-1){
                orderId = orderRepository.getCurrentOrder();
                Log.i("ProductSQL", "getCurrentOrder "+ orderId );
            }else {
                orderId = orderRepository.getMaxOrder()+1;
                Log.i("ProductSQL", "getMaxOrder "+ orderId);
            }

            Order order = new Order(name.getText().toString(), Integer.parseInt(count.getText().toString()),
                    costPerOne, orderId, 0,spinner.getSelectedItemPosition(), (Integer) image.getTag());
            Log.i("ProductSQL", "Order "+ order.toString()+" imageID "+R.drawable.pizza1+" "+image.getTag());
            boolean test =  orderRepository.addOrder(order);
            Log.i("ProductSQL", "after create Order "+ orderRepository.getAll().size());

            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    public void BackMainActivity(View view) {
        makeOrder();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }
}