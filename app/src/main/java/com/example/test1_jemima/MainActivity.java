package com.example.test1_jemima;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//CHANGE 'MainActivty' to 'ImongTitleAniNgaClass'
public class MainActivity extends AppCompatActivity {

    //DECLARE VARIABLES
    private EditText lName, fName, mName, email, pNum, password, age, address;
    private RadioGroup gender;
    private Spinner municipality;

    //DECLARE VARIABLE FOR BIRTHDATE
    private Button bDate;

    //FOR DATE PICKER (PARA MAG SELECT SILA SA ILANG DATE SA ILANG bDate)
    private DatePickerDialog picker, dateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ASSIGN AND CALL THE VARIABLES FROM ASSIGNED @id SA XML
        lName = findViewById(R.id.lastname);
        fName = findViewById(R.id.firstname);
        mName = findViewById(R.id.MiddleName);
        email = findViewById(R.id.email);
        pNum = findViewById(R.id.Mobile);
        password = findViewById(R.id.Password);
        age = findViewById(R.id.Age);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.gender);
        /*PARA SA BIRTHDATE - PERO KUNG GUSTO KA TEXT LANG OR MAG TYPE2 NALANG SILA.
          E REMOVE NALANG NING 'bDate();'
          APILA PUD ning 'private void bDate()*/
        bDate();


        //municipality selection
        //ASSIGN XML TO JAVA
        municipality =  findViewById(R.id.municipality);
        //GET LIST FROM STRING.XML
        ArrayAdapter<CharSequence> adapterCities = ArrayAdapter.createFromResource(this, R.array.municipality, android.R.layout.simple_spinner_item);
        //PARA MO FUNCTION ANG SPINNER
        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //SELECTED LIST
        municipality.setAdapter(adapterCities);


        //DECLARE NEXT BUTTON
        Button next_button = findViewById(R.id.next);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstname = fName.getText().toString().trim();
                final String lastname = lName.getText().toString().trim();
                final String middlename = mName.getText().toString().trim();
                final String phoneNumber = pNum.getText().toString().trim();
                final String u_email = email.getText().toString().trim();
                final String u_password = password.getText().toString().trim();
                final String u_age = age.getText().toString().trim(); // for database reference only.
                final String u_address = address.getText().toString().trim();
                final String birthdate = bDate.getText().toString();
                //SCANNING GENDER or IDENTIFYING SELECTED RADIO - this is for database reference
                int checkedGENDER = gender.getCheckedRadioButtonId();
                RadioButton selectedGENDER = gender.findViewById(checkedGENDER);
                //READING MUNICIPALITY FROM SPINNER
                String u_municipality = municipality.getSelectedItem().toString();



                //MGA CONDITIONS NA DARI INCASE KUNG WALA NA FILL IN TANAN SA USER ANG FORM
                if (TextUtils.isEmpty(firstname)){
                    Toast.makeText(MainActivity.this, "Please enter your firstname!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(middlename)){
                    Toast.makeText(MainActivity.this, "Please enter your middlename!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(lastname)){
                    Toast.makeText(MainActivity.this, "Please enter your lastname!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(u_email)){
                    Toast.makeText(MainActivity.this, "Please enter your email!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(u_password)){
                    Toast.makeText(MainActivity.this, "Please enter your password!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(MainActivity.this, "Please enter your phone number!", Toast.LENGTH_LONG).show();
                }else if (birthdate.equals("SELECT DATE")){
                    Toast.makeText(MainActivity.this, "Please select your birthdate!", Toast.LENGTH_LONG).show();
                }else if (selectedGENDER == null) {
                    Toast.makeText(MainActivity.this, "Please select a gender!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(u_address)){
                    Toast.makeText(MainActivity.this, "Please enter your address!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(u_municipality)){
                    Toast.makeText(MainActivity.this, "Please select your municipality!", Toast.LENGTH_LONG).show();
                } else {
                    //E CHANGE LANG NING NAME SA CLASS FROM '"PAGE2".class' TO '"bagongaclass".class'
                    //E CHANGE PUD NING 'MainActivity.class' TO 'imongclass.class'
                    startActivity(new Intent(MainActivity.this, PAGE2.class));
                }
            }
        });

    }

    //PRIVATE VOIDS
    private void bDate() {
        /*
          ANG KANING 'bDate' IS E INVISIBLE LANG NI SA XML.
          ANG PURPOSE NGA E INVISIBLE SYA PARA DARA MAG KUHAG REFERENCE ANG DATABASE.
          KAY DILI MAN PWEDE MA KUHA ANG REFERENCE SA BUTTON.
        */

        bDate = (Button) findViewById(R.id.btn_bDate);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dateDialog = new DatePickerDialog(view.getContext(), datePickerListener, year, month, day);
                dateDialog.getDatePicker().setMaxDate(new Date().getTime());
                dateDialog.show();
            }
        });



    }
    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            age.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
            month = month+1;
            String birthdate = month+"/"+day+"/"+year;
                        /*
                          ANG KANING 'bDate_Button.setHint(birthdate)' DARI MA CHANGE TONG TEXT
                          FROM "BUTTON NGA WORD" TO "ILANG BIRTHDATE"
                        */
            bDate.setText(birthdate);

        }
    };
    int calculateAge(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;
    }
}