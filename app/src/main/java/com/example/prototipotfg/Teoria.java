package com.example.prototipotfg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import static android.view.View.GONE;

public class Teoria extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teoria);
    }

    public void siguiente(View view){
        findViewById(R.id.textView1).setVisibility(GONE);
        findViewById(R.id.textView2).setVisibility(GONE);
        findViewById(R.id.textView3).setVisibility(GONE);
        findViewById(R.id.textView5).setVisibility(GONE);
        findViewById(R.id.textView6).setVisibility(GONE);
        findViewById(R.id.textView7).setVisibility(GONE);
        findViewById(R.id.textView4).setVisibility(View.VISIBLE);
        findViewById(R.id.textView8).setVisibility(View.VISIBLE);
        findViewById(R.id.textView9).setVisibility(View.VISIBLE);
        findViewById(R.id.textView10).setVisibility(View.VISIBLE);
        view.setVisibility(GONE);
        findViewById(R.id.btnCerrar).setVisibility(View.VISIBLE);
    }

    public void cerrar(View view){
        this.finish();
    }
}
