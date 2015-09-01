package com.example.benjamintauber.lakewoodmap2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by benjamintauber on 9/7/14.
 */
public class ListAdapter extends ArrayAdapter<Person> {
    private int resource;
    public ListAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View row = inflater.inflate(resource, null, false);

        TextView lineOne = (TextView)row.findViewById(R.id.lineOne);
        TextView lineTwo = (TextView)row.findViewById(R.id.lineTwo);
        TextView lineThree = (TextView)row.findViewById(R.id.lineThree);

        Person person = getItem(position);
        lineOne.setText(person.getFirstLine());
        lineTwo.setText(person.getAddress());
        lineThree.setText(person.getPhone());
        return row;
    }
}
