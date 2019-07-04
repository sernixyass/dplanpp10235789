package com.example.carpoolingappv1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class ReportPopupActivity extends AppCompatActivity {

    EditText messageReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_popup);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //placesText = findViewById(R.id.C_P_places);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.95),(int)(height*.3));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x= 0 ;
        params.y= -20;


        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        messageReport = findViewById(R.id.messageReport);

        final String hint = getIntent().getStringExtra("whichReport");

        findViewById(R.id.sendReportBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hint.equals("user")){
                    MainActivity.sendUserReport(MainActivity.currentUserID,MainActivity.selectedDriverAccountID,messageReport.getText().toString());
                }else {
                    MainActivity.sendPostReport(MainActivity.currentUserID,HomeFragment.selectedTripID,messageReport.getText().toString());
                }
                finish();
            }
        });
    }
}
