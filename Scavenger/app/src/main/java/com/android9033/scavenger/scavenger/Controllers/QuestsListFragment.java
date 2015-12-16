package com.android9033.scavenger.scavenger.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android9033.scavenger.scavenger.Models.Quest;
import com.android9033.scavenger.scavenger.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by yirongshao on 11/21/15.
 */
public class QuestsListFragment extends Fragment {

    private ListView lv;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> str;

    Stack<Quest> quests = new Stack<Quest>();
    ArrayList<String> questIDs = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_quests, container, false);
        str = new ArrayList<String>();
        lv = (ListView) view.findViewById(R.id.lvQuest);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1, str);
        lv.setAdapter(adapter);

        ParseQuery<Quest> query = new ParseQuery<Quest>("Quest2");
        query.findInBackground(new FindCallback<Quest>() {
            @Override
            public void done(List<Quest> objects, ParseException e) {
                if (e == null)
                {
                    for (Quest q : objects)
                    {
                        //List user_list = q.getList("userfinished");
                        //String curr_user = ParseUser.getCurrentUser().getUsername();
                        //if (!user_list.contains(curr_user))
                        quests.push(q);
                        questIDs.add(0, q.getObjectId());
                    }
                }
                int index = 0;
                for (int tt = 0; tt < 10; tt++)
                {
                    if (!quests.isEmpty())
                    {
                        index++;
                        Quest q = quests.pop();
                        str.add(q.getString("name"));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getActivity(), QuestActivity.class);
                mIntent.putExtra("questID", questIDs.get(position));
                mIntent.putExtra("questName", str.get(position));
                startActivity(mIntent);
            }
        });

        return view;
    }
}
