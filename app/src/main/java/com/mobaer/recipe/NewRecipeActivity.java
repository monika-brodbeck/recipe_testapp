package com.mobaer.recipe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobaer.recipe.model.Recipe;
import com.mobaer.recipe.rest_api.RecipeAPI;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewRecipeActivity extends AppCompatActivity implements Callback<Recipe> {
    public static final int REQUEST_PHOTO = 1;
    public static final String RECIPE_PHOTO = "recipePhotoPath";

    private String filePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        if(savedInstanceState != null && savedInstanceState.get(RECIPE_PHOTO) != null){
            filePath = savedInstanceState.getString(RECIPE_PHOTO);
            Uri file = Uri.parse(filePath);
            showImage(file);
        }
    }

    public void saveRecipe(View view){
        Recipe newRecipe = new Recipe();
        String title = ((EditText)findViewById(R.id.title_newRecipe)).getText().toString();
        int time = Integer.parseInt(((EditText)findViewById(R.id.duration_newRecipe)).getText().toString());
        String text = ((EditText)findViewById(R.id.text_newRecipe)).getText().toString();
        newRecipe.setTitle(title);
        newRecipe.setText(text);
        newRecipe.setTimeNeeded(time);
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.188.33:8081/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        RecipeAPI recipeAPI = retrofit.create(RecipeAPI.class);
        Call<Recipe> call = recipeAPI.storeRecipe(newRecipe);
        call.enqueue(this);
    }

    public void addPhoto(View view){
        Intent requestPhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            System.out.println("Error und creating image");
            ex.printStackTrace();
        }

        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.mobaer.recipe.fileprovider",
                    photoFile);
            requestPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(requestPhotoIntent, REQUEST_PHOTO);
        }

//        if(requestPhotoIntent.resolveActivity(getPackageManager()) != null){
//            startActivityForResult(requestPhotoIntent, REQUEST_PHOTO);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            Bitmap imageBitmap = data.getParcelableExtra("data");
//            showImage(imageBitmap);

            Uri file = Uri.parse(filePath);
            showImage(file);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if(filePath != null) {
            savedInstanceState.putString(RECIPE_PHOTO, filePath);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String recipeName = ((EditText)findViewById(R.id.title_newRecipe)).getText().toString();
        String imageFileName = "JPEG_" + recipeName + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        filePath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void showImage(Uri fileUri){
        try {
            int orientation = getOrientationEXIF(fileUri);
            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
            int height = imageBitmap.getHeight();
            int width = imageBitmap.getWidth();
            if(height > width){
                width = (int)(width / (height / 120.0));
                height = 120;
            }else{
                height = (int)(height / (width / 120.0));
                width = 120;
            }
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
            imageBitmap = rotate(orientation, imageBitmap);

            FrameLayout imageContainer = (FrameLayout) findViewById(R.id.imageContainer_newRecipe);
            imageContainer.removeAllViews();
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(imageBitmap);
            imageContainer.addView(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static int getOrientationEXIF(Uri uri) {
        try {

            ExifInterface exif = new ExifInterface(uri.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Bitmap rotate(float rotationValue, Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(rotationValue);

        Bitmap rotated = Bitmap.createBitmap(image, 0, 0, width, height, matrix, true);

        return rotated;
    }

    @Override
    public void onResponse(Call<Recipe> call, Response<Recipe> response) {
        if(response.code() == 201){
            Toast toast = Toast.makeText(this, "Recipe saved successfully", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, Recipes.class);
//            EditText editText = (EditText)findViewById(R.id.edit_message);
//            String message = editText.getText().toString();
//            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(this, "Recipe could not be saved", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onFailure(Call<Recipe> call, Throwable t) {
        Toast toast = Toast.makeText(this, "Error on save "+t.getMessage(), Toast.LENGTH_SHORT);
        toast.show();
    }
}
