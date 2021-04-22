package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {




    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference();
    private GoogleSignInClient mGoogleSignInClient;


    Button add,pred,stat,ck,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=(Button)findViewById(R.id.add_info);
        pred=(Button)findViewById(R.id.btn_pred);
        stat=(Button)findViewById(R.id.btn_stat);
        ck=(Button)findViewById(R.id.check);
        logout=(Button)findViewById(R.id.logout_btn);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        ck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,fused_location.class);
                startActivity(i);
                finish();
            }
        });


        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,statActivity.class);
                startActivity(i);
                finish();
            }
        });


        pred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,predActivity.class);
                startActivity(i);
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,infoActivity.class);
                startActivity(i);
                finish();
                //Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT).show();
            }
        });
       logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mAuth=FirebaseAuth.getInstance();
               mUser=mAuth.getCurrentUser();
               mAuth.signOut();
               // Configure Google Sign In

               try {
                   GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                           .requestIdToken(getString(R.string.default_web_client_id))
                           .requestEmail()
                           .build();
                   // [END config_signin]

                   mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                   mGoogleSignInClient.signOut();
               }
               catch (Exception e)
               {
                   Log.i("Logout",e.getMessage());
               }
               Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               finish();
           }


       });

  /*      try{
            if(mUser!=null) {//Checking if user is actually present
                name.setText(mUser.getEmail());
                            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                // Configure Google Sign In

                try {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();
                    // [END config_signin]

                    mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                    mGoogleSignInClient.signOut();
                }
                catch (Exception e)
                {
                    Log.i("Logout",e.getMessage());
                }
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

*/


    }


}