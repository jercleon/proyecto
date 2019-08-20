package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CrearCuenta extends AppCompatActivity {
    //se definen las variables
    private Spinner spinner1;
    Integer id_sector;
    EditText editText1, editText2, editText3, editText4;
    Button registrar;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        //guardamos los parametros ingresados por el usuario en variables
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        registrar = findViewById(R.id.button2);
        spinner1= (Spinner)findViewById(R.id.spinner1);
        String [] opciones= {"Guayaquil","Daule"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        spinner1.setAdapter(adapter);
        //se setea el codigo del sector dependiendo de la seleccion en el spinner
        String sector= spinner1.getSelectedItem().toString();
        if (sector.equals("Guayaquil")){
            id_sector = 2;
        } else if (sector.equals("Daule")){
            id_sector = 1;
        }

        if (editText1.getText().toString().isEmpty())
            Toast.makeText(this, "Por favor, ingresar un campo válido en Nombre", Toast.LENGTH_LONG).show();
        else{
            if (editText2.getText().toString().isEmpty()){
                Toast.makeText(this,"Por favor, ingresar un campo válido en email",Toast.LENGTH_LONG).show();
            }else{
                if (editText3.getText().toString().isEmpty()){
                    Toast.makeText(this,"Por favor, ingresar un campo válido en Constraseña",Toast.LENGTH_LONG).show();
                }else{
                    if (editText4.getText().toString().isEmpty()){
                        Toast.makeText(this,"Por favor, ingresar un campo válido en Constraseña",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this,"Su registro se realizo exitosamente",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

        //se crea el metodo que se ejecutara cuando se presione el boton de registrar
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarServicio("http://192.168.0.106/Register.php");
            }
        });
    }

    //se crea el metodo que envaira las peticiones al servidor
    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("nombres",editText1.getText().toString());
                parametros.put("correo",editText2.getText().toString());
                parametros.put("password",editText3.getText().toString());
                parametros.put("telefono",editText4.getText().toString());
                parametros.put("id_sector", id_sector.toString());
                return parametros;
            }
        };
        queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
    //metodo que permite salir de la actividad
    public void Salir (View view){
        finish();
    }

}
