package com.mobaer.recipe;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobaer.recipe.model.Recipe;
import com.mobaer.recipe.rest_api.RecipeAPI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeList extends Fragment implements Callback<List<Recipe>> {
    View mainView;
    OnButtonCreateListener mCallback;

    public RecipeList() {
        // Required empty public constructor
    }

    public interface OnButtonCreateListener {
        public void onButtonCreated(int id, String text);
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        // This makes sure that the container activity has implemented
//        // the callback interface. If not, it throws an exception
//        try {
//            mCallback = (OnButtonCreateListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnButtonCreateListener");
//        }
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

//        String[] values = new String[] {"Schinkenhörnchen", "Mohörnchen", "Gnocchi", "Pizza"};
//        final ArrayList<String> list = new ArrayList<String>();
//        for(int i = 0; i < values.length; i++){
//            list.add(values[i]);
//        }

        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.188.33:8081/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        RecipeAPI recipeAPI = retrofit.create(RecipeAPI.class);
        Call<List<Recipe>> call = recipeAPI.loadAllRecipes();
        call.enqueue(this);

        return mainView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainView = view;
    }

//    private class MyArrayAdapter extends ArrayAdapter<Recipe> {
//
//        HashMap<Recipe, Integer> mIdMap = new HashMap<Recipe, Integer>();
//
//        public MyArrayAdapter(Context context, int textViewResourceId,
//                              List<Recipe> objects) {
//            super(context, textViewResourceId, objects);
//            for (int i = 0; i < objects.size(); ++i) {
//                mIdMap.put(objects.get(i), i);
//            }
//        }
//
//        @Override
//        public long getItemId(int position) {
//            Recipe item = getItem(position);
//            return mIdMap.get(item);
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return true;
//        }
//
//    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState){
//        super.onViewCreated(view, savedInstanceState);
//        layout = (LinearLayout) view.findViewById(R.id.recipeList_layout);
//    }

//    @Override
//    public void onStart(){
//        super.onStart();
//        //new HttpRequestRecipeList().execute();
//
////        Button button1 = new Button(getActivity());
////        button1.setText("Schinkenhörnchen");
////        button1.setOnClickListener((Recipes)getActivity());
////
////        layout.addView(button1);
////        mCallback.onButtonCreated(1, "Schinkenhörnchen");
////
////        Button button2 = new Button(getActivity());
////        button2.setText("Mohörnchen");
////        button2.setOnClickListener((Recipes)getActivity());
////
////        layout.addView(button2);
////        mCallback.onButtonCreated(2, "Mohörnchen");
////
////        Button button3 = new Button(getActivity());
////        button3.setText("Gnocchi");
////        button3.setOnClickListener((Recipes)getActivity());
////
////        layout.addView(button3);
////        mCallback.onButtonCreated(3, "Gnocchi");
//        Gson gson = new GsonBuilder().create();
//        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.188.33:8081/api/")
//                .addConverterFactory(GsonConverterFactory.create(gson)).build();
//        RecipeAPI recipeAPI = retrofit.create(RecipeAPI.class);
//        Call<List<Recipe>> call = recipeAPI.loadAllRecipes();
//        call.enqueue(this);
//    }

//    private void createButtons(List<Recipe> list){
//        for(Recipe r: list){
//            Button button = new Button(getActivity());
//            button.setText(r.getTitle());
//            button.setOnClickListener((Recipes)getActivity());
//
////            layout.addView(button);
//            mCallback.onButtonCreated(r.getPk(), r.getTitle());
//        }
//    }

    private void createListView(Recipe[] list){
        ListView listView = (ListView) mainView.findViewById(R.id.recipeList_layout);
//        ListView listView = (ListView)mainView;
        MyTestArrayAdapter adapter= new MyTestArrayAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((Recipes)getActivity());
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
//        createButtons(response.body());
        createListView(response.body().toArray(new Recipe[0]));
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        t.printStackTrace();
    }

//    private class HttpRequestRecipeList extends AsyncTask<Void, Void, Recipe[]> {
//        @Override
//        protected Recipe[] doInBackground(Void... params){
//            try {
//                final String url = "http://192.168.188.33:8081/api/recipe/";
//                RestTemplate restTemplate = new RestTemplate();
//                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//                HttpHeaders headers = new HttpHeaders();
//                headers.set("Cookie", "csrftoken=TNl7eLp24ETXj890tD0jzbPAyD2qjia5; sessionid=28zta9a1cns3lpucra3n9fnwki8oogkd");
//                headers.set("Accept", "application/json");
//
//                HttpEntity entity = new HttpEntity(headers);
//
//                HttpEntity<Recipe[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Recipe[].class);
//                //System.out.println(response.toString());
//                Recipe[] recipe = response.getBody();
//                return recipe;
//            }catch (Exception e){
//                System.out.println("problem with REST API Call" + e.getMessage());
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Recipe[] recipe){
//            createButtons(recipe);
//        }
//    }
}
