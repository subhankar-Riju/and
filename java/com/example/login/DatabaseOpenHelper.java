package com.example.login;

import android.content.Context;
import Jama.Matrix;
import Jama.QRDecomposition;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME="test2.db";
    private static int  DATABASE_VERSION=1;
    public DatabaseOpenHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

}
