package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class predActivity extends AppCompatActivity {
    int iCurrentSelection;
    Button click,btn_stcon,btn_distcon,btn_typcon;
    EditText yr;
    TextView tv_pred;
    String selected_dist,district_name,state_name,crime_name;
    int year;
    DatabaseAccess databaseAccess;
    Spinner sp_state,sp_dist,sp_crime;
    state_dist_array state_dist_array=new state_dist_array();
    double[] y=new double[14];

    String dist[];
    String crime[]={"Rape", "Kidnapping and Abduction", "Dowry Deaths", "Assault on women with intent to outrage her modesty", "Insult to modesty of Women", "Cruelty by Husband or his Relatives", "Importation of Girls", "Total Crime"};
    String state[]={"A & N ISLANDS","ANDHRA PRADESH","ARUNACHAL PRADESH","ASSAM","BIHAR","CHANDIGARH","CHHATTISGARH","PONDICHERRY","DAMAN & DIU","D & N HAVELI","GOA","GUJARAT","HARYANA","HIMACHAL PRADESH","JAMMU & KASHMIR","KARNATAKA","KERALA","LAKSHADWEEP","MADHYA PRADESH","MAHARASHTRA","MANIPUR","MEGHALAYA","MIZORAM","NAGALAND","ODISHA","PUNJAB","RAJASTHAN","SIKKIM","TAMIL NADU","TRIPURA","UTTAR PRADESH","UTTARAKHAND","WEST BENGAL"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pred);
        tv_pred=(TextView)findViewById(R.id.tv_pred);
        yr=(EditText)findViewById(R.id.et_year);
        click=(Button)findViewById(R.id.btn_check);
        sp_state=(Spinner)findViewById(R.id.sp_state);
        sp_dist=(Spinner)findViewById(R.id.sp_dist);
        sp_crime=(Spinner)findViewById(R.id.sp_crime);
        btn_stcon=(Button)findViewById(R.id.btn_stcon);
        btn_distcon=(Button)findViewById(R.id.btn_distcon);
        btn_typcon=(Button)findViewById(R.id.btn_typcon);

        //spinner crime
        ArrayList<String> arrayList_crime=new ArrayList<>(Arrays.asList(crime));
        final ArrayAdapter<String> arrayAdapter_crime=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,arrayList_crime);
        sp_crime.setAdapter(arrayAdapter_crime);

        sp_crime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                ozil2();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        // spinner state
        ArrayList<String> arrayList=new ArrayList<>(Arrays.asList(state));
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,arrayList);
        sp_state.setAdapter(arrayAdapter);

        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                ozil();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // button state confirm
        btn_stcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ozil();

            }
        });

        /*et=(EditText)findViewById(R.id.et_check);
         databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
         databaseAccess.open();
         Cursor cursor=databaseAccess.getAddress("ANDAMAN");
        databaseAccess.close();
         cursor.close();*/

        //final click
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year=Integer.parseInt(yr.getText().toString());
                //Toast.makeText(getApplicationContext(),district_name +" "+ crime_name+ " "+ state_name,Toast.LENGTH_LONG).show();
               databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                Cursor cursor=databaseAccess.getAddress(state_name,district_name,crime_name);
               int i=0;
                while(cursor.moveToNext()){
                    //Toast.makeText(getApplicationContext(),cursor.getString(3),Toast.LENGTH_LONG).show();
                    while(i<=13) {
                        y[i] = cursor.getDouble(i+2);
                        i++;
                    }
                }
                //double[] x={2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014};
                //Toast.makeText(getApplicationContext(),""+x.length +""+ y.length,Toast.LENGTH_LONG).show();*/
              //  double[] y = { 3,1,2,10,4,6,12,12,18,23,13,13,13,13};
                double[] x = { 1,2,3,4,5,6,7,8,9,10,11,12,13,14};
                PolynomialRegression regression = new PolynomialRegression(x, y, 3,year);
                double s=regression.method();
                if(s<0){
                    s=0;
                }
                tv_pred.setText(String.format("%.0f",s));
               // Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_LONG).show();

                cursor.close();
                databaseAccess.close();
            }
        });


        //button dist confirm
        btn_distcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ozil1();
            }
        });

        //button crime confirm
        btn_typcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ozil2();
            }
        });




    }

    public  void dis_dist(String[] dist){
        ArrayList<String> arrayList=new ArrayList<>(Arrays.asList(dist));
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,arrayList);
        sp_dist.setAdapter(arrayAdapter);
        sp_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ozil1();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void ozil(){
        dist=state_dist_array.select(sp_state.getSelectedItem().toString());
        state_name=sp_state.getSelectedItem().toString();
        dis_dist(dist);

    }

    public  void ozil1(){
        district_name=sp_dist.getSelectedItem().toString();

    }

    public  void ozil2(){
        crime_name=sp_crime.getSelectedItem().toString();

    }

    public  void pred(String state,String dist,String crime,int year){
        databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        Cursor cursor=databaseAccess.getAddress(state,dist,crime);
        int i=2;
        while(cursor.moveToNext()){

           while(i<=15) {
                y[i - 2] = cursor.getDouble(i);
              i++;
            }
        }
        double[] x={1,2,3,4,5,6,7,8,9,10,11,12,13,14};
        PolynomialRegression regression = new PolynomialRegression(x, y, 3,year);
        //String s=regression.method();
        //tv_pred.setText(s);
        cursor.close();
        databaseAccess.close();

    }



}
