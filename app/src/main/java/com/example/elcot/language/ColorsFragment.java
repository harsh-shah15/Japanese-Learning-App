package com.example.elcot.language;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {
    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        mMediaPlayer.start();
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        releaseMediaPlayer();
                    }
                }
            };
    /*
      this code is triggered when the mediaplayer has finished playing sound
    */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        /*
           Create and setup AudioManager to request audio focus
         */
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        // Create a list of Words
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Red", "akai",R.drawable.color_red, R.raw.color_red));
        words.add(new Word("Green", "midori", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("Brown", "chairoi", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("Gray", "guree", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("Black", "kuroi", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("White", "shiroi", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("Yellow", "kiiroi", R.drawable.color_mustard_yellow, R.raw.color_yellow));
        words.add(new Word("Blue", "aoi", R.drawable.color_blue, R.raw.color_blue));
        words.add(new Word("Orange", "orenji", R.drawable.color_orange, R.raw.color_orange));
        words.add(new Word("Purple", "murasaki", R.drawable.color_purple, R.raw.color_purple));
        words.add(new Word("Pink", "pinku", R.drawable.color_pink, R.raw.color_pink));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.purple);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                /*
                  Release mediaplayer if it currently exsist to play the
                  next sound from the file
                 */
                releaseMediaPlayer();
                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // we have a audio focus now.

                /*
                  create and setup the mediaplayer for the audio resource for the
                  current word.
                 */
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getmMediaResourceId());
                    // start playing the audio
                    mMediaPlayer.start();
                 /*
                  setup a listener on the mediaplayer so that we can stop and release the
                  resource when the sound has finished
                 */
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        /*
           when the activity is stopped, release the mediaplayer resource,
           as we don"t need the sound to still been played
         */
        releaseMediaPlayer();
    }
    /*
      Clean up mediaplayer by releasing its resource.
    */
    private void releaseMediaPlayer() {
        /*
           if mediaplayer is not null it will be playing a sound.
         */
        if (mMediaPlayer != null) {
            /*
               Regardless to the current state of mediaplayer, release its resource
               as we no longer require them
             */
            mMediaPlayer.release();
            /*
               Set back the mediaplayer to null as it is the state when no audio is
               been played at the moment.
             */
            mMediaPlayer = null;
             /*
               Regardless of whether or not we were granted audio focus,abandon it.
               this also unregisters AudioFocusChangeListener so we don"t get anymore callbacks.
             */
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }

    }
}
