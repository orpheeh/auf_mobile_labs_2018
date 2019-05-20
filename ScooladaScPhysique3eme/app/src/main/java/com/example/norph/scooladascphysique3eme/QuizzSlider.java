package com.example.norph.scooladascphysique3eme;

import android.content.Context;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizzSlider {
    private Context mContext;

    private RadioButton[] mRadioButton;

    private ViewGroup mMainContainer;

    private LinearLayout mPropositionContainer;

    private int mCurrentQuestion;

    //Pour calculer le score on fait:
    // +1 pour chaque bonne réponse
    // -0.5 pour chaque mauvaise réponse
    // 0.0 pour aucune tantative
    private float mScore = 0.0f;

    private List<ChapterActivity.Quizz> mQuizz;

    public QuizzSlider(Context context, ViewGroup mainContainer, List<ChapterActivity.Quizz> quizz){
        mContext = context;

        mMainContainer = mainContainer;

        mQuizz = quizz;
    }

    private void load(){
        mMainContainer.removeAllViews();
        LinearLayout view = (LinearLayout) LayoutInflater.from( mContext ).inflate( R.layout.layout_quizz, mMainContainer, false );
        mMainContainer.addView( view );
        mPropositionContainer = mMainContainer.findViewById( R.id.props );

        final ChapterActivity.Quizz quizz = mQuizz.get( mCurrentQuestion );
        loadLayoutForQuizz( quizz );

        Button confirm = mMainContainer.findViewById( R.id.confirm );
        confirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentQuestion++;

                if(mCurrentQuestion >= mQuizz.size()){
                    //TODO implement on end listener
                } else {
                    mPropositionContainer.removeAllViews();

                    for(int i = 0; i < mRadioButton.length; i++){
                        if(mRadioButton[i].isChecked()){
                            if(i == quizz.reponse){
                                mScore += 1.0f;
                            } else {
                                mScore -= 0.5f;
                                if(mScore < 0) mScore = 0.0f;
                            }
                        }
                    }

                    loadLayoutForQuizz( mQuizz.get( mCurrentQuestion ) );
                }
            }
        } );
    }

    public void create(){
        load();
    }

    public void loadLayoutForQuizz(ChapterActivity.Quizz quizz){
        TextView question = mMainContainer.findViewById( R.id.question );
        question.setText( quizz.question );

        TextView questionNum = mMainContainer.findViewById( R.id.question_num );
        questionNum.setText( "Question : " + (mCurrentQuestion + 1) + "/" + mQuizz.size() );

        TextView scoreView = mMainContainer.findViewById( R.id.score );
        scoreView.setText( "Score : " + mScore );

        mRadioButton = new RadioButton[quizz.propostions.size()];

        for(int i = 0; i < quizz.propostions.size(); i++){
            mRadioButton[i] = new RadioButton( mContext );

            mRadioButton[i].setText( Html.fromHtml( quizz.propostions.get( i )) );

            mPropositionContainer.addView( mRadioButton[i] );
        }
    }
}
