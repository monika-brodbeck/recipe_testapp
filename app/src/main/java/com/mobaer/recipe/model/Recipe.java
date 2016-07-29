package com.mobaer.recipe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by momo on 22.07.16.
 */
public class Recipe {

    @SerializedName("pk")
    @Expose
    private int pk;
    @SerializedName("title")
    @Expose
    private String title;
//    @SerializedName("user")
//    @Expose
//    private String user;
    @SerializedName("timeNeeded")
    @Expose
    private int timeNeeded;
    @SerializedName("isPublic")
    @Expose
    private boolean isPublic;
    @SerializedName("text")
    @Expose
    private String text;
//    @SerializedName("ingredients")
//    @Expose
//    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

    /**
     *
     * @return
     * The pk
     */
    public int getPk() {
        return pk;
    }

    /**
     *
     * @param pk
     * The pk
     */
    public void setPk(int pk) {
        this.pk = pk;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

//    /**
//     *
//     * @return
//     * The user
//     */
//    public String getUser() {
//        return user;
//    }
//
//    /**
//     *
//     * @param user
//     * The user
//     */
//    public void setUser(String user) {
//        this.user = user;
//    }

    /**
     *
     * @return
     * The timeNeeded
     */
    public int getTimeNeeded() {
        return timeNeeded;
    }

    /**
     *
     * @param timeNeeded
     * The timeNeeded
     */
    public void setTimeNeeded(int timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    /**
     *
     * @return
     * The isPublic
     */
    public boolean isIsPublic() {
        return isPublic;
    }

    /**
     *
     * @param isPublic
     * The isPublic
     */
    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

    public String toString(){
        return title;
    }

//    /**
//     *
//     * @return
//     * The ingredients
//     */
//    public List<Ingredient> getIngredients() {
//        return ingredients;
//    }
//
//    /**
//     *
//     * @param ingredients
//     * The ingredients
//     */
//    public void setIngredients(List<Ingredient> ingredients) {
//        this.ingredients = ingredients;
//    }



//
//    private int pk;
//    private String title;
//    private int timeNeeded;
////    private int portions;
//    private boolean isPublic;
//    private String text;
//    //private HashMap<Item, Integer> ingredients;
//
//
//    public int getPk() {
//        return pk;
//    }
//
//    public void setPk(int pk) {
//        this.pk = pk;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public int getTimeNeeded() {
//        return timeNeeded;
//    }
//
//    public void setTimeNeeded(int time) {
//        this.timeNeeded = time;
//    }
////
////    public int getPortions() {
////        return portions;
////    }
////
////    public void setPortions(int portions) {
////        this.portions = portions;
////    }
//
//    public boolean getIsPublic() {
//        return isPublic;
//    }
//
//    public void setIsPublic(boolean aPublic) {
//        isPublic = aPublic;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String description) {
//        this.text = description;
//    }
//
////    public HashMap<Item, Integer> getIngredients() {
////        return ingredients;
////    }
////
////    public void setIngredients(HashMap<Item, Integer> ingredients) {
////        this.ingredients = ingredients;
////    }
}
