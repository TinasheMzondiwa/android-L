package com.tinashe.l_animations.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.tinashe.l_animations.R;
import com.tinashe.l_animations.model.Person;

import java.util.List;

/**
 * Created by tinashe on 2014/12/03.
 */
public class NamesAdapter extends ArrayAdapter<Person> {


    public NamesAdapter(Context context, int resource, List<Person> objects) {
        super(context, resource, objects);
    }

    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.name_item, null);

            holder.textView = (TextView) convertView.findViewById(R.id.name);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Person person = getItem(position);

        holder.textView.setText(person.getName());
        setImage(holder.imageView, person);

        return convertView;
    }

    private void setImage(ImageView img, Person person){

        String symbol = person.getName().charAt(0) + "";

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(symbol.toUpperCase(), getContext().getResources().getColor(person.getColor()));

        img.setImageDrawable(drawable);

    }
}
