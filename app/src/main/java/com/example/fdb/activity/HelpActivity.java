package com.example.fdb.activity;


import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.fdb.R;

public class HelpActivity extends BaseActivity
{
    TextView helpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.addContentView(R.layout.activity_help);

        helpTextView = findViewById(R.id.helpTextView);
        helpTextView.setMovementMethod(new ScrollingMovementMethod());
    }//protected void onCreate(Bundle savedInstanceState)
}//public class HelpActivity extends BaseActivity