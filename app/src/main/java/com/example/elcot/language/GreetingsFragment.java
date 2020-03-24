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
public class GreetingsFragment extends Fragment {
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



    public GreetingsFragment() {
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

        words.add(new Word("Hello", "kon ni chi wa", R.raw.greetings_hello));
        words.add(new Word("Good Morning", "ohayou gozai masu", R.raw.greetings_goodmorning));
        words.add(new Word("Good Afternoon", "kon ni chi wa", R.raw.greetings_goodafternoon));
        words.add(new Word("Good Evening", "kon ban wa", R.raw.greetings_goodevening));
        words.add(new Word("Good Night", "oyasumi nasai", R.raw.greetings_goodnight));
        words.add(new Word("Thank You", "arigatou gozaimasu", R.raw.greetings_thankyou));
        words.add(new Word("Sorry", "gomen nasai", R.raw.greetings_sorry));
        words.add(new Word("Welcome", "youkoso", R.raw.greetings_welcome));
        words.add(new Word("Nice to meet you", "hajimemashite", R.raw.greetings_nicetomeetyou));
        words.add(new Word("See You", "matane", R.raw.greetings_seeyou));
        words.add(new Word("Good Bye", "sayounara", R.raw.greetings_goodbye));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.blue);

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
