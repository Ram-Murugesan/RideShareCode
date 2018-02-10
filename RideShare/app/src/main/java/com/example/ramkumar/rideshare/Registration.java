package com.example.ramkumar.rideshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Registration extends AppCompatActivity {

    DatabaseReference root,registerUser,index;
    private MaterialBetterSpinner dd,mm,yy;

    private Button register,refreshCaptcha;

    private EditText name,mobileNo,email,password,correctPassword,dob,captcha;
    private String regName,regEmail,regPassword,regCorrectPassword,regDob,indexKey;
    private Long regMobileNo;




    private String[] monthList={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    public List<String> dateArrayList,yearArrayList;

    private RadioButton male,female,others;
    private String check;
    private List<RowItemSelect> rowItemSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        root = FirebaseDatabase.getInstance().getReference();
        registerUser = root.child("Register");

        rowItemSelect=new ArrayList<RowItemSelect>();

        register = (Button) findViewById(R.id.register);
        name = (EditText) findViewById(R.id.name);
        mobileNo = (EditText) findViewById(R.id.mobileNo);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        correctPassword = (EditText) findViewById(R.id.correctPassword);
        dob = (EditText) findViewById(R.id.dob);

        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        others = (RadioButton) findViewById(R.id.others);

        dd=(MaterialBetterSpinner) findViewById(R.id.dd);
        mm=(MaterialBetterSpinner) findViewById(R.id.mm);
        yy=(MaterialBetterSpinner) findViewById(R.id.yy);

        loadSpinnerData();

        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                check = "male";

            }
        });

        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                check = "female";

            }
        });

        others.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                check = "others";

            }
        });
        registerUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                regMobileNo = (Long) dataSnapshot.child("Mobile").getValue();

                RowItemSelect item=new RowItemSelect( regName, regMobileNo, regEmail, regPassword, regDob, check);
                rowItemSelect.add(item);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                regMobileNo = (Long) dataSnapshot.child("Mobile").getValue();

                RowItemSelect item=new RowItemSelect( regName, regMobileNo, regEmail, regPassword, regDob, check);
                rowItemSelect.add(item);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                regMobileNo = (Long) dataSnapshot.child("Mobile").getValue();

                RowItemSelect item=new RowItemSelect( regName, regMobileNo, regEmail, regPassword, regDob, check);
                rowItemSelect.add(item);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        password.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                regPassword = password.getText().toString();
                int passlen = regPassword.length();
                if(passlen==0){
                    //   Toast.makeText(Registeration.this, "Enter the password", Toast.LENGTH_SHORT).show();
                }
                else if(passlen>0 && passlen<5){
                    Toast.makeText(Registration.this, "Weak password...", Toast.LENGTH_SHORT).show();
                }
                else if(passlen>=5 && passlen<=7){
                    //  Toast.makeText(Registeration.this, "Normal password...", Toast.LENGTH_SHORT).show();
                }
                else if(passlen>=8) {
                    Toast.makeText(Registration.this, "Strong password...", Toast.LENGTH_SHORT).show();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){

            }

        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean status=false;
                Boolean mobileNoCheck = true;
               /*
                * This EventListener is used to check wether mobileNo is in the database
                * Mobile Number acts as the Primary Key for the Database
                * Mobile Number is used in all the classes
                */

                regName = name.getText().toString();
                regMobileNo = Long.parseLong(mobileNo.getText().toString());
                regEmail = email.getText().toString();
                regPassword = password.getText().toString();
                regCorrectPassword=correctPassword.getText().toString();
                regDob=dd.getText()+"/"+mm.getText()+"/"+yy.getText();



                String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
                Boolean b = regEmail.matches(emailregex);

                if (b == false) {
                    System.out.println("Email Address is Invalid");
                }else if(b == true){
                    System.out.println("Email Address is Valid");
                    mobileNoCheck = false;
                }

                /* This Loop is used
                 * To check weather the user is already registered or not */
                System.out.println("regMobileNo.toString().length()"+regMobileNo.toString().length()+""+(regMobileNo.toString().startsWith("9") || regMobileNo.toString().startsWith("8") || regMobileNo.toString().startsWith("7") ));
                if(registerUser.toString().trim().equalsIgnoreCase("") || regMobileNo.toString().trim().equalsIgnoreCase("") || regEmail.toString().trim().equalsIgnoreCase("")
                        || regPassword.toString().trim().equalsIgnoreCase("") || regDob.toString().trim().equalsIgnoreCase("") || check.toString().trim().equalsIgnoreCase(""))
                {
                    Toast.makeText(Registration.this, "Enter all fields", Toast.LENGTH_SHORT).show();

                }

                else if(!(regPassword.trim().equals(regCorrectPassword.trim())))
                {
                    password.setText("");
                    correctPassword.setText("");
                    Toast.makeText(Registration.this, "Enter correct Password", Toast.LENGTH_SHORT).show();

                }
                else if(regMobileNo.toString().length()==10 && !(regMobileNo.toString().startsWith("9") || regMobileNo.toString().startsWith("8") || regMobileNo.toString().startsWith("7") ))

                {
                    System.out.println("regMobileNo.toString().length()"+regMobileNo.toString().length()+"2");
                    Toast.makeText(Registration.this, "Invalid Mobile No", Toast.LENGTH_SHORT).show();
                }
                else if(regMobileNo.toString().length()>10 || (regMobileNo.toString().length()< 10 ) )

                {
                    System.out.println("regMobileNo.toString().length()"+regMobileNo.toString().length()+"2");
                    Toast.makeText(Registration.this, "Invalid Mobile No", Toast.LENGTH_SHORT).show();
                }
                else if (b == false) {
                    Toast.makeText(Registration.this, "Email Address is Invalid", Toast.LENGTH_SHORT).show();

                }
                else if(regCorrectPassword!="")
                {
                    int passlen = regCorrectPassword.length();
                    if(passlen==0){
                        Toast.makeText(Registration.this, "Enter the password", Toast.LENGTH_SHORT).show();
                    }
                    else if(passlen>0 && passlen<5){
                        Toast.makeText(Registration.this, "Weak password... Enter Strong password", Toast.LENGTH_SHORT).show();
                    }
                    else if(passlen>=5 && passlen<=7){
                        Toast.makeText(Registration.this, "Normal password... Enter Strong password", Toast.LENGTH_SHORT).show();
                    }
                    else if(passlen>=8) {
                        Toast.makeText(Registration.this, "Strong", Toast.LENGTH_SHORT).show();
                        for(int i=0; i<rowItemSelect.size();i++){

                            if (regMobileNo.equals(rowItemSelect.get(i).getRegMobileNo())) {
                                System.out.println(rowItemSelect.get(i).getRegMobileNo());
                                status = true;
                                Toast.makeText(Registration.this, "Already Registered", Toast.LENGTH_LONG).show();

                            }
                        }
                        System.out.println("status" + status);
                        if (status == false) {
                            index = registerUser.push();

                            //OTP Generation Code
                            List<Integer> numbers = new ArrayList<>();
                            for(int i = 0; i < 10; i++){
                                numbers.add(i);
                            }

                            Collections.shuffle(numbers);

                            String OTPNumber = "";
                            for(int i = 0; i < 6; i++){
                                OTPNumber += numbers.get(i).toString();
                            }
                            //send notification code


                            Map<String, Object> map1 = new HashMap<String, Object>();

                            map1.put("Name", regName);
                            map1.put("Mobile", regMobileNo);
                            map1.put("Email", regEmail);
                            map1.put("Password", regPassword);
                            map1.put("DOB", regDob);
                            map1.put("Gender", check);
                            map1.put("indexKey", index.getKey());
                            index.updateChildren(map1);

                            Intent intent = new Intent(Registration.this, MainActivity.class);
                            intent.putExtra("regMobileNo",regMobileNo);
                            Toast.makeText(Registration.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();

                        }
                    }
                }
                else
                {
                    System.out.println("ram");

                }



            }

        });


    }
    /*private void sendNotification(String title, String remoteMessage) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        System.out.println(title + "\n" + remoteMessage);

        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage))
                .setContentTitle(title)
                .setContentText(remoteMessage)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());

    }*/
    private void loadSpinnerData() {
        dateArrayList=new ArrayList<String>();
        yearArrayList=new ArrayList<String>();
        for(int i=1;i<=31;i++)
        {

            dateArrayList.add(i+"");
        }
        for(int i=1950;i<=2050;i++)
        {
            yearArrayList.add(i+"");
        }
        String[] dateList=new String[dateArrayList.size()] ;
        String[] yearList =new String[yearArrayList.size()] ;

        dateList=dateArrayList.toArray(dateList);
        yearList=yearArrayList.toArray(yearList);
        ArrayAdapter<String> unitAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, dateList);
        dd.setAdapter(unitAdapter1);
        ArrayAdapter<String> unitAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, monthList);
        mm.setAdapter(unitAdapter2);
        ArrayAdapter<String> unitAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, yearList);
        yy.setAdapter(unitAdapter3);

    }
}
