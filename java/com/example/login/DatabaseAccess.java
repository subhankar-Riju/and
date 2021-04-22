package com.example.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;

    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c=null;
    private  DatabaseAccess(Context context){
        this.openHelper=new DatabaseOpenHelper(context);
    }
    public static DatabaseAccess getInstance(Context context){
        if(instance==null){
            instance=new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db=openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    public Cursor getAddress(String state,String dist,String crime){
        String s=new String();
        switch(crime){
            case "Rape":
                s="rp";
                break;
            case "Kidnapping and Abduction":
                s="ka";
                break;
            case "Dowry Deaths":
                s="dd";
                break;
            case "Assault on women with intent to outrage her modesty":
                s="aow";
                break;
            case "Cruelty by Husband or his Relatives":
                s="cbhor";
                break;
            case "Importation of Girls":
                s="iog";
                break;
            case "Total Crime":
                s="tc";
                break;
            case "Insult to modesty of Women":
                s="itmow";
                break;
        }



       Cursor c=db.rawQuery("select * from '"+s+"' where dist= '"+dist+"' and state= '"+state+"'",new String[]{});

        return c;


    }



    public Cursor getAddress3(String state,String dist){




        Cursor c=db.rawQuery("select * from tc where dist= '"+dist+"' and state= '"+state+"'",new String[]{});
        return c;




    }


    public Cursor getAddress2(String year, String crime,String state,String dist){
        String s=new String();
        switch(crime){
            case "Rape":
                s="rp";
                break;
            case "Kidnapping and Abduction":
                s="ka";
                break;
            case "Dowry Deaths":
                s="dd";
                break;
            case "Assault on women with intent to outrage her modesty":
                s="aow";
                break;
            case "Cruelty by Husband or his Relatives":
                s="cbhor";
                break;
            case "Importation of Girls":
                s="iog";
                break;
            case "Total Crime":
                s="tc";
                break;
            case "Insult to modesty of Women":
                s="itmow";
                break;
        }


        Cursor c=db.rawQuery("select '" +2001+"' from '"+s+"' where dist= '"+dist+"' and state= '"+state+"'",new String[]{});

        return c;


    }





}

