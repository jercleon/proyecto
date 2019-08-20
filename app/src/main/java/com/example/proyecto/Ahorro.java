package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class Ahorro extends AppCompatActivity {

    private PieChart pieChart;
    final private String[] horas =new String[]{"Sistema tradicional","Sistema automatizado"};
    private int[] colors=new int[]{Color.GREEN,Color.YELLOW};
    private int[]valores=new int[]{10,25};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahorro);
        pieChart=(PieChart)findViewById(R.id.pieChart);
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
            entry.formColor = colors[i];
            entry.label= horas[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
    private ArrayList<PieEntry>getBarEntries(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(int i=0; i<valores.length;i++){
            entries.add(new PieEntry(valores[i]));
        }
        return entries;
    }

    public void createCharts(){
        pieChart=(PieChart)getSameChart(pieChart,"Ahorro mensual",Color.GRAY,Color.CYAN,3000);
        pieChart.setHoleRadius(2);
        pieChart.setData(getBarData());
        pieChart.invalidate();
    }
    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colors);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }
    private PieData getBarData(){
        PieDataSet pieDataSet=(PieDataSet) getData(new PieDataSet(getBarEntries(),""));
        pieDataSet.setSliceSpace(1);
        pieDataSet.setValueFormatter(new PercentFormatter());

        return new PieData(pieDataSet);
    }

    public void Salir (View view){
        finish();
    }
}
