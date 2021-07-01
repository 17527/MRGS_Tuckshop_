package com.example.mrgstuckshopapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mrgstuckshopapp.Models.CartModel;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    final static String DBNAME = "mydatabase.db";
    final static int DBVERSION = 1;


    public DbHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table orders" +
                        "(id integer primary key autoincrement," +
                        "name text," +
                        "phone text," +
                        "price int," +
                        "image int," +
                        "quantity int," +
                        "foodname text," +
                        "description text)"

        );

    }
    //as the version is getting changed, old version will be 'dropped' and updated by a newly created one
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP table if exists orders");
        onCreate(sqLiteDatabase);

    }

    public boolean insertOrder(String name, String phone, int price, int image, String foodname, String desc, int quantity){
        SQLiteDatabase database = getReadableDatabase();
        ContentValues values = new ContentValues();

        /*
        * Indexes
        * id = 0
        * name =1
        * phone = 2
        * price = 3
        * image = 4
        * quantity = 5
        * foodname = 6
        * desc = 7
        *
        * */
        values.put("name", name);
        values.put("phone", phone);
        values.put("price", price);
        values.put("image", image);
        values.put("quantity", quantity);
        values.put("foodname", foodname);
        values.put("description", desc);

        //prevents the id created to be less than 0
        long id = database.insert("orders", null, values);
        if(id <= 0) {
            return false;
        }else{
            return true;
        }

    }

    //modelling the ordered data from the database
    public ArrayList<CartModel> getOrders() {
        ArrayList<CartModel> orders = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        //cursor is used to get rows from data base so it can be inserted into the cart
        Cursor cursor = database.rawQuery("Select id, foodname,image,price from orders", null );
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                CartModel model = new CartModel();
                model.setOrderNumber(cursor.getInt(0)+"");
                model.setSoldItemName(cursor.getString(1));
                model.setOrderImage(cursor.getInt(2));
                model.setPrice(cursor.getInt(3)+"");
                orders.add(model);
            }
        }
        cursor.close();
        database.close();
        return orders;
    }

    //getting the information for detail activity for updating
    public Cursor getOrderById(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        //cursor is used to get rows from data base so it can be inserted
        Cursor cursor = database.rawQuery("Select * from orders where id =" + id, null );

        if(cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    public boolean updateOrder(String name, String phone, int price, int image, String foodname, String desc,
                               int quantity, int id){
        SQLiteDatabase database = getReadableDatabase();
        ContentValues values = new ContentValues();

        /*
         * Indexes
         * id = 0
         * name =1
         * phone = 2
         * price = 3
         * image = 4
         * quantity = 5
         * foodname = 6
         * desc = 7
         *
         * */
        values.put("name", name);
        values.put("phone", phone);
        values.put("price", price);
        values.put("image", image);
        values.put("quantity", quantity);
        values.put("foodname", foodname);
        values.put("description", desc);

        //prevents the id created to be less than 0
        long row = database.update("orders", values, "id="+id, null);
        if(row <= 0) {
            return false;
        }else{
            return true;
        }

    }

    public int deletedOrder(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete("orders", "id=" +id, null);
    }
}
