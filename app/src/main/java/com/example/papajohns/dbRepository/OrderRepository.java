package com.example.papajohns.dbRepository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.papajohns.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myDb1";
    private static final String TABLE_ORDERS= "Orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COUNT = "count";
    private static final String COLUMN_COST = "cost";
    private static final String COLUMN_ORDER_ID = "orderId";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_TYPE_CHOICE = "typeChoice";

    public boolean addOrder(Order order){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, order.getName());
            values.put(COLUMN_COUNT, order.getCount());
            values.put(COLUMN_COST, order.getCost());
            values.put(COLUMN_ORDER_ID, order.getOrderId());
            values.put(COLUMN_STATUS, order.getStatus());
            values.put(COLUMN_TYPE_CHOICE, order.getTypeChoice());
            sqLiteDatabase.insert(TABLE_ORDERS, null, values);
            sqLiteDatabase.close();
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public int updateOrder(Order order){
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, order.getName());
            values.put(COLUMN_COUNT, order.getCount());
            values.put(COLUMN_COST, order.getCost());
            values.put(COLUMN_ORDER_ID, order.getOrderId());
            values.put(COLUMN_STATUS, order.getStatus());
            values.put(COLUMN_TYPE_CHOICE, order.getTypeChoice());
            int result = sqLiteDatabase.update(TABLE_ORDERS, values, COLUMN_ID + " = ? ",
                    new String[]{String.valueOf(order.getId())});
            sqLiteDatabase.close();
            return result;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }

    public void deleteOrder(Order order){
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.delete(TABLE_ORDERS, COLUMN_ID + " = ?", new String[]{String.valueOf(order.getId())});
            sqLiteDatabase.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Order getById(int id){
        try{
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_ORDERS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_COUNT,
                            COLUMN_COST, COLUMN_ORDER_ID, COLUMN_STATUS, COLUMN_TYPE_CHOICE}, COLUMN_ID + " = ?", new String[]{String.valueOf(id)},
                    null, null, null, null);
            if (cursor!=null){
                cursor.moveToFirst();
            }

            Order product = new Order(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                    cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6));
            sqLiteDatabase.close();
            return product;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public List<Order> getByOrderId(int OrderId){
        List<Order> list= new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_ORDERS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_COUNT,
                            COLUMN_COST, COLUMN_ORDER_ID, COLUMN_STATUS, COLUMN_TYPE_CHOICE}, COLUMN_ORDER_ID + " = ?", new String[]{String.valueOf(OrderId)},
                    null, null, null, null);
            if (cursor!=null){
                cursor.moveToFirst();
                do {
                    Order product = new Order(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                            cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6));
                    list.add(product);
                }while (cursor.moveToNext());
            }
            sqLiteDatabase.close();
            return list;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public List<Order> getAll(){
        List<Order> list= new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
            if (cursor!=null){
                cursor.moveToFirst();
                do {
                    Order order = new Order(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                            cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6));
                    list.add(order);
                }while (cursor.moveToNext());
            }
            sqLiteDatabase.close();
        }catch (Exception ex){
            ex.printStackTrace();
            Log.i("ProductSQL", "cursor null" + ex.getMessage());
        }
        return list;
    }
    public int getMaxOrder(){
        int result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT MAX(" + COLUMN_ORDER_ID + ") FROM " + TABLE_ORDERS, null);
            if (cursor.moveToFirst()) {
                result = cursor.getInt(0);
            }
            sqLiteDatabase.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    public int getCurrentOrder(){
        int result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_ORDERS,new String[]{COLUMN_ORDER_ID}, COLUMN_STATUS + " = ?", new String[]{String.valueOf(0)},
                    null, null, null, null);
            if (cursor!=null) {
                cursor.moveToFirst();
                result=cursor.getInt(0);
            }
            sqLiteDatabase.close();;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    public OrderRepository(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_SCRIPT = "CREATE TABLE " +
                TABLE_ORDERS + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_NAME + " TEXT NOT NULL , " + COLUMN_COUNT + " INTEGER NOT NULL , " +
                COLUMN_COST + " INTEGER NOT NULL , " + COLUMN_ORDER_ID + " INTEGER NOT NULL , "
                + COLUMN_STATUS + " INTEGER NOT NULL , " + COLUMN_TYPE_CHOICE + " INTEGER NOT NULL)";
        db.execSQL(CREATE_ORDERS_SCRIPT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }
}
