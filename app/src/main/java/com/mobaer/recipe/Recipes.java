package com.mobaer.recipe;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobaer.recipe.model.Recipe;
import com.mobaer.recipe.rest_api.RecipeAPI;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recipes extends FragmentActivity implements View.OnClickListener, Callback<Recipe>, AdapterView.OnItemClickListener{
//    private HashMap<String, Integer> recipeIds;

    public Recipes(){
//        recipeIds = new HashMap<String, Integer>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        //not all layouts use fragment container, other display fragments all at once
        //this is already defined within layout file
        if(findViewById(R.id.fragment_container_recipe) != null){
            //if coming from previous state (savedInstance not null) don't load fragment,
            //use the one already loaded
            if(savedInstanceState != null){
                return;
            }

            RecipeList firstFragment = new RecipeList();
            //pass intents extras, activity could be started by intent
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_recipe, firstFragment).commit();
        }
    }

    private void openText(String text){

        if(findViewById(R.id.fragment_container_recipe) != null) {
            RecipeDescription secondFragment = new RecipeDescription();
            Bundle args = new Bundle();
            args.putString(RecipeDescription.RECIPE_NUMBER, text);
            secondFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_recipe, secondFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }else if(getSupportFragmentManager().findFragmentById(R.id.recipeDescription_fragment) != null){
            RecipeDescription secondFragment = (RecipeDescription)getSupportFragmentManager().findFragmentById(R.id.recipeDescription_fragment);
            secondFragment.updateContent(text);
        }
    }

    @Override
    public void onClick(View view) {
//        Toast toast = Toast.makeText(this, "Test Toast neues Rezept", Toast.LENGTH_SHORT);
//        toast.show();

        Intent intent = new Intent(this, NewRecipeActivity.class);
        startActivity(intent);
    }

//    @Override
//    public void onButtonCreated(int id, String text) {
//        recipeIds.put(text, id);
//    }

    @Override
    public void onResponse(Call<Recipe> call, Response<Recipe> response) {
        openText(response.body().getText());
    }

    @Override
    public void onFailure(Call<Recipe> call, Throwable t) {
        t.printStackTrace();
        openText(t.getMessage());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Recipe item = (Recipe)adapterView.getItemAtPosition(i);

        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.188.33:8081/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        RecipeAPI recipeAPI = retrofit.create(RecipeAPI.class);
        Call<Recipe> call = recipeAPI.getRecipe(item.getPk());
        call.enqueue(this);
    }
}
