package org.creatorslane.glagchurch;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class pdfreader extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfreader);
        String value = getIntent().getStringExtra("pdf");
        pdfView=(PDFView)findViewById(R.id.pdfView);
        File f=new File(value);
        pdfView.fromFile(f).load();

    }
}
