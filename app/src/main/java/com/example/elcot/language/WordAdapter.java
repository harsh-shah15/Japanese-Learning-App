package com.example.elcot.language;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    /*
       Resource ID for the background color for the list of words
     */
    private int mColorResourseId;


    public WordAdapter(Activity context, ArrayList<Word> words,int colorResourceId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        mColorResourseId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the sample text
        TextView sampleTextView = (TextView) listItemView.findViewById(R.id.sample_text_view);
        // Get thes ample text from the current Word object and
        // set this text on the sample TextView
        sampleTextView.setText(currentWord.getSampleTranslation());

        // Find the TextView in the list_item.xml layout with the default text
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the default text from the current Word object and
        // set this text on the default TextView
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Find the ImageView in the list_item.xml layout with the image
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        // Get the image from the current Word object and
        // set this image on the image View
        if (currentWord.hasImage()) {

            imageView.setImageResource(currentWord.getmImageResourceId());


        }
        else {
            imageView.setVisibility(View.GONE);
        }
        /*
           set background color
         */
        View textContainer = listItemView.findViewById(R.id.text_Container);
        int color = ContextCompat.getColor(getContext(),mColorResourseId);
        textContainer.setBackgroundColor(color);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}

