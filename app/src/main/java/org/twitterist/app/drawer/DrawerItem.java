package org.twitterist.app.drawer;

/**
 * Created by marcowuthrich on 15.09.15.
 */

import org.twitterist.app.R;

public class DrawerItem {

    private String title;
    private int icon;
    private int defaultIcon = R.drawable.ic_drawer;

    public DrawerItem(String title){
        this.title = title;
        icon = defaultIcon;
    }
    public DrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }
}
