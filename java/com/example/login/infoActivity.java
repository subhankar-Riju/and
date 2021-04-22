package com.example.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class infoActivity extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference();
    TextView name, fname, fmob;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Button submit,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        name = (EditText) findViewById(R.id.et_name);
        fname = (EditText) findViewById(R.id.et_fname);
        fmob = (EditText) findViewById(R.id.et_fmob);
        submit = (Button) findViewById(R.id.ad_ovr);
        back=(Button)findViewById(R.id.btn_back);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(root.child("users").child(mUser.getUid().toString()) != null){
                    overwrite();
                }
                else{
                    add();
                }






            }


        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(infoActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public  void overwrite(){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                infoActivity.this);

// Setting Dialog Title
        alertDialog2.setTitle("already you have filled");

// Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want overwrite?");

// Setting Icon to Dialog
        //alertDialog2.setIcon(R.drawable.delete);

// Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        String nm = name.getText().toString();
                        String fnm = fname.getText().toString();
                        String fmb = fmob.getText().toString();
                        root.child("users").child(mUser.getUid().toString()).child("name").setValue(nm);
                        root.child("users").child(mUser.getUid().toString()).child("fname").setValue(fnm);
                        root.child("users").child(mUser.getUid().toString()).child("fmob").setValue(fmb);

                    }
                });
// Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Toast.makeText(getApplicationContext(),
                                "thank you", Toast.LENGTH_SHORT)
                                .show();
                        dialog.cancel();
                    }
                });

// Showing Alert Dialog
        alertDialog2.show();


    }

    public  void  add(){
        String nm = name.getText().toString();
        String fnm = fname.getText().toString();
        String fmb = fmob.getText().toString();
        root.child("users").child(mUser.getUid().toString()).child("name").setValue(nm);
        root.child("users").child(mUser.getUid().toString()).child("fname").setValue(fnm);
        root.child("users").child(mUser.getUid().toString()).child("fmob").setValue(fmb);

    }
}
