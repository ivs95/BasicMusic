package com.example.prototipotfg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GestorBBDD.getInstance().setContexto(getApplicationContext());
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
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    protected void onStop(){
        super.onStop();
        finishAffinity();
    }

    public void registro(View view){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }


}
