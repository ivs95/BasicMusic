package com.example.prototipotfg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prototipotfg.BBDD.AppDatabase;
import com.example.prototipotfg.BBDD.Usuario;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class Login extends AppCompatActivity {

    private boolean registro = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
        for (int i = 0; i < ModoJuego.values().length; i++) {
            ModoJuego.values()[i].rellena_cambios(ModoJuego.values()[i].toString(), this);
        }

        GestorBBDD.getInstance().setContexto(getApplicationContext());
        GestorBBDD.getInstance().compruebaUsuarioPrueba();
        if (GestorBBDD.getInstance().usuarioRecordado()){
            confirmaLogin();
        }
        setContentView(R.layout.activity_login);


    }

    public void log(View view){
        String correo = ((EditText)findViewById(R.id.txtEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.txtPassword)).getText().toString();
        boolean recordado= ((CheckBox)findViewById(R.id.checkRecordado)).isChecked();
        if (!correo.isEmpty() && !password.isEmpty()){
            if (GestorBBDD.getInstance().validaUsuario(correo,password,recordado)){
                confirmaLogin();
            }
            else{
                ((TextView)findViewById(R.id.textErrorLog)).setVisibility(View.VISIBLE);
                ((EditText)findViewById(R.id.txtEmail)).setText("");
                ((EditText)findViewById(R.id.txtPassword)).setText("");

            }
        }
        else{
            ((TextView)findViewById(R.id.textErrorLog)).setVisibility(View.VISIBLE);
        }
    }

    private void confirmaLogin() {
        registro = false;
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    protected void onStop(){
        super.onStop();
        if(!registro)
            finishAffinity();
    }



    public void registro(View view){
        registro = true;
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }


}
