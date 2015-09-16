package org.twitterist.app.drawer;

/**
 * Created by marcowuthrich on 15.09.15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.twitterist.app.R;

import java.util.ArrayList;


public class DrawerListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<DrawerItem> navDrawerItems;
    private LayoutInflater mInflater;

    static class ViewHolder{
        ImageView icon_item;
        TextView name_item;
    }

    public DrawerListAdapter(Context context, ArrayList<DrawerItem> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View mView = convertView;
        ViewHolder holder = null;

        if (mView == null) {
            holder = new ViewHolder();
            mView = mInflater.inflate(R.layout.drawer_item_list, parent, false);

            holder.icon_item = (ImageView) mView.findViewById(R.id.icon);
            holder.name_item = (TextView) mView.findViewById(R.id.option);

            mView.setTag(holder);
        }
        else
            holder = (ViewHolder) mView.getTag();

        DrawerItem currentItem = navDrawerItems.get(position);

        if (currentItem != null )
        {
            holder.icon_item.setImageResource(currentItem.getIcon());
            holder.name_item.setText(currentItem.getTitle());
        }

        return mView;
    }
}
