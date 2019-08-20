package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class consumo_por_mes extends AppCompatActivity {
    private static String stringKey1 = "skey1";
    private String seleccion;
    public String mes;
    private TextView titulo;
    private String tit;
    private RequestQueue queue;


    private Spinner spinner;
    private LineChartView lineChartView;
    List yAxisValues = new ArrayList();
    List axisValues = new ArrayList();

    String[] dias =new String[]{"1","2","3","4","5","6","7","8","9","10",
            "11","12","13","14","15","16","17","18","19","20",
            "21","22","23","24","25","26","27","28","29","30","31"};
    Float[]valores=new Float[]{0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,
            6.3f,3.5f,6f,5.5f,4.6f,9.3f,8.7f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumo_por_mes);
        mes = getIntent().getStringExtra(Consultar.stringKey);
        //aqui se empieza a buscar el mes en la base de datos y se llenan los arreglos de las tablas
        tit = "Consumo de agua en " + mes;
        titulo = (TextView)findViewById(R.id.txt_titulo);
        titulo.setText(tit);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dias);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        lineChartView=(LineChartView)findViewById(R.id.lineChart);
        buscarProducto("http://192.168.0.106/consumo_mes.php?mes="+mes);
        try {
            Thread.sleep(7000);
            createchart();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void createchart(){
        Line line=new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));
        for(int i=0;i<dias.length;i++){
            axisValues.add(i, new AxisValue(i).setLabel(dias[i]));
        }
        for(int i=0;i<valores.length;i++){
            yAxisValues.add(i, new PointValue(i,valores[i]));
        }
        List lines = new ArrayList();
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        lineChartView.setLineChartData(data);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);
        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        yAxis.setName("Litros de agua consumida");
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top =20;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }
    public void tabla2(View view){
        seleccion = spinner.getSelectedItem().toString();
        lanzarAct(seleccion);
    }
    public void Salir (View view){
        finish();
    }

    private void lanzarAct(String val){
        Intent i = new Intent(this, consulta_por_dia.class);
        i.putExtra(stringKey1,val);
        startActivity(i);
    }



    private void buscarProducto(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for(int i = 0; i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        String fec = jsonObject.getString("fecha");
                        String[] dia = fec.split("-");
                        Float y = Float.parseFloat(jsonObject.getString("duracion_riego"));
                        //Toast.makeText(getApplicationContext(),valores[(Integer.parseInt(dia[2]))-1].toString(),Toast.LENGTH_SHORT).show();
                        valores[(Integer.parseInt(dia[2]))-1] += y;
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"error al leer datos",Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"NO HAY REGISTROS EN ESTE MES",Toast.LENGTH_SHORT).show();
            }
        });

        queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }

}
