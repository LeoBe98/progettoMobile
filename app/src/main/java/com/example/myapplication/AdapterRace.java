package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.tools.Race;

import java.util.ArrayList;

public class AdapterRace extends ArrayAdapter<Race> {

    private ArrayList<Race> race;
    private Activity context;


    public AdapterRace(Activity _context, ArrayList<Race> _race) {
        super(_context, R.layout.item_race, _race);
        this.context = _context;
        this.race = _race;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        AdapterRace.ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.item_race, null);
            viewHolder = new AdapterRace.ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (AdapterRace.ViewHolder) r.getTag();
        }

        String race_name = race.get(position).getCIRCUIT();
        String race_date = race.get(position).getDATA();


        viewHolder.tv_name_circuit_lv_item.setText(race_name);
        viewHolder.tv_date_circuit_lv_item.setText(race_date);


        return r;
    }

    class ViewHolder {
        TextView tv_name_circuit_lv_item,tv_date_circuit_lv_item;

        ViewHolder(View v) {
            tv_name_circuit_lv_item = v.findViewById(R.id.tv_name_circuit_lv_item);
            tv_date_circuit_lv_item = v.findViewById(R.id.tv_date_circuit_lv_item);


        }
    }


}