package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class statActivity extends AppCompatActivity {
    Button st_btn,dist_btn,bo_btn,finalcon_btn,click;
    String district_name,state_name,crime_name,yr;

    Spinner sp_state,sp_dist,sp_bo,sp_final;

    state_dist_array state_dist_array=new state_dist_array();
    double[] y=new double[14];
    DatabaseAccess databaseAccess;

    String dist[];
    String crime[]={"Rape", "Kidnapping and Abduction", "Dowry Deaths", "Assault on women with intent to outrage her modesty", "Insult to modesty of Women", "Cruelty by Husband or his Relatives", "Importation of Girls", "TotalCrime"};
    String year[]={"2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014"};
    String state[]={"A & N ISLANDS","ANDHRA PRADESH","ARUNACHAL PRADESH","ASSAM","BIHAR","CHANDIGARH","CHHATTISGARH","PONDICHERRY","DAMAN & DIU","D & N HAVELI","GOA","GUJARAT","HARYANA","HIMACHAL PRADESH","JAMMU & KASHMIR","KARNATAKA","KERALA","LAKSHADWEEP","MADHYA PRADESH","MAHARASHTRA","MANIPUR","MEGHALAYA","MIZORAM","NAGALAND","ODISHA","PUNJAB","RAJASTHAN","SIKKIM","TAMIL NADU","TRIPURA","UTTAR PRADESH","UTTARAKHAND","WEST BENGAL"};
    String bo[]={"crime","year"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        //Button
        st_btn=(Button)findViewById(R.id.btn_stat_state);
        dist_btn=(Button)findViewById(R.id.btn_stat_dist);
        bo_btn=(Button)findViewById(R.id.btn_stat_bo);
        finalcon_btn=(Button)findViewById(R.id.btn_stat_finalcon);
        click=(Button)findViewById(R.id.btn_st_click);
        final BarChart barChart = (BarChart) findViewById(R.id.barchart);

        //spinner
        sp_state=(Spinner)findViewById(R.id.sp_stat_st);
        sp_dist=(Spinner)findViewById(R.id.sp_stat_dist);
        sp_bo=(Spinner)findViewById(R.id.sp_stat_bo);
        sp_final=(Spinner)findViewById(R.id.sp_stat_final);

        //spinner state

        ArrayList<String> arrayList_state=new ArrayList<>(Arrays.asList(state));
        final ArrayAdapter<String> arrayAdapter_state=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,arrayList_state);
        sp_state.setAdapter(arrayAdapter_state);

        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                call1();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Button state confirm

        st_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call1();
            }
        });

        //spinner for based on
        ArrayList<String> arrayList_bo=new ArrayList<>(Arrays.asList(bo));
        final ArrayAdapter<String> arrayAdapter_bo=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,arrayList_bo);
        sp_bo.setAdapter(arrayAdapter_bo);

        sp_bo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                call3();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //button based on

        bo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call3();
            }
        });

        //final confirm
        finalcon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call3();
            }
        });

        //click
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yr.equals("")){

                    databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();
                    Cursor cursor=databaseAccess.getAddress(state_name,district_name,"Total Crime");
                    int i=0;
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    while(cursor.moveToNext()){

                        while(i<=13){
                            entries.add(new BarEntry(cursor.getInt(i+2), i));
                            i++;
                        }
                    }
                    //Toast.makeText(getApplicationContext(),cursor.getColumnNames().toString(),Toast.LENGTH_SHORT).show();
                    BarDataSet bardataset = new BarDataSet(entries, "Cells");
                    ArrayList<String> labels = new ArrayList<String>();

                    for(int j=0;j<=13;j++){
                        String s="";
                        s=s+(2001+j);
                        labels.add(s);
                        s="";
                    }
                    BarData data = new BarData(labels, bardataset);
                    barChart.setData(data); // set the data and list of labels into chart
                    barChart.setDescription("Set Bar Chart Description Here");  // set the description
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    barChart.animateY(5000);
                    cursor.close();
                    databaseAccess.close();



                }
                // yr got
                //run recursive querry to fetch the crime numbers gainsty the yr
                else {


                    databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    for (int i = 0; i < crime.length; i++) {

                        Cursor cursor = databaseAccess.getAddress2(yr, crime[i], state_name, district_name);
                       /* while (cursor.moveToNext()) {
                            Toast.makeText(getApplicationContext(), crime[i], Toast.LENGTH_SHORT).show();

                            entries.add(new BarEntry(cursor.getInt(0), i));

                        }*/


                    }

                   /* BarDataSet bardataset = new BarDataSet(entries, "Cells");
                    ArrayList<String> labels = new ArrayList<String>();

                    for(int j=0;j<=13;j++){
                        String s="";
                        s=s+(2001+j);
                        labels.add(s);
                        s="";
                    }
                    BarData data = new BarData(labels, bardataset);
                    barChart.setData(data); // set the data and list of labels into chart
                    barChart.setDescription("Set Bar Chart Description Here");  // set the description
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    barChart.animateY(5000);

                    Toast.makeText(getApplicationContext(),entries.toString(),Toast.LENGTH_LONG).show();
*/


                }
            }
        });





    }

    public void call1(){
        dist=state_dist_array.select(sp_state.getSelectedItem().toString());
        state_name=sp_state.getSelectedItem().toString();
        dis_dist(dist);

    }
    public  void dis_dist(String[] dist){
        ArrayList<String> arrayList=new ArrayList<>(Arrays.asList(dist));
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,arrayList);
        sp_dist.setAdapter(arrayAdapter);
        sp_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                call2();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void call2(){
        district_name=sp_dist.getSelectedItem().toString();

    }

    public void call3(){
        if(sp_bo.getSelectedItem().toString().equals("crime")){

            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(crime));
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arrayList);
            sp_final.setAdapter(arrayAdapter);
            sp_final.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   call4();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else{
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(year));
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arrayList);
            sp_final.setAdapter(arrayAdapter);
            sp_final.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   // call2();
                    call5();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }


    }
    public void call4(){
        crime_name=sp_final.getSelectedItem().toString();
        yr="";
    }

    public void call5(){
        yr=sp_final.getSelectedItem().toString();
        crime_name="";
    }
}
