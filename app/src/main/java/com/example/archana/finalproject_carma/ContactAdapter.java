package com.example.archana.finalproject_carma;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Archana on 11/26/2015.
 */
public class ContactAdapter extends ArrayAdapter<Contact>{
    public ContactAdapter(Context context, int textViewResourceId, List<Contact> objects) {
        super(context, textViewResourceId, objects);
    }



    private class ViewHolder {
        TextView code;
        CheckBox name;
    }


}

