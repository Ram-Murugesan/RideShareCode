package com.example.ramkumar.rideshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    static Long loginMobileNum;

    private TextView registerUser,forgetPassword;
    private AutoCompleteTextView mobileNoEditText;
    private EditText passwordEditText;
    private Button signIn;

    DatabaseReference root,registerUserTable,index;
    private List<RowItemSelect> rowItemSelect;
    private Long regMobileNo;
    private String regCorrectPassword,loginEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mobileNoEditText=(AutoCompleteTextView) findViewById(R.id.mobileNo);
        passwordEditText=(EditText) findViewById(R.id.password);
        registerUser=(TextView) findViewById(R.id.registerUser);

        signIn=(Button) findViewById(R.id.signIn);

        root = FirebaseDatabase.getInstance().getReference();
        registerUserTable = root.child("Register");

        rowItemSelect=new ArrayList<RowItemSelect>();


        registerUserTable.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                regMobileNo = (Long) dataSnapshot.child("Mobile").getValue();
                loginEmail=(String) dataSnapshot.child("Email").getValue();
                regCorrectPassword=(String) dataSnapshot.child("Password").getValue();
                RowItemSelect item=new RowItemSelect( regMobileNo,loginEmail,regCorrectPassword);
                rowItemSelect.add(item);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                regMobileNo = (Long) dataSnapshot.child("Mobile").getValue();
                loginEmail=(String) dataSnapshot.child("Email").getValue();
                regCorrectPassword=(String) dataSnapshot.child("Password").getValue();
                RowItemSelect item=new RowItemSelect( regMobileNo,loginEmail,regCorrectPassword);
                rowItemSelect.add(item);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                regMobileNo = (Long) dataSnapshot.child("Mobile").getValue();
                loginEmail=(String) dataSnapshot.child("Email").getValue();
                regCorrectPassword=(String) dataSnapshot.child("Password").getValue();
                RowItemSelect item=new RowItemSelect( regMobileNo,loginEmail,regCorrectPassword);
                rowItemSelect.add(item);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean status = false;


                String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
                Boolean b = mobileNoEditText.getText().toString().matches(emailregex);

                if (b == false) {
                    System.out.println("Email Address is Invalid");
                    regMobileNo = Long.parseLong(mobileNoEditText.getText().toString());
                    regCorrectPassword = passwordEditText.getText().toString();


                    if (regMobileNo.toString().trim().equalsIgnoreCase("") || passwordEditText.toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(Login.this, "Enter all fields", Toast.LENGTH_SHORT).show();

                    } else if (regMobileNo.toString().length() == 10 && !(regMobileNo.toString().startsWith("9") || regMobileNo.toString().startsWith("8") || regMobileNo.toString().startsWith("7")))

                    {
                        System.out.println("regMobileNo.toString().length()" + regMobileNo.toString().length() + "2");
                        Toast.makeText(Login.this, "Invalid Mobile No", Toast.LENGTH_SHORT).show();
                    } else if (regMobileNo.toString().length() > 10 || (regMobileNo.toString().length() < 10))

                    {
                        System.out.println("regMobileNo.toString().length()" + regMobileNo.toString().length() + "2");
                        Toast.makeText(Login.this, "Invalid Mobile No", Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("ajay every login validation is checked");
                        for (int i = 0; i < rowItemSelect.size(); i++) {

                            if (regMobileNo.equals(rowItemSelect.get(i).getLoginMobileNo()) && regCorrectPassword.trim().equals(rowItemSelect.get(i).getPassword())) {
                                System.out.println(rowItemSelect.get(i).getLoginMobileNo() + rowItemSelect.get(i).getPassword());
                                status = true;
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("regMobileNo", regMobileNo);
                                //Login mobile number used in every activity
                                loginMobileNum = regMobileNo;
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();

                                startActivity(intent);
                                finish();
                            }
                        }
                        System.out.println("status" + status);
                        if (status == false) {


                            Toast.makeText(Login.this, "Login failed..Register if you haven't", Toast.LENGTH_LONG).show();


                        }
                    }
                } else if (b == true) {
                    System.out.println("Email Address is Valid");

                    loginEmail = mobileNoEditText.getText().toString();
                    regCorrectPassword = passwordEditText.getText().toString();


                    if (loginEmail.toString().trim().equalsIgnoreCase("") || passwordEditText.toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(Login.this, "Enter all fields", Toast.LENGTH_SHORT).show();

                    } else {
                        System.out.println("ajay every login validation is checked");
                        for (int i = 0; i < rowItemSelect.size(); i++) {

                            if (loginEmail.equals(rowItemSelect.get(i).getLoginEmail()) && regCorrectPassword.trim().equals(rowItemSelect.get(i).getPassword())) {
                                System.out.println(rowItemSelect.get(i).getLoginEmail() + rowItemSelect.get(i).getPassword());
                                status = true;
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("regMobileNo", rowItemSelect.get(i).getLoginMobileNo());
                                //Login mobile number used in every activity
                                loginMobileNum = rowItemSelect.get(i).getLoginMobileNo();
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();

                                startActivity(intent);
                                finish();
                            }
                        }
                        System.out.println("status" + status);
                        if (status == false) {


                            Toast.makeText(Login.this, "Login failed..Register if you haven't", Toast.LENGTH_LONG).show();


                        }
                    }
                }


            }

        });




        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Login.this,Registration.class);
                startActivity(i);

            }
        });
//        forgetPassword.setVisibility(View.INVISIBLE);

    }
}