package com.example.ramkumar.rideshare;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Book extends AppCompatActivity implements View.OnClickListener {

    private String[] cartype = {"4 Passenger","6 Passenger"};
    private String[] durationList={"1 hour","2 hours","3 hours","4 hours","5 hours"};
    private MaterialBetterSpinner carType;
    private Button confirm,cancel;

    private EditText fromLocation,toLocation,distance;
    private TextView checkTariff;
    private DateFormat time = new SimpleDateFormat("HH:mm:ss");
    private DateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");

    private List<RowItemSelect> rowItemSelect;
    private Long regMobileNo;
    private String from, to, distanceIntent;
    DatabaseReference root,bookNow,index,registerUserTable;
    private Long othersMobileNo;
    private String othersName,userName;

    private String bookFor;
    private Long regMobileNoBookNow;

    private double distanceCalc, amount;

    private String[] licenseCategoryList = {"Recidency","Le Meridian","Park Plaza","SkyLine", "Jenny","Radisan Blue", "Taj"};
    private MaterialBetterSpinner licenseCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        rowItemSelect=new ArrayList<RowItemSelect>();

        root = FirebaseDatabase.getInstance().getReference();
        bookNow = root.child("BookNow");
        registerUserTable = root.child("Register");

        carType = (MaterialBetterSpinner) findViewById(R.id.carType);

        checkTariff=(TextView) findViewById(R.id.checkTariff);
        confirm=(Button) findViewById(R.id.confirm);
        cancel=(Button) findViewById(R.id.cancel);

        fromLocation = (EditText) findViewById(R.id.fromLocation);
        toLocation = (EditText) findViewById(R.id.toLocation);
        distance=(EditText) findViewById(R.id.distance);
        loadSpinnerData();

        //confirm.setVisibility(View.INVISIBLE);
        //cancel.setVisibility(View.INVISIBLE);
        checkTariff.setOnClickListener(this);

        //This is used to update the material spinenner with the hotel names
        licenseCategory = (MaterialBetterSpinner) findViewById(R.id.licenseCategory);
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, licenseCategoryList);
        licenseCategory.setAdapter(unitAdapter);

        Bundle bundle = getIntent().getExtras();
        //regMobileNo = bundle.getLong("regMobileNo");
        //othersMobileNo = bundle.getLong("othersMobileNo");
        //othersName = bundle.getString("othersName");
        //bookFor = bundle.getString("BookFor");
        from = bundle.getString("from");
        to = bundle.getString("to");
        distanceIntent = bundle.getString("distanceIntent");
        distanceCalc = bundle.getDouble("roundOff");

        //distanceCalc = Double.parseDouble(distanceIntent);

        if(distanceCalc>=0 && distanceCalc < 7)
        {
            amount = 60;
        }

        else if(distanceCalc>=7 && distanceCalc < 40)
        {
            amount = distanceCalc * 10;
        }

        else if(distanceCalc>=40 && distanceCalc < 60)
        {
            amount = distanceCalc * 13;
        }

        else if(distanceCalc>60)
        {
            amount = distanceCalc * 15;
        }



        System.out.println("bookFor "+bookFor);

        registerUserTable.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                regMobileNoBookNow = (Long) dataSnapshot.child("Mobile").getValue();
                userName=(String) dataSnapshot.child("Name").getValue();

                RowItemSelect item=new RowItemSelect( regMobileNoBookNow,userName);
                rowItemSelect.add(item);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                regMobileNoBookNow = (Long) dataSnapshot.child("Mobile").getValue();
                userName=(String) dataSnapshot.child("Name").getValue();

                RowItemSelect item=new RowItemSelect( regMobileNoBookNow,userName);
                rowItemSelect.add(item);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                regMobileNoBookNow = (Long) dataSnapshot.child("Mobile").getValue();
                userName=(String) dataSnapshot.child("Name").getValue();

                RowItemSelect item=new RowItemSelect( regMobileNoBookNow,userName);
                rowItemSelect.add(item);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        fromLocation.setText(from);
        toLocation.setText(to);
        distance.setText(distanceIntent);
        checkTariff.setText("Rs. " + Double.toString(amount));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                System.out.println(date1.format(date));

                Calendar cal = Calendar.getInstance();
                System.out.println(time.format(cal.getTime()));


                Map<String, Object> map1 = new HashMap<String, Object>();
                index = bookNow.push();
                map1.put("StartLocation", from);
                map1.put("EndLocation", to);
                map1.put("Distance", distanceIntent);
                map1.put("Amount", amount);

                map1.put("Mobile", regMobileNo);
                map1.put("Name", regMobileNo);
                map1.put("Date", date1.format(date));
                map1.put("Time",time.format(cal.getTime()));
                map1.put("IndexKey", index.getKey());
                index.updateChildren(map1);

                sendNotification("BookTaxi","Request had been received. Our Driver will call you with 15 mins \nFrom - "+ from + "\nTo - " + to + "\nAmount - "+amount );

                Intent intent = new Intent(Book.this, MainActivity.class);

                intent.putExtra("regMobileNo", regMobileNo);
                Toast.makeText(Book.this, "Your trip has been booked successfully", Toast.LENGTH_LONG).show();

                startActivity(intent);
                finish();
            }
        });

    }
    private void loadSpinnerData() {
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, cartype);
        carType.setAdapter(unitAdapter);

    }


    @Override
    public void onClick(View view) {

    }

    private void sendNotification(String title, String remoteMessage) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        System.out.println(title + "\n" + remoteMessage);

        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.a4e)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage))
                .setContentTitle(title)
                .setContentText(remoteMessage)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());

    }
}