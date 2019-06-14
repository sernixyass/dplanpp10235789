package com.example.carpoolingappv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;


public class AddPostActivity extends AppCompatActivity {
    public static final String EXTRA_STARTpOINT =
            "com.example.carpoolingappv1.EXTRA_STARTpOINT";

    public static final String EXTRA_ENDpOINT =
            "com.example.carpoolingappv1.EXTRA_ENDpOINT";


    private EditText startingPoint;
    private EditText endingPoint;
    private FloatingActionButton confermeAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        startingPoint = findViewById(R.id.edit_start_point);
        endingPoint = findViewById(R.id.edit_end_point);
        confermeAdd=findViewById(R.id.btn_conform_add);
        confermeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePoste();
            }
        });



    }


    private void savePoste() {
        String startP = startingPoint.getText().toString();
        String endP = endingPoint.getText().toString();
        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        if (startP.trim().isEmpty() || endP.trim().isEmpty()) {
            Toast.makeText(this, "please insert a startingPoint and endingPoint", Toast.LENGTH_SHORT).show();
            return;
            //this return without executing any code below
        }
        //save to firebase table .............................



        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_STARTpOINT, startP);
        bundle.putString(EXTRA_ENDpOINT, endP);
        Toast.makeText(this, "setting bundele", Toast.LENGTH_SHORT).show();
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        finish();


    /*    Intent data = new Intent(this,HomeFragment.class);
        data.putExtra(EXTRA_STARTpOINT, startP);
        data.putExtra(EXTRA_ENDpOINT, endP);
        setResult(RESULT_OK, bundle);
        finish();*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.poste_menu, menu);
        return true;
    }














    //Handele clicks

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_conform_add:
                Toast.makeText(this, "00", Toast.LENGTH_SHORT).show();
                //hade el method li tsauvgarder lpost
                savePoste();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/
}

