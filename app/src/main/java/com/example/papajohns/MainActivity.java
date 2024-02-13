package com.example.papajohns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ListView;

import com.example.papajohns.adapters.CategoryAppAdapter;
import com.example.papajohns.models.CategoryApp;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<CategoryApp> list;
    CategoryAppAdapter categoryAppAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        list = new ArrayList<>();
        list.add(new CategoryApp(1, "Pizza", R.drawable.pizza1));
        list.add(new CategoryApp(2, "Sushi", R.drawable.sushi1));
        list.add(new CategoryApp(3, "Restaurant", R.drawable.rest1));
        categoryAppAdapter = new CategoryAppAdapter(this, list, R.layout.category_item);
        listView.setAdapter(categoryAppAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SubMenu subMenuFile = menu.addSubMenu("О Компании");
        subMenuFile.add(Menu.NONE,3,Menu.NONE,"Контакты");
        subMenuFile.add(Menu.NONE,4,Menu.NONE,"Справка");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cart) {
            // Действие при нажатии на кнопку
            return true;
        }
//        if (id == R.id.action_edit) {
//            // Действие при нажатии на кнопку
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}