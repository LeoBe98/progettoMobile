package com.example.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.tools.Championship;
import com.example.myapplication.tools.ProfileImage;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdapterChampionship extends ArrayAdapter<Championship> {

    private ArrayList<Championship> championship;
    private Activity context;


    public AdapterChampionship(Activity _context, ArrayList<Championship> _championship) {
        super(_context, R.layout.item_championship, _championship);
        this.context = _context;
        this.championship = _championship;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        AdapterChampionship.ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.item_championship, null);
            viewHolder = new  AdapterChampionship.ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = ( AdapterChampionship.ViewHolder) r.getTag();
        }

        String championship_name = championship.get(position).getNAME();
        String championship_logo = championship.get(position).getLOGO();


        viewHolder.tv_name_championship_lv_item.setText(championship_name);

       if(championship_name.equals("Leon Supercopa")){
            viewHolder.iw_logo_championship_lw_item.setImageResource(R.drawable.logochamp0);
        }
       else{
           viewHolder.iw_logo_championship_lw_item.setImageResource(R.drawable.logochamp1);
        }
        return r;
    }

    class ViewHolder {
        TextView tv_name_championship_lv_item;
        ImageView iw_logo_championship_lw_item;

        ViewHolder(View v) {
            tv_name_championship_lv_item = v.findViewById(R.id.tv_name_championship_lv_item);
            iw_logo_championship_lw_item = v.findViewById(R.id.iw_logo_championship_lw_item);


        }
    }



}



