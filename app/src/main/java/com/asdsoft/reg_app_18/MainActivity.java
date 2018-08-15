package com.asdsoft.reg_app_18;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class    MainActivity extends AppCompatActivity
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

        SharedPreferences prefs = getSharedPreferences("cllgName", MODE_PRIVATE);
            int pos = prefs.getInt("cllgName", 0); //0 is the default value.
            spinner.setSelection(pos);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
//                String selectedItem = parent.getItemAtPosition(position).toString(); //this is your selected item
                if(spinner.getSelectedItem().toString().equals("Other…"))
                    findViewById(R.id.other).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.other).setVisibility(View.GONE);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        final EditText name=findViewById(R.id.name);
        final EditText name2=findViewById(R.id.name2);
        final EditText name3=findViewById(R.id.name3);
        final EditText name4=findViewById(R.id.name4);

        final EditText email=findViewById(R.id.email);
        final EditText phone=findViewById(R.id.ph_no);
        final EditText college=findViewById(R.id.name4);
        final CheckBox checkBox=findViewById(R.id.ieee);
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
                String tempString=null;
                if(spinner.getSelectedItem().toString().equals("Other…"))
                {
                    tempString=((EditText)findViewById(R.id.other)).getText().toString();;
                }
                else
                {
                    tempString=spinner.getSelectedItem().toString();
                }
                final String contestantCollege=tempString;
                final Boolean ieeeMember=checkBox.isChecked();
                if(!TextUtils.isEmpty(contestantName)&&
                        !TextUtils.isEmpty(contestantEmail)&&
                        !TextUtils.isEmpty(contestantPhone))
                {
                    if( Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches())
                    {
                        if( android.util.Patterns.PHONE.matcher(phone.getText()).matches()&&
                                contestantPhone.length()==10 &&
                                (contestantPhone.charAt(0)=='6' ||
                                        contestantPhone.charAt(0)=='7' ||
                                        contestantPhone.charAt(0)=='8' ||
                                        contestantPhone.charAt(0)=='9'))         //SEND INFORMATION TO SERVER AFTER THIS
                        {
                            if(!contestantCollege.equals("Select Visiting College")&&!contestantCollege.isEmpty())
                            {
                                SharedPreferences.Editor editor = getSharedPreferences("cllgName", MODE_PRIVATE).edit();
                                editor.putInt("cllgName", spinner.getSelectedItemPosition());
                                editor.apply();
                                Intent intent = new Intent(MainActivity.this, Register.class);
                                intent.putExtra("name",contestantName);
                                intent.putExtra("name2",contestantName2);
                                intent.putExtra("name3",contestantName3);
                                intent.putExtra("name4",contestantName4);
                                intent.putExtra("email",contestantEmail);
                                intent.putExtra("phone",contestantPhone);
                                intent.putExtra("college",contestantCollege);
                                intent.putExtra("ieee",ieeeMember);
                                startActivity(intent);
                                finish();
                            }
                            else
                                Toast.makeText(MainActivity.this, "Invalid College Name!", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(MainActivity.this, Prev.class);
            startActivity(intent);
        }
        else if (id == R.id.bug) {
            Intent intent = new Intent(MainActivity.this, Bug.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}