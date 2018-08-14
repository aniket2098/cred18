package com.asdsoft.reg_app_18;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        final Spinner spinner = (Spinner) findViewById(R.id.college);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colleges_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);



        final EditText name=findViewById(R.id.name);
        final EditText name2=findViewById(R.id.name2);
        final EditText name3=findViewById(R.id.name3);
        final EditText name4=findViewById(R.id.name4);

        final EditText email=findViewById(R.id.email);
        final EditText phone=findViewById(R.id.ph_no);
        final EditText college=findViewById(R.id.name4);

        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {              //BUTTON ON-CLICK
            @Override
            public void onClick(View view) {
                final String contestantName=name.getText().toString().trim();       //NAME
                final String contestantEmail=email.getText().toString().trim();     //EMAIL
                final String contestantPhone=phone.getText().toString().trim();     //PHONE
                final String contestantName2=name2.getText().toString().trim();
                final String contestantName3=name3.getText().toString().trim();
                final String contestantName4=name4.getText().toString().trim();
                final String contestantCollege=spinner.getSelectedItem().toString();
                if(!TextUtils.isEmpty(contestantName)&&
                        !TextUtils.isEmpty(contestantEmail)&&
                        !TextUtils.isEmpty(contestantPhone))
                {
                    if( Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches())
                    {
                        if( android.util.Patterns.PHONE.matcher(phone.getText()).matches())         //SEND INFORMATION TO SERVER AFTER THIS
                        {
                            Intent intent = new Intent(MainActivity.this, Register.class);
                            intent.putExtra("name",contestantName);
                            intent.putExtra("name2",contestantName2);
                            intent.putExtra("name3",contestantName3);
                            intent.putExtra("name4",contestantName4);
                            intent.putExtra("email",contestantEmail);
                            intent.putExtra("phone",contestantPhone);
                            intent.putExtra("college",contestantCollege);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(MainActivity.this, "Invalid Phone no.!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(MainActivity.this, "Invaid Email!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "All * marked fields are mandatory!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.register) {
//            Intent intent = new Intent(MainActivity.this, Prev.class);
//            startActivity(intent);
        }
        else if (id == R.id.bug) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}