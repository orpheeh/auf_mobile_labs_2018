package com.example.norph.scooladascphysique3eme;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChapterActivity extends AppCompatActivity {

    public static final String CONTENT_COURSE = "course";

    public static final String CONTENT_QUIZZ = "quizz";

    private String mCurrentSection = "chimie";

    private int mCurrentChapter = 0;

    private int mCurrentPart = 0;

    private String mTitle;

    private Chapter mChapter;

    private CourseSlide mCourseSlider;

    private QuizzSlider mQuizzSlider;

    private String mContentType = CONTENT_COURSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chapter );

        Intent intent = getIntent();

        mCurrentSection = intent.getStringExtra( "Section" );
        mCurrentChapter = intent.getIntExtra( "Chapter", 1 );
        mCurrentPart = intent.getIntExtra( "Part", 0 );
        mContentType = intent.getStringExtra( "ContentType" );
        mTitle = intent.getStringExtra( "Title" );

        mChapter = loadChapterOnJSon();

        final ScrollView view = findViewById( R.id.main_container );
        if(mContentType.equals(CONTENT_COURSE) ) {
            View planView = LayoutInflater.from( this ).inflate( R.layout.layout_chapter_plan, view,  false );
            view.addView( planView );

            loadCoursePlane();

            Button buttonStart = findViewById( R.id.get_start );
            buttonStart.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    view.removeAllViews();
                    View v = LayoutInflater.from( ChapterActivity.this ).inflate( R.layout.layout_course, view, false );
                    view.addView( v );

                    LinearLayout container = findViewById( R.id.container );
                    List<Slide> slide = mChapter.parts.get( mCurrentPart ).slides;

                    mCourseSlider = new CourseSlide( ChapterActivity.this, container, view, slide, mChapter.parts.get( mCurrentPart ).title, mCurrentSection );
                    mCourseSlider.create();

                    mCourseSlider.setOnEndListener( new CourseSlide.OnEndListener() {
                        @Override
                        public void perform() {
                            ViewGroup mainContainer = findViewById( R.id.main_container );
                            List<Quizz> quizz = mChapter.parts.get( mCurrentPart ).quizz;
                            mQuizzSlider = new QuizzSlider( ChapterActivity.this, mainContainer, quizz);

                            mQuizzSlider.create();
                        }
                    } );
                }
            } );

        } else if(mContentType.equals( CONTENT_QUIZZ) ){
            //TODO
        }
    }


    private void loadCoursePlane(){
        LinearLayout container = findViewById( R.id.part_container );

        TextView titleView = findViewById( R.id.title );
        titleView.setText( mTitle );

        for(int i = 0; i < mChapter.parts.size(); i++){
            LinearLayout partView = (LinearLayout) LayoutInflater.from( this ).inflate( R.layout.layout_chapter_plan_title, container, false );
            TextView textView = partView.findViewById( R.id.text );
            textView.setText( (mChapter.parts.get( i ).title) );

            container.addView( partView );
        }
    }

    private String loadJSONFromAsset(String filename){
        String json = null ;
        try {
            InputStream is = ChapterActivity.this.getAssets().open( filename );
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read( buffer );
            json = new String(buffer, "UTF-8");
            is.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return json;
    }

    public Chapter loadChapterOnJSon(){
        Chapter chapter = new Chapter();

        try {
            String filename = mCurrentSection + "_" + mCurrentChapter + ".json";
            JSONArray jaRoot = new JSONArray( loadJSONFromAsset( filename ) );

            for(int part = 0; part < jaRoot.length(); part++) {

                JSONObject joPart = jaRoot.getJSONObject( part );

                JSONArray ja = joPart.getJSONArray( "course" );

                Part part1 = new Part();

                part1.title = joPart.getString( "title" );

                for (int i = 0; i < ja.length(); i++) {
                    Slide slide = new Slide();
                    JSONObject joSlide = ja.getJSONObject( i );
                    JSONArray jaValue = (JSONArray) joSlide.get( "value" );
                    JSONArray jaType = (JSONArray) joSlide.get( "type" );

                    for (int j = 0; j < jaValue.length(); j++) {
                        if (Integer.parseInt( (String) jaType.get( j ) ) == 1) {
                            slide.sections.add( (String) jaValue.get( j ) );
                        } else if(Integer.parseInt( (String)jaType.get( j )) == 2){
                            int id = this.getResources().getIdentifier( (String) jaValue.get( j ), "drawable", this.getPackageName() );
                            slide.sections.add( id );
                        }
                    }
                    part1.slides.add( slide );
                }

                JSONArray jaQuizz = joPart.getJSONArray( "quizz" );

                for(int i = 0; i < jaQuizz.length(); i++){
                    JSONObject joQuizz = jaQuizz.getJSONObject( i );
                    String question = (String)joQuizz.get( "question" );
                    JSONArray jaProps = (JSONArray) joQuizz.get( "propositions" );
                    int reponse = Integer.parseInt((String)joQuizz.get( "reponse" ));

                    Quizz quizz = new Quizz();
                    quizz.question = question;
                    quizz.reponse = reponse;
                    for(int j = 0; j < jaProps.length(); j++){
                        quizz.propostions.add( (String)jaProps.get( j ) );
                    }
                    part1.quizz.add( quizz );
                }
                chapter.parts.add( part1 );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return chapter;
    }

    public static class Slide {
        public List<Object> sections = new ArrayList<>(  );
    }

    public static class Quizz {
        public String question;

        public List<String> propostions = new ArrayList<>(  );

        public int reponse;
    }

    public static class Part {
        public List<Slide> slides = new ArrayList<>(  );

        public List<Quizz> quizz = new ArrayList<>(  );

        public String title;
    }

    public static class Chapter {
        public List<Part> parts = new ArrayList<>(  );
    }
}
