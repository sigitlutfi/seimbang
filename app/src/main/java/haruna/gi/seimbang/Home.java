package haruna.gi.seimbang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mehdi.sakout.fancybuttons.FancyButton;

public class Home extends AppCompatActivity{
    private Window mWindow;FirebaseAuth mAuth;
    FancyButton tambah,help,profil,play;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        mAuth = FirebaseAuth.getInstance();
        TextView pengguna = (TextView)findViewById(R.id.tt);
        pengguna.setText(mAuth.getCurrentUser().getDisplayName().toString());

        tambah = (FancyButton)findViewById(R.id.btambah);


        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,MainActivity.class));finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
