package com.mobaer.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by momo on 22.07.16.
 */
public class Item {
    private int pk;
    private String title;
    private String unit;
    private String isInfinite;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String isInfinite() {
        return isInfinite;
    }

    public void setIsInfinite(String isInfinite) {
        this.isInfinite = isInfinite;
    }
}
