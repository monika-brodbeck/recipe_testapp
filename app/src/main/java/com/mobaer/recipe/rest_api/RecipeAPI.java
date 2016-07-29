package com.mobaer.recipe.rest_api;

import com.mobaer.recipe.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by momo on 25.07.16.
 */
public interface RecipeAPI {
    @Headers({
            "Accept: application/json",
            "Cookie: csrftoken=TNl7eLp24ETXj890tD0jzbPAyD2qjia5; sessionid=28zta9a1cns3lpucra3n9fnwki8oogkd"
    })
    @GET("recipe")
    Call<List<Recipe>>  loadAllRecipes();

    @Headers({
            "Accept: application/json",
            "Cookie: csrftoken=TNl7eLp24ETXj890tD0jzbPAyD2qjia5; sessionid=28zta9a1cns3lpucra3n9fnwki8oogkd"
    })
    @GET("recipe/{id}")
    Call<Recipe> getRecipe(@Path("id") int id);

    @Headers({
            "Accept: application/json",
            "Cookie: csrftoken=TNl7eLp24ETXj890tD0jzbPAyD2qjia5; sessionid=28zta9a1cns3lpucra3n9fnwki8oogkd",
            "X-CSRFToken: TNl7eLp24ETXj890tD0jzbPAyD2qjia5"
    })
    @POST("recipe/")
    Call<Recipe> storeRecipe(@Body Recipe recipe);
}
