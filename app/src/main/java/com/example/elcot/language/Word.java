package com.example.elcot.language;

public class Word {
    /** Default translation for the Word */
    private String mDefaultTranslation;
    /** sample translation for the Word */
    private String mSampleTranslation;
    // image resource id for the Word
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    private int mMediaResourceId;
    /*
      Create a new Word object.
      @param defaultTranslation is the Word in a language that the user is already familiar with
                                 (such as English)
      @param  sampleTranslation is the Word in the sample language
     */
    public  Word(String defaultTranslation, String sampleTranslation, int mediaResourceId) {
        mDefaultTranslation = defaultTranslation;
        mSampleTranslation = sampleTranslation;
        mMediaResourceId = mediaResourceId;
    }
    /*
      Create a new Word object.
      @param defaultTranslation is the Word in a language that the user is already familiar with
                                 (such as English)
      @param  sampleTranslation is the Word in the sample language
     */
    public  Word(String defaultTranslation, String sampleTranslation,int imageResourceId, int mediaResourceId) {
        mDefaultTranslation = defaultTranslation;
        mSampleTranslation = sampleTranslation;
        mImageResourceId = imageResourceId;
        mMediaResourceId = mediaResourceId;
    }
    /**
     * Get the default translation of the Word.
     */
    public String getDefaultTranslation() {

        return mDefaultTranslation;
    }
    /**
     * Get the Sample translation of the Word.
     */
    public String getSampleTranslation() {
        return mSampleTranslation;
    }
    /*
       get image resource id of the Word.
     */

    public int getmImageResourceId() {
        return mImageResourceId;
    }
    /*
      checks if the image is there or not for this Word
    */
    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
    /*
       get media resource id of the Word.
     */
    public int getmMediaResourceId(){return mMediaResourceId;}

}
