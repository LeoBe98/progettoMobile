package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.tools.ObjectPilot;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//Creo l'adapter per i piloti
public class AdapterPilot extends ArrayAdapter<ObjectPilot> {
    private ArrayList<ObjectPilot> pilots;
    private Activity context;

    //Costruttore
    public AdapterPilot(Activity _context, ArrayList<ObjectPilot> _pilots) {
        super(_context, R.layout.item_pilot, _pilots);
        this.context = _context;
        this.pilots = _pilots;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //setto il layout
        View r = convertView;
        AdapterPilot.ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.item_pilot, null);
            viewHolder = new AdapterPilot.ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (AdapterPilot.ViewHolder) r.getTag();
        }

        //Recupero le informazioni dalla lista
        String pilot_name = pilots.get(position).getNAME();
        String pilot_team = pilots.get(position).getTEAM();
        Integer pilot_points = pilots.get(position).getPOINTS();
        String pilot_car = pilots.get(position).getCAR();

        //Setto le informazioni
        viewHolder.tv_name.setText(pilot_name);
        viewHolder.tv_team.setText(pilot_team);
        viewHolder.tv_points.setText(String.valueOf(pilot_points));
        viewHolder.tv_car.setText(pilot_car);

        return r;
    }

    class ViewHolder {
        //Setto i componenti dell'item
        TextView tv_name, tv_team, tv_points, tv_car;

        ViewHolder(View v) {
            tv_name = v.findViewById(R.id.tv_name_pilot_lv_item);
            tv_team = v.findViewById(R.id.tv_team_pilot_lv_item);
            tv_points = v.findViewById(R.id.tv_points_pilot_lv_item);
            tv_car = v.findViewById(R.id.tv_car_pilot_lv_item);

        }
    }


}



