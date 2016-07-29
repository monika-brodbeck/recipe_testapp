package com.mobaer.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobaer.recipe.model.Recipe;

/**
 * Created by momo on 25.07.16.
 */
public class MyTestArrayAdapter extends ArrayAdapter<Recipe>{
    private final Context context;
    private final Recipe[] values;

    public MyTestArrayAdapter(Context context, Recipe[] values){
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.recipe_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.recipe_list_title);
        textView.setText(values[position].getTitle());
        return rowView;
    }
}

