package haruna.gi.seimbang;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

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
        play   = (FancyButton)findViewById(R.id.bplay);
        SpeedDialView speedDialView = findViewById(R.id.speedDial);
        speedDialView.setMainFabClosedBackgroundColor(Color.parseColor("#FFFFFF"));
        speedDialView.setMainFabOpenedBackgroundColor(Color.parseColor("#FFFFFF"));
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.shome, R.drawable.home)
                        .setFabBackgroundColor(Color.parseColor("#FFFFFF"))
                        .setFabImageTintColor(Color.parseColor("#96a0d1"))
                        .setLabel("Home")
                        .setLabelColor(Color.parseColor("#96a0d1"))
                        .setLabelBackgroundColor(Color.parseColor("#FFFFFF"))
                        .setLabelClickable(true)
                        .create()
        );
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.stambah, R.drawable.add)
                        .setFabBackgroundColor(Color.parseColor("#FFFFFF"))
                        .setLabel("Buat")
                        .setLabelColor(Color.parseColor("#d81b60"))
                        .setLabelBackgroundColor(Color.parseColor("#FFFFFF"))
                        .setLabelClickable(true)
                        .create()
        );
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.sprofil, R.drawable.list)
                        .setFabBackgroundColor(Color.parseColor("#FFFFFF"))
                        .setFabImageTintColor(Color.parseColor("#50bb72"))
                        .setLabel("Profil")
                        .setLabelColor(Color.parseColor("#50bb72"))
                        .setLabelBackgroundColor(Color.parseColor("#FFFFFF"))
                        .setLabelClickable(true)
                        .create()
        );

        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.stambah:
                        startActivity(new Intent(Home.this,MainActivity.class));finish();
                        return false;// true to keep the Speed Dial open
                    case R.id.shome:
                        startActivity(new Intent(Home.this,Home.class));finish();
                        return false;
                    case R.id.sprofil:
                        View view = findViewById(R.layout.dashboard);
                        Snackbar mSnackbar = Snackbar.make(findViewById(R.id.dashboard_layout),"wadaw", Snackbar.LENGTH_SHORT);
                        mSnackbar.show();
                    default:
                        return false;
                }
            }
        });
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,MainActivity.class));finish();
            }
        });
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=haruna.gi.seimbang"));
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
