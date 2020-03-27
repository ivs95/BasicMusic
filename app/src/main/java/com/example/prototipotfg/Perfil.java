package com.example.prototipotfg;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipotfg.BBDD.Usuario;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class Perfil extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void registrarUsuario(View view){
        String correo = ((EditText)findViewById(R.id.txtCorreoRegistro)).getText().toString();
        String nombre = ((EditText)findViewById(R.id.txtNombreRegistro)).getText().toString();
        String password = ((EditText)findViewById(R.id.txtPass1)).getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            ((EditText)findViewById(R.id.txtCorreoRegistro)).setError("Email no válido");
        }
        else if (nombre.isEmpty()){
            ((EditText)findViewById(R.id.txtNombreRegistro)).setError("El nombre no puede estar vacío");
        }
        else if (password.isEmpty()){
            ((EditText)findViewById(R.id.txtPass1)).setError("La contraseña no puede ser vacía");

        }
        else if (!password.equals(((EditText)findViewById(R.id.txtPass2)).getText().toString())){
            ((EditText)findViewById(R.id.txtPass1)).setError("La contraseña no coincide");
            ((EditText)findViewById(R.id.txtPass2)).setError("La contraseña no coincide");

        }
        else{
            Usuario usuario = new Usuario(correo,nombre,password);
            if (GestorBBDD.getInstance().registraUsuario(usuario))
                this.finish();
            else
                ((TextView)findViewById(R.id.textErrorRegistro)).setVisibility(View.VISIBLE);
        }

    }

}
