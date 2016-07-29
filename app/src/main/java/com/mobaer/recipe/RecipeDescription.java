package com.mobaer.recipe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDescription extends Fragment {
    public static final String RECIPE_NUMBER = "com.mobaer.recipe.RECIPE_ID";
    private FrameLayout layout;

    public RecipeDescription() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_description, container, false);
        System.out.println("fragmentView instantiated");
        Bundle args = getArguments();
        if(args == null){
            return view;
        }
        String text = args.getString(RECIPE_NUMBER);
        FrameLayout layout = (FrameLayout)view.findViewById(R.id.description_layout);
        createTextView(text, layout);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = (FrameLayout) view.findViewById(R.id.description_layout);
    }

    public void updateContent(String text){
        layout.removeAllViews();
        createTextView(text, layout);

    }

    private void createTextView(String text, FrameLayout layout) {
        TextView descriptionText = new TextView(getActivity());
//        switch(recipe_id) {
//            case 1:
//                descriptionText.setText("Irgendein Text den ich noch durch was sinnvolles ersetzen muss. Der sollte aber ein bisschen länger sein drum steht hier so viel Blödsinn");
//                break;
//            case 2:
//                descriptionText.setText("Das ist ein anderer doofer Beispieltext. Der wird nur für Chrihörnchen verwendet, die anderen Rezepte haben einen anderen");
//                break;
//            default:
//                descriptionText.setText("Rezept nicht gefunden");
//                break;
//        }
        descriptionText.setText(text);
        layout.addView(descriptionText);
    }

}
