package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.tools.ObjectChampionship;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//Creo l'adapter per i campionati
public class AdapterChampionship extends ArrayAdapter<ObjectChampionship> {

    private ArrayList<ObjectChampionship> championship;
    private Activity context;

    //Costruttore
    public AdapterChampionship(Activity _context, ArrayList<ObjectChampionship> _championship) {
        super(_context, R.layout.item_championship, _championship);
        this.context = _context;
        this.championship = _championship;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //setto il layout
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

       //Recupero l'informazione dalla lista
        String championship_name = championship.get(position).getNAME();
        //Setto il nome del campionato nell'Item campionato
        viewHolder.tv_name_championship_lv_item.setText(championship_name);

        //Imposto l'immagine
       if(championship_name.equals("Leon Supercopa")){
            viewHolder.iw_logo_championship_lw_item.setImageResource(R.drawable.logochamp0);
        }
       else{
           viewHolder.iw_logo_championship_lw_item.setImageResource(R.drawable.logochamp1);
        }

        return r;
    }

    class ViewHolder {
        //Setto i componenti dell'item
        TextView tv_name_championship_lv_item;
        ImageView iw_logo_championship_lw_item;

        ViewHolder(View v) {
            tv_name_championship_lv_item = v.findViewById(R.id.tv_name_championship_lv_item);
            iw_logo_championship_lw_item = v.findViewById(R.id.iw_logo_championship_lw_item);


        }
    }



}



