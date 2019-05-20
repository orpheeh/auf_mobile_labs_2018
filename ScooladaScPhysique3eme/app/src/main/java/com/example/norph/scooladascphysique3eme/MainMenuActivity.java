package com.example.norph.scooladascphysique3eme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_menu );

        LinearLayout [] section = new LinearLayout[4];

        final String [] sectionName = { TableOfContentActivity.SECTION_CHIMIE, TableOfContentActivity.SECTION_ELECTRICITE,
                TableOfContentActivity.SECTION_OPTIQUE, TableOfContentActivity.SECTION_PESANTEUR };

        int[] id = { R.id.chimie, R.id.electricite, R.id.optique, R.id.mecanique };

        for(int i = 0; i < section.length; i++) {
            final int index = i;
            section[i] = findViewById( id[i] );
            section[i].setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( MainMenuActivity.this, TableOfContentActivity.class );

                    intent.putExtra( "section", sectionName[index] );

                    MainMenuActivity.this.startActivity( intent );
                }
            } );
        }
    }
}
