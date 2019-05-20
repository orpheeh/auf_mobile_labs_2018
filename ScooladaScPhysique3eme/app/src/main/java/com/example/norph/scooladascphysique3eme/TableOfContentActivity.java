package com.example.norph.scooladascphysique3eme;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableOfContentActivity extends AppCompatActivity {

    private Map<String, List<Chapter>> mTableOfContent = new HashMap<String, List<Chapter>>();

    private String mSectionName;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_table_of_content );

        Intent intent = getIntent();

        mSectionName = intent.getStringExtra( "section" );

        List<Chapter> list = new ArrayList<>(  );
        //Chimie
        list.add( new Chapter("L'eau", R.drawable.nuage, "H<sub><small>2</small></sub>O" ));

        list.add( new Chapter("Les Hydrocarbures", R.drawable.methane, "") );

        list.add( new Chapter("Action du dioxigène sur les corps simples", R.drawable.sang, "") );

        list.add( new Chapter( "Réduction de l'oxyde de cuivre et de l'oxyde de fer", R.drawable.rouille, "") );

        list.add( new Chapter( "Des solutions acides où basiques", R.drawable.citron, "") );

        list.add( new Chapter( "Identifications d'ions en solution", R.drawable.ions, "") );

        mTableOfContent.put( SECTION_CHIMIE , list);

        //Electricite
        list = new ArrayList<>(  );

        list.add( new Chapter( "Puissance electrique", R.drawable.wattmetre, "Watt" ) );

        list.add( new Chapter( "Energie électrique", R.drawable.wattmetre, "" ) );

        list.add( new Chapter( "Moteurs électriques", R.drawable.wattmetre, "" ) );

        list.add( new Chapter( "Résistances", R.drawable.resistance, "" ) );

        list.add( new Chapter( "Association des résistances", R.drawable.wattmetre, "" ) );

        mTableOfContent.put( SECTION_ELECTRICITE , list );


        //Optique
        list = new ArrayList<>(  );

        list.add( new Chapter( "Les lentilles convergente", R.drawable.oeille, "" ) );

        list.add( new Chapter( "Construction d'une image", R.drawable.projection, "" ) );

        list.add( new Chapter( "Des appareils munis de lentilles", R.drawable.microscope, "" ) );

        mTableOfContent.put( SECTION_OPTIQUE , list );

        //Pesanteur
        list = new ArrayList<>(  );

        list.add( new Chapter( "Le poids d'un corps", R.drawable.gravitation, "" ) );

        list.add( new Chapter( "Masse et poids", R.drawable.pesanteur, "" ) );

        list.add( new Chapter( "Poussé d'Archimède", R.drawable.pousse_archimede, "" ) );

        list.add( new Chapter( "Densité et corps flottants", R.drawable.pirogue, "" ) );

        mTableOfContent.put( SECTION_PESANTEUR , list );

        mRecyclerView = findViewById( R.id.recyclerview );

        ChaptersAdapter adapter = new ChaptersAdapter( mTableOfContent.get( mSectionName ), getResources().getColor( getColorId(mSectionName) ));

        adapter.setOnItemClickListener( new ChaptersAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

                Intent i = new Intent(TableOfContentActivity.this, ChapterActivity.class);

                i.putExtra( "Section", mSectionName );
                i.putExtra( "Chapter", position + 1 );
                i.putExtra( "ContentType", ChapterActivity.CONTENT_COURSE );
                i.putExtra( "Title", mTableOfContent.get( mSectionName ).get( position ).title );

                TableOfContentActivity.this.startActivity( i );
            }
        } );

        mRecyclerView.setAdapter( adapter );

        mRecyclerView.setLayoutManager( new GridLayoutManager( this, 2 ) );
    }

    public static int getColorId(String section){
        int color = 0;
        switch (section) {
            case SECTION_CHIMIE:
                color = R.color.colorChimie;
                break;
            case SECTION_ELECTRICITE:
                color = R.color.colorElectricite;
                break;
            case SECTION_OPTIQUE:
                color = R.color.colorOptique;
                break;
            case SECTION_PESANTEUR:
                color = R.color.colorPesanteur;
                break;
        }
        return color;
    }

    public class Chapter {

        public String title;

        public int illustration;

        public String comment;

        public Chapter(String title, int ill, String com){
            this.title = title;

            this.illustration = ill;

            this.comment = com;
        }
    }

    public final static String SECTION_CHIMIE = "chimie";
    public final static String SECTION_OPTIQUE = "optique";
    public final static String SECTION_ELECTRICITE = "electricite";
    public final static String SECTION_PESANTEUR = "pesanteur";
}
