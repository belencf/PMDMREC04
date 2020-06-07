package com.belen.proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    private Context context;
    private ArrayList<Provincia> listitems;
    public Adaptador(Context context,ArrayList<Provincia> listitems){
        this.context=context;
        this.listitems=listitems;
    }
    @Override
    public int getCount() {
        return listitems.size();
    }

    @Override
    public Object getItem(int position) {
        return listitems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Provincia item=(Provincia) getItem(position);
        convertView= LayoutInflater.from(context).inflate(R.layout.item,null);
        TextView itprovincia=(TextView) convertView.findViewById(R.id.itprovincia);
        TextView itfase=(TextView) convertView.findViewById(R.id.itfase);
        Button itMore=(Button) convertView.findViewById(R.id.itMore);
        itprovincia.setText(item.getNombre());
        itfase.setText(item.getFase());
        itMore.setText("Ver más");
        itMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImgAerea.class);
                intent.putExtra("var",item.getNombre());
                context.startActivity(intent);
            }
        });



        return convertView;
    }
}
