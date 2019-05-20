package com.example.norph.scooladascphysique3eme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        Button login = findViewById( R.id.login );
        login.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, MainMenuActivity.class);
                MainActivity.this.startActivity( i );
            }
        } );

        Button plus = findViewById( R.id.btn_plus );
        plus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );
    }
}
