package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.tools.Championship;
import com.example.myapplication.tools.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class ChampionshipAdapter extends ArrayAdapter<Championship> {

    private ArrayList<Championship> championship;
    private Activity context;


    public ChampionshipAdapter(Activity _context, ArrayList<Championship> _championship) {
        super(_context, R.layout.championship_item, _championship);
        this.context = _context;
        this.championship = _championship;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ChampionshipAdapter.ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.championship_item, null);
            viewHolder = new  ChampionshipAdapter.ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = ( ChampionshipAdapter.ViewHolder) r.getTag();
        }

        String championship_name = championship.get(position).getNAME();
        String championship_logo = championship.get(position).getLOGO();


        viewHolder.tv_name_championship_lv_item.setText(championship_name);
        //viewHolder.tv_name_championship_lv_item.setText(championship_logo);


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



