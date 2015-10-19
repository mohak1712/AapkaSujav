package com.example.mohak.aapkasujav.AppIntro.RecyclerV;

/**
 * Created by mohak on 22/8/15.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;


import com.example.mohak.aapkasujav.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sarthak on 02-07-2015.
 */                                               //classname.MyViewHolder
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
    ArrayList<Single> data;
    LayoutInflater inflater;
    //List<SingleRow> refers to list of objects of SingleRow

    public Adapter(Context context, ArrayList<Single> data)
    {
        inflater=LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Whenever new row is to be displayed this fn is called
        View view = inflater.inflate(R.layout.single_index, viewGroup , false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        //used to set data to be displayed int the recycler view and update it as per the position (i)

        Single current = data.get(position);
        viewHolder.textView.setText(current.title);
        viewHolder.circleImageView.setImageResource(current.icon);



    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
            //This class needs to be a sub class of the Adapter class
    {
        TextView textView;
        CircleImageView circleImageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView3);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.profile_image);

        }
    }
}
