package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText editText, editText3;
    String skey = "skey";
    Button boton;
    private RequestQueue queue;
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        editText3 = (EditText) findViewById(R.id.editText3);
        boton = (Button) findViewById(R.id.button4);



        if (editText.getText().toString().isEmpty()){
            Toast.makeText(this,"Por favor, ingresar un campo válido en email",Toast.LENGTH_LONG).show();
        }else{
            if (editText3.getText().toString().isEmpty()){
                Toast.makeText(this,"Por favor, ingresar un campo válido en password",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"BIENVENIDO A HIDROSMART",Toast.LENGTH_LONG).show();
            }
        }


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarProducto("http://192.168.43.6/login.php?correo=" + editText.getText() + "&password=" + editText3.getText() + "");
                if (id != null) {
                    Inicio(id);
                }
            }
        });
    }

    public void Inicio(String val) {
        id = null;
        Intent i = new Intent(this, Inicio.class);
        i.putExtra(skey, val);
        startActivity(i);
    }

    public void crearCuenta(View view) {
        Intent i = new Intent(this, CrearCuenta.class);
        startActivity(i);
    }

    private void buscarProducto(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        id = jsonObject.getString("id");
                    } catch (JSONException e) {
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "CREDENCIALES INCORRECTAS", Toast.LENGTH_SHORT).show();
            }
        });
        queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }
    public void salir(View view) {
       finish();
    }

}