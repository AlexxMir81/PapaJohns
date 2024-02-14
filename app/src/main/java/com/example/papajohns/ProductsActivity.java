package com.example.papajohns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.papajohns.adapters.ProductCategoryAdapter;
import com.example.papajohns.dbRepository.ProductRepository;
import com.example.papajohns.models.CategoryApp;
import com.example.papajohns.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    ListView listView;
    List<Product> list;
    ProductCategoryAdapter productCategoryAdapter;
    ProductRepository productRepository;
    int categoryId;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Log.i("ProductSQL", "before createRepository");
        productRepository = new ProductRepository(this);
        if(productRepository.getAll()!=null && productRepository.getAll().size()<=1) {
            productRepository.addProduct(new Product("Пицца грибная", 300, "Грибы, курица, сыр", R.drawable.pizza1,1));
            productRepository.addProduct(new Product("Пицца деликатесная", 400, "курица, сыр, помидоры", R.drawable.pizza2, 1));
            productRepository.addProduct(new Product("Пицца Пеперони", 350, "колабаса, сыр, помидоры", R.drawable.pizza3, 1));
            productRepository.addProduct(new Product("Суши терияки", 300, "Рис, нури, огурец, авокадо", R.drawable.sushi1, 2));
            productRepository.addProduct(new Product("Суши горячие", 250, "Рис, нури, угорь", R.drawable.sushi2, 2));
            productRepository.addProduct(new Product("Суши филадельфия", 270, "Рис, нури, лосось", R.drawable.sushi2, 2));
            productRepository.addProduct(new Product("Ресторан восточный", 0, "Новый ресторан восточной кухни", R.drawable.rest1, 3));
            productRepository.addProduct(new Product("Ресторан итальянский", 0, "Новый ресторан итальянской кухни", R.drawable.rest2, 3));
        }
        listView = findViewById(R.id.listview);
        list = new ArrayList<>();
        Intent intent = getIntent();
        categoryId = intent.getIntExtra("id", 0);
        position = intent.getIntExtra("position", 0);
        list = productRepository.getByCategoryId(categoryId);
        Log.i("ProductSQL", "after getByCategoryId() " + list.size());
        productCategoryAdapter = new ProductCategoryAdapter(this, list, R.layout.category_item);
        listView.setAdapter(productCategoryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = new Product();
                product = (Product) listView.getItemAtPosition(position);
                Log.i("ProductSQL", "befor ProductItemActivity");
                ProductItemActivity(view, position, product);
            }
        });
    }
    public void BackMainActivity(View view) {
        Intent intent = new Intent();
        // intent.putExtra("count",count);
        setResult(RESULT_CANCELED,intent);
        finish();
    }
    public void ProductItemActivity(View view, int position, Product product) {
        Intent intent = new Intent(this, ProductItemActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("id", product.getId());
        intent.putExtra("name", product.getName());
        intent.putExtra("cost", product.getCost());
        intent.putExtra("composition", product.getComposition());
        intent.putExtra("image", product.getImage());
        intent.putExtra("category", product.getCategory());
        startActivityForResult(intent,2);
    }
}