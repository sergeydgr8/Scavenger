package com.android9033.scavenger.scavenger.Controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android9033.scavenger.scavenger.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

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
    private ParseUser curUser=ParseUser.getCurrentUser();
    private ImageView mImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_you, container, false);


        TextView name = (TextView) view.findViewById(R.id.profileName);
        String a = curUser.getUsername();
        name.setText(a);

        TextView points = (TextView) view.findViewById(R.id.points);
        String b = curUser.getString("point");
        points.setText("points: " + b);

        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(mIntent);
            }
        });


        mImageView = (ImageView) view.findViewById(R.id.image);
        ParseFile profile=curUser.getParseFile("image");
        profile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if(e==null){
                    Bitmap profile= BitmapFactory.decodeByteArray(data, 0, data.length);
                    mImageView.setImageBitmap(profile);
                }
            }
        });

        str1 = new ArrayList<String>();
        lv1 = (ListView) view.findViewById(R.id.completedList);
        adapter1 = new ArrayAdapter<String>(getActivity(),R.layout.text, str1);
        lv1.setAdapter(adapter1);
        List<String> completeList=curUser.getList("complete");
        for (int i = 0; i< completeList.size(); i++){

            str1.add(completeList.get(i));
            adapter1.notifyDataSetChanged();

        }

        str2 = new ArrayList<String>();
        lv2 = (ListView) view.findViewById(R.id.createdList);
        adapter2 = new ArrayAdapter<String>(getActivity(),R.layout.text, str2);
        lv2.setAdapter(adapter2);
        List<String> createList=curUser.getList("create");
        for (int i = 0; i< createList.size(); i++){

            str2.add(createList.get(i));
            adapter2.notifyDataSetChanged();

        }


        return view;
    }
}
