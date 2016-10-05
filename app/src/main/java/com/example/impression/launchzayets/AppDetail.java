package com.example.impression.launchzayets;

import android.graphics.drawable.Drawable;

public class AppDetail {
    CharSequence name;
    CharSequence label;
    Drawable icon;
    int count;

    public AppDetail(CharSequence name, CharSequence label, Drawable icon, int count) {
        this.name = name;
        this.label = label;
        this.icon = icon;
        this.count = count;
    }

    public CharSequence getName() {
        return name;
    }

    public CharSequence getLabel() {
        return label;
    }

    public Drawable getIcon() {
        return icon;
    }

    public int getCount() {
        return count;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public void setLabel(CharSequence label) {
        this.label = label;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
