package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.tools.ObjectTeam;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//Creo l'adapter per il team
public class AdapterTeam extends ArrayAdapter<ObjectTeam> {
    private ArrayList<ObjectTeam> teams;
    private Activity context;

    //Costruttore
    public AdapterTeam(Activity _context, ArrayList<ObjectTeam> _teams) {
        super(_context, R.layout.item_team, _teams);
        this.context = _context;
        this.teams = _teams;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //setto il layout
        View r = convertView;
        AdapterTeam.ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.item_team, null);
            viewHolder = new AdapterTeam.ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (AdapterTeam.ViewHolder) r.getTag();
        }

        //Recupero le informazioni dalla lista
        String team_name = teams.get(position).getNAME();
        Integer team_points = teams.get(position).getPOINTS();
        String team_car = teams.get(position).getCAR();
        //Setto le informazioni nell'item
        viewHolder.tv_name.setText(team_name);
        viewHolder.tv_points.setText(String.valueOf(team_points));
        viewHolder.tv_car.setText(team_car);


        return r;
    }

    class ViewHolder {
        //Setto i componenti dell'item
        TextView tv_name, tv_points, tv_car;

        ViewHolder(View v) {
            tv_name = v.findViewById(R.id.tv_name_team_lv_item);
            tv_points = v.findViewById(R.id.tv_points_team_lv_item);
            tv_car = v.findViewById(R.id.tv_car_team_lv_item);

        }
    }


}



