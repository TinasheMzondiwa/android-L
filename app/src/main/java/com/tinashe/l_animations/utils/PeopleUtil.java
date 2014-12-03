package com.tinashe.l_animations.utils;

import android.content.Context;

import com.tinashe.l_animations.R;
import com.tinashe.l_animations.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tinashe on 2014/12/03.
 */
public class PeopleUtil {

    public static List<Person> generatePeople(Context context){
        List<Person> persons = new ArrayList<>();

        int[] colors = {R.color.theme_accent, R.color.red, R.color.yellow, R.color.teal, R.color.brown};
        String[] names = context.getResources().getStringArray(R.array.dummy_names);

        Random rand = new Random();

        for(String name : names){
            Person person = new Person();
            person.setName(name);

            int i = rand.nextInt((colors.length - 1) + 1);
            person.setColor(colors[i]);

            persons.add(person);
        }

        return persons;
    }


}
