package com.demo.story.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.story.R;
import com.demo.story.model.item;
import com.demo.story.read;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private Context context;
    private List<item> items;
    ArrayList<Integer> count = new ArrayList();


    public CustomListAdapter(Context context, List<item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        for(int i = 0; i< items.size(); i++){
            count.add(0);
        }
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_list_view_row_items, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.description = convertView.findViewById(R.id.description);
            viewHolder.read = convertView.findViewById(R.id.read);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.read.setTag(position);
        if(items.get(position).getIsItemAdded()){
            viewHolder.read.setText("Read");
        } else
            viewHolder.read.setText("Start");
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(!items.get(position).getIsItemAdded()){
                    items.get(position).setIsItemAdded(true);
                    finalViewHolder.read.setText("Read");
                    Intent intent = new Intent(context, read.class);
                    intent.putExtra("title", items.get(position).getStoryTitle());
                    intent.putExtra("description", items.get(position).getStoryDescription());
                    context.startActivity(intent);
                //}
            }
        });
        viewHolder.title.setText(items.get(position).getStoryTitle());
        viewHolder.description.setText(items.get(position).getStoryDescription().substring(0,50) + "...");
        return convertView;
    }
    public class ViewHolder {
        TextView title;
        TextView description;
        Button read;

    }
}
