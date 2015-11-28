package com.android9033.scavenger.scavenger.Control;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android9033.scavenger.scavenger.Model.Quest;
import com.android9033.scavenger.scavenger.R;

import java.util.ArrayList;

/**
 * Created by yirongshao on 11/21/15.
 */
public class YouFragment extends Fragment {

    private ListView lv1;
    private ArrayAdapter<String> adapter1;
    private ArrayList<String> str1;
    private ListView lv2;
    private ArrayAdapter<String> adapter2;
    private ArrayList<String> str2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_you, container, false);


        TextView name = (TextView) view.findViewById(R.id.profileName);
        String a = "name ";
        name.setText(a);

        TextView points = (TextView) view.findViewById(R.id.points);
        String b = "? points";
        points.setText(b);

        str1 = new ArrayList<String>();
        lv1 = (ListView) view.findViewById(R.id.completedList);
        adapter1 = new ArrayAdapter<String>(getActivity(),R.layout.text, str1);
        lv1.setAdapter(adapter1);
        for (int tt = 0; tt < 10; tt++){

            str1.add("completed quests " + tt);
            adapter1.notifyDataSetChanged();

        }

        str2 = new ArrayList<String>();
        lv2 = (ListView) view.findViewById(R.id.createdList);
        adapter2 = new ArrayAdapter<String>(getActivity(),R.layout.text, str2);
        lv2.setAdapter(adapter2);
        for (int tt = 0; tt < 10; tt++){

            str2.add("created quests " + tt);
            adapter2.notifyDataSetChanged();

        }


        return view;
    }
}
