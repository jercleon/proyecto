package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class consulta_por_dia extends AppCompatActivity {

    private BarChart barChart;

    //FALTA PASAR LA SELECCION DEL DIA DE LA ACTITIVITY ANTERIOR
    //VALIDAR LA SELECCION EN LA BASE DE DATOS Y LLENAR LA TABLA
    final private String[] horas =new String[]{"18","19","20","21","22","23",
            "24","1","2","3","4","5","6"};

    private int[]valores=new int[]{10,25,30,27,16,44,21,15,28,22,24,
            30,25};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_por_dia);
        barChart=(BarChart)findViewById(R.id.lineChart);
        createCharts();
    }

    private Chart getSameChart(Chart chart, String descripcion, int textColor, int backgroung, int animateY){
        chart.getDescription().setText(descripcion);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(backgroung);
        chart.animateY(animateY);
        legend(chart);
        return chart;
    }

    private void legend(Chart chart){
        Legend legend=chart.getLegend();
        legend.setForm(Legend.LegendForm.DEFAULT);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries=new ArrayList<>();
        for(int i = 0; i< horas.length; i++){
            LegendEntry entry=new LegendEntry();
            entry.label= horas[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
    private ArrayList<BarEntry>getBarEntries(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0; i<valores.length;i++){
            entries.add(new BarEntry(i,valores[i]));
        }
        return entries;
    }
    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(horas));
    }

    private void axisLeft(YAxis axis){
        axis.setSpaceTop(35);
        axis.setAxisMinimum(0);
    }
    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }

    public void createCharts(){
        barChart=(BarChart)getSameChart(barChart, "Consumo por mes",
                Color.BLUE,Color.LTGRAY,3000);
        barChart.setDrawGridBackground(true);
        barChart.setData(getBarData());
        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
    }
    private DataSet getData(DataSet dataSet){

        dataSet.setValueTextSize(10);
        return dataSet;
    }
    private BarData getBarData(){
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries(),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }


    public void Salir (View view){
        finish();
    }
}
