package com.tinashe.l_animations.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.tinashe.l_animations.R;
import com.tinashe.l_animations.adapters.NamesAdapter;
import com.tinashe.l_animations.model.Person;
import com.tinashe.l_animations.utils.PeopleUtil;

import java.util.List;

/**
 * Created by tinashe on 2014/12/03.
 */
public class PeopleFragment extends Fragment {

    private static final String TAG = PeopleFragment.class.getName();


    private AlertDialog mDialog;

    private List<Person> persons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View rootView = getView();

        if(rootView != null){
            ListView listView = (ListView) rootView.findViewById(R.id.list);

            persons = PeopleUtil.generatePeople(getActivity());
            NamesAdapter adapter = new NamesAdapter(getActivity(), R.layout.name_item, persons);

            listView.setAdapter(adapter);


            final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.attachToListView(listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Person person = persons.get(position);
                    showDialog(person);
                }
            });
        }
    }




    private void showDialog(Person person){
        final View dialogView = View.inflate(getActivity(), R.layout.dialog_view, null);
        dialogView.findViewById(R.id.reveal_view).setBackgroundColor(getResources().getColor(person.getColor()));

        String[] name = person.getName().split(" ");
        EditText firstName = (EditText) dialogView.findViewById(R.id.first_name);
        firstName.setText(name[0]);
        EditText lastName = (EditText) dialogView.findViewById(R.id.last_name);
        lastName.setText(name[1]);


        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        builder.setView(dialogView)
                .setCancelable(false);


        mDialog = builder.create();
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                revealShow(dialogView);

            }
        });
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
        dialogView.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });


        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.show();



    }

    private void revealShow(View rootView){
        final View view = rootView.findViewById(R.id.reveal_view);
        int w = view.getWidth();
        int h = view.getHeight();
        float maxRadius = (float) Math.sqrt(w * w / 4 + h * h / 4);


        Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view,
                w / 2, h / 2, 0, maxRadius);

        view.setVisibility(View.VISIBLE);
        revealAnimator.start();



    }

    private void closeDialog(){

        final View view = mDialog.findViewById(R.id.reveal_view);
        if(view != null){
            int w = view.getWidth();
            int h = view.getHeight();
            float maxRadius = (float) Math.sqrt(w * w / 4 + h * h / 4);

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, w / 2, h / 2, maxRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mDialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });

            anim.start();
        }

    }
}
