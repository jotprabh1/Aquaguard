package com.jagatintl.aquaguard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by Prabhjot Singh on 17-10-2016.
 */
public class CustomAdapter extends BaseAdapter{

    LayoutInflater inflater=null;
    Context context=null;
    public CustomAdapter(ProductsActivity productsActivity)
    {
        context=productsActivity;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return ProductsActivity.searchProductImages.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.grid_design,null);
        ImageView img=(ImageView)convertView.findViewById(R.id.imageViewProduct);
        TextView desc=(TextView)convertView.findViewById(R.id.textDescription);
        TextView price=(TextView)convertView.findViewById(R.id.textPrice);

        img.setImageBitmap(ProductsActivity.searchProductImages.get(position));
        desc.setText(ProductsActivity.searchProductList.get(position).get(SplashScreen.TAG_NAME));
        price.setText(ProductsActivity.searchProductList.get(position).get(SplashScreen.TAG_ADDRESS));

        return convertView;
    }
}
