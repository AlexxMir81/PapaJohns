package com.example.papajohns.dbRepository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.papajohns.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "myDb";
    private static final String TABLE_PRODUCTS = "Products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COST = "cost";
    private static final String COLUMN_COMPOSITION = "composition";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_CATEGORY = "categoryId";

    public boolean addProduct(Product product){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, product.getName());
            values.put(COLUMN_COST, product.getCost());
            values.put(COLUMN_COMPOSITION, product.getComposition());
            values.put(COLUMN_IMAGE, product.getImage());
            values.put(COLUMN_CATEGORY, product.getCategory());
            sqLiteDatabase.insert(TABLE_PRODUCTS, null, values);
            sqLiteDatabase.close();
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public int updateProduct(Product product){
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, product.getName());
            values.put(COLUMN_COST, product.getCost());
            values.put(COLUMN_COMPOSITION, product.getComposition());
            values.put(COLUMN_IMAGE, product.getImage());
            values.put(COLUMN_CATEGORY, product.getCategory());
            int result = sqLiteDatabase.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ? ",
                    new String[]{String.valueOf(product.getId())});
            sqLiteDatabase.close();
            return result;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }

    public void deleteProduct(Product product){
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{String.valueOf(product.getId())});
            sqLiteDatabase.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Product getById(int id){
        try{
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_PRODUCTS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_COST,
                            COLUMN_COMPOSITION, COLUMN_IMAGE, COLUMN_CATEGORY}, COLUMN_ID + " = ?", new String[]{String.valueOf(id)},
                    null, null, null, null);
            if (cursor!=null){
                cursor.moveToFirst();
            }

            Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                    cursor.getString(3), cursor.getInt(4), cursor.getInt(5));
            sqLiteDatabase.close();
            return product;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public List<Product> getByCategoryId(int categoryId){
        List<Product> list= new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_PRODUCTS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_COST,
                            COLUMN_COMPOSITION, COLUMN_IMAGE, COLUMN_CATEGORY}, COLUMN_CATEGORY + " = ?", new String[]{String.valueOf(categoryId)},
                    null, null, null, null);
            if (cursor!=null){
                cursor.moveToFirst();
                do {
                    Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                            cursor.getString(3), cursor.getInt(4), cursor.getInt(5));
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

    public List<Product> getAll(){
        List<Product> list= new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
            if (cursor!=null){
                cursor.moveToFirst();
                do {
                    Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                            cursor.getString(3), cursor.getInt(4), cursor.getInt(5));
                    list.add(product);
                }while (cursor.moveToNext());
            }
            sqLiteDatabase.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return list;
    }

    public ProductRepository(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_SCRIPT = "CREATE TABLE " +
                TABLE_PRODUCTS + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_NAME + " TEXT NOT NULL , " + COLUMN_COST + " INTEGER NOT NULL , " +
                COLUMN_COMPOSITION + " TEXT NOT NULL , " + COLUMN_IMAGE + " INTEGER NOT NULL , " 
                + COLUMN_CATEGORY + " INTEGER NOT NULL)";
        db.execSQL(CREATE_PRODUCTS_SCRIPT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }
}
