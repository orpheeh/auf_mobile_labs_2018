package com.example.norph.scooladascphysique3eme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class CourseSlide {

    private int mCurrentSlide;

    private String mCurrentSection;

    private LinearLayout mContainer;

    private ViewGroup mMainContainer;

    private Context mContext;

    private List<ChapterActivity.Slide> mSlide;

    private String mTitle;

    private OnEndListener mListener;

    public CourseSlide(Context context, LinearLayout container, ViewGroup mainContainer, List<ChapterActivity.Slide> slides, String partTitle, String section){
        mContext = context;
        mContainer = container;
        mSlide = slides;
        mMainContainer = mainContainer;
        mTitle = partTitle;
        mCurrentSection = section;
    }

    private void load(){
        ChapterActivity.Slide slide = mSlide.get( mCurrentSlide );
        loadLayoutForCourse( slide );

        // Ce bouton permet de changer de slide
        //Quand on arrive au dernier slide on passe au slides du Quizz
        Button next = mContainer.findViewById( R.id.next );
        next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentSlide++;
                if(mCurrentSlide >= mSlide.size()){
                    mListener.perform();
                } else {
                    ChapterActivity.Slide slide = mSlide.get( mCurrentSlide );
                    mContainer.removeViews( 1,  mSlide.get( mCurrentSlide-1 ).sections.size());
                    loadLayoutForCourse( slide );
                }
            }
        } );
    }

    private void loadLayoutForCourse(ChapterActivity.Slide slide){
        if(mCurrentSlide == 0){
            TextView title = (TextView)LayoutInflater.from( mContext ).inflate( R.layout.colortextlayout, mContainer, false );
            title.setText( mTitle );
            title.setTextColor( mContext.getResources().getColor(TableOfContentActivity.getColorId( mCurrentSection )) );
            mContainer.addView( title, 0 );
        }
        for(int i = 0; i < slide.sections.size(); i++){

            if(slide.sections.get( i ) instanceof String){
                TextView textView = (TextView) LayoutInflater.from( mContext ).inflate( R.layout.simpletextlayout, mContainer, false );
                textView.setText( (String)slide.sections.get( i ));
                mContainer.addView( textView, i+1 );
            } else {
                ImageView imageView = (ImageView)LayoutInflater.from( mContext ).inflate( R.layout.simpleimagelayout, mContainer, false );
                imageView.setImageResource( (Integer)slide.sections.get( i ) );
                mContainer.addView( imageView, i+1);
            }
        }
        mMainContainer.findViewById( R.id.main_container ).scrollTo( 0, 0 );
    }

    public void setOnEndListener(OnEndListener listener){
        mListener = listener;
    }

    public void create(){
        load();
    }

    public interface OnEndListener {
        public void perform();
    }
}
