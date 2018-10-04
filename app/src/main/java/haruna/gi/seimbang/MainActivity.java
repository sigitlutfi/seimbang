package haruna.gi.seimbang;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import faranjit.currency.edittext.CurrencyEditText;
import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomSheetListener, OnMapReadyCallback {
    private static final String TAG = MainActivity.class.getSimpleName();
    String a,pagu,rpx;
    int md,xpagu;
    NumberFormat formatRupiah;
    FancyButton dbmenu,dbkat;
    FirebaseAuth mAuth;
    TextView h;
    MaterialEditText currencyEditText;
    BottomSheet share;
    Intent intent;
    int katsheet=R.menu.list_k1;
    ImageView btnimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home);
        findViewById(R.id.bmenu).setOnClickListener(this);
        findViewById(R.id.bkat).setOnClickListener(this);
        findViewById(R.id.bsubmit).setOnClickListener(this);
        //h = (TextView)findViewById(R.id.textView);
        dbmenu = (FancyButton)findViewById(R.id.bmenu);
        dbkat = (FancyButton)findViewById(R.id.bkat);
        dbkat.setEnabled(false);
        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        currencyEditText = (MaterialEditText) findViewById(R.id.pagu);
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("", R.drawable.home, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("", R.drawable.add, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("", R.drawable.list, R.color.colorAccent);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        bottomNavigation.setCurrentItem(1);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                String position1 = String.valueOf(position);

                Toast.makeText(getApplicationContext(), position1, Toast.LENGTH_SHORT).show();
                if (position == 2){
                    mAuth.signOut();
                }else if (position == 0){

                    startActivity(new Intent(MainActivity.this,Home.class));
                }
                return true;
            }
        });

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.imageButton).setOnClickListener((View view) -> {
            Pix.start(MainActivity.this, 100, 5);
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(MainActivity.this, currentUser.getDisplayName(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(-7.812800, 110.377146);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(MainActivity.this, 100, 5);
                } else {
                    Toast.makeText(MainActivity.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bmenu:

                new BottomSheet.Builder(this,R.style.MyBottomSheetStyle)
                        .setSheet(R.menu.list_sheet)
                        .object("menu")
                        .setListener(this)
                        .setTitle("Pilih Menu")
                        .show();

                break;
            case R.id.bkat:
                new BottomSheet.Builder(this)
                        .setSheet(md)
                        .object("kat")
                        .setListener(this)
                        .setTitle("Pilih Kategori")
                        .show();

                break;
            case R.id.bsubmit:
                Intent i = new Intent(MainActivity.this, Home.class);
                startActivity(i);
        }
    }

    @Override
    public void onSheetShown(@NonNull BottomSheet bottomSheet, @Nullable Object object) {
        Log.v(TAG, "onSheetShown with Object " + object);
        //Toast.makeText(getApplicationContext(),  " Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem item, @Nullable Object object) {
        //Toast.makeText(getApplicationContext(), item.getTitle() + " Clicked", Toast.LENGTH_SHORT).show();
        a=String.valueOf(item.getTitle());
        String mda=String.valueOf(item.getTitleCondensed());
        if (mda.equals("ma")){
            md=R.menu.list_k1;
        }else if (mda.equals("mb")){
            md=R.menu.list_k2;
        }else if (mda.equals("mc")){
            md=R.menu.list_k3;
        }else if (mda.equals("md")){
            md=R.menu.list_k4;
        }
        if (object == "menu"){
            dbmenu.setText(a);
            dbkat.setEnabled(true);
        }else {
            dbkat.setText(a);
        }
    }

    @Override
    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @Nullable Object object, @DismissEvent int dismissEvent) {
        Log.v(TAG, "onSheetDismissed " + dismissEvent);


        switch (dismissEvent) {
            case BottomSheetListener.DISMISS_EVENT_BUTTON_POSITIVE:
                //Toast.makeText(getApplicationContext(), "Positive Button Clicked", Toast.LENGTH_SHORT).show();
                break;

            case BottomSheetListener.DISMISS_EVENT_BUTTON_NEGATIVE:
               // Toast.makeText(getApplicationContext(), "Negative Button Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {
                   // ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                   // myAdapter.addImage(returnValue);
                    /*for (String s : returnValue) {
                        Log.e("val", " ->  " + s);
                    }*/
                }
            }
            break;
        }
    }



}
