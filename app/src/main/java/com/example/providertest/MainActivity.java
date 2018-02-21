package com.example.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mbtnadd, mbtnquery, mbtnupdate, mbtndelete;
    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbtnadd = findViewById(R.id.add_data);
        mbtnquery = findViewById(R.id.query_data);
        mbtnupdate = findViewById(R.id.updata_data);
        mbtndelete = findViewById(R.id.delete_data);

        mbtnadd.setOnClickListener(this);
        mbtnquery.setOnClickListener(this);
        mbtnupdate.setOnClickListener(this);
        mbtndelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*
            通过Uri的parse()方法  将字符串形式的uri内容解析成Uri对象
            通过传入该Uri对象知道我们要去操作哪一个表的数据
            通过给ContentValues实例赋值，并调用getContentResover中的insert（）方法操作数据库，insert方法会返回一个Uri对象
            通过对该对象调用方法getPathSegments方法，该方法第0位是路径，第一位是表的id

             */
            case R.id.add_data:
                Uri uri = Uri.parse("content://com.example.databasetest.provider/book");
                ContentValues values = new ContentValues();
                values.put("name", "A Clash of Kings");
                values.put("author", "George martin");
                values.put("pages", 1040);
                values.put("price", 22.85);
                Uri newUri = getContentResolver().insert(uri, values);
                assert newUri != null;
                newId = newUri.getPathSegments().get(1);

                break;
            /*
            通过Uri的parse()方法  将字符串形式的uri内容解析成Uri对象
            调用getContentResover 中的query()方法，query方法会返回一个Cursor对象，该对象带有查询的数据
            可以通过该对象的get方法获取内部内容，操作完记得关闭cursor对象
             */
            case R.id.query_data:
                Uri uri1 = Uri.parse("content://com.example.databasetest.provider/book");
                Cursor cursor = getContentResolver().query(uri1, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "book name is " + name);
                        Log.d("MainActivity", "book author is " + author);
                        Log.d("MainActivity", "book pages is " + pages);
                        Log.d("MainActivity", "book price is " + price);
                    }
                    cursor.close();
                }

                break;
                /*
                通过Uri的parse()方法  将字符串形式的uri内容解析成Uri对象，后面加上ID号是查询表中那一列数据
                通过给ContentValues实例赋值，并调用getContentResover中的update（）方法操作数据库，update方法会返回一个int型参数
                 */
            case R.id.updata_data:
                Uri uri2 = Uri.parse("content://com.example.databasetest.provider/book/" + newId);
                ContentValues values1 = new ContentValues();
                values1.put("name", "A Storm of Swords");
                values1.put("pages", 1246);
                values1.put("price", 24.05);
                getContentResolver().update(uri2, values1, null, null);

                break;
            /*
            通过Uri的parse()方法  将字符串形式的uri内容解析成Uri对象，后面加上ID号是查询表中那一列数据
            通过给ContentValues实例赋值，并调用getContentResover中的delete（）方法操作数据库，delete方法会返回一个int型参数
             */
            case R.id.delete_data:
                Uri uri3 = Uri.parse("content://com.example.databasetest.provider/book/" + newId);
                getContentResolver().delete(uri3, null, null);
                break;
        }
    }
}
