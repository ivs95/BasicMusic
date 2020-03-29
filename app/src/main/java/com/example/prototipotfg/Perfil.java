package com.example.prototipotfg;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipotfg.BBDD.AppDatabase;
import com.example.prototipotfg.BBDD.Usuario;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class Perfil extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);


    }

    public void EditarUsuario(View view){
        String nombre = ((EditText)findViewById(R.id.txtNombrePerfil)).getText().toString();
        String password = ((EditText)findViewById(R.id.txtPass1Perfil)).getText().toString();
        if (nombre.isEmpty()){
            ((EditText)findViewById(R.id.txtNombrePerfil)).setError("El nombre no puede estar vacío");
        }
        else if (password.isEmpty()){
            ((EditText)findViewById(R.id.txtPass1Perfil)).setError("La contraseña no puede estar vacía");

        }
        else if (!password.equals(((EditText)findViewById(R.id.txtPass2Perfil)).getText().toString())){
            ((EditText)findViewById(R.id.txtPass1Perfil)).setError("La contraseña no coincide");
            ((EditText)findViewById(R.id.txtPass2Perfil)).setError("La contraseña no coincide");

        }
        else{
            Usuario usuario = new Usuario(GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(),nombre,password,false);
            if (GestorBBDD.getInstance().UpdateUsuario(usuario)) {
                GestorBBDD.getInstance().setUsuarioLoggeado(usuario);
                this.finish();
            }
            else
                ((TextView)findViewById(R.id.textErrorPerfil)).setVisibility(View.VISIBLE);
        }

    }

}
