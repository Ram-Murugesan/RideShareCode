package com.example.ramkumar.rideshare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Travel extends AppCompatActivity {
    private Button travelProfile, travelEdit, travelDriver, submitTravelDetails, uploadRegisterDetails;
    private EditText travelsName, travelAddress, addNewVehicle;
    private String travelsNameStr, travelAddressStr, addNewVehicleStr, imageName;
    private String[] vehicleCategoryList = {"2 Passengers", "4 Passengers", "8 Passengers"};
    private ImageView registeredDetails;
    private MaterialBetterSpinner vehicleCategory;
    private ListView listView;
    private List<RowItemSelect> rowItemSelects;
    DatabaseReference root, travelsEdit, index;
    private List<RowItemSelect> rowItemSelect, checkboxList;
    // CustomBaseAdapter adapter;
    private String checkboxName;
    String tName, tAddress, adminStat, tIndexKey;
    Long tMobileNo;

    //ImageView process datatypes declaration
    private static int IMG_RESULT = 200;
    Intent intent;
    private Long regMobileNo;
    private String myBase64Image;
    private Bitmap myBitmapAgain;
    String imageValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        travelsName = (EditText) findViewById(R.id.travelsName);
        travelAddress = (EditText) findViewById(R.id.travelAddress);


        registeredDetails = (ImageView) findViewById(R.id.registeredDetails);


        uploadRegisterDetails = (Button) findViewById(R.id.uploadRegisteredDetails);
        submitTravelDetails = (Button) findViewById(R.id.submitTravelDetails);
//ImageView
        uploadRegisterDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Image Upload is clicked");

                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, IMG_RESULT);

            }
        });
        rowItemSelect = new ArrayList<RowItemSelect>();
        checkboxList = new ArrayList<RowItemSelect>();
        for (int i = 0; i < vehicleCategoryList.length; i++) {
            RowItemSelect item = new RowItemSelect(vehicleCategoryList[i], false);
            rowItemSelect.add(item);
        }
       /* adapter = new CustomBaseAdapter(this, rowItemSelect);
        listView.setAdapter(adapter);
*/
        root = FirebaseDatabase.getInstance().getReference();
        travelsEdit = root.child("Travels");


        rowItemSelect = new ArrayList<RowItemSelect>();

        Bundle bundle = getIntent().getExtras();
        regMobileNo = bundle.getLong("regMobileNo");
        System.out.println("regMobileNo bundle in TravelEdit OnCreate " + regMobileNo);

        travelsEdit.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                tName = (String) dataSnapshot.child("TravelsName").getValue();
                tAddress = (String) dataSnapshot.child("TravelsAddress").getValue();
                adminStat = (String) dataSnapshot.child("AdminStatus").getValue();
                tMobileNo = (Long) dataSnapshot.child("MobileNo").getValue();
                tIndexKey = (String) dataSnapshot.child("indexKey").getValue();
                imageValue = (String) dataSnapshot.child("RegProofImageUrl").getValue();
                //  System.out.println("imageValue"+imageValue);

                //get the key add it in the rowitemselect
                RowItemSelect item = new RowItemSelect(tName, tAddress, adminStat, tMobileNo, tIndexKey, imageValue, checkboxList);
                rowItemSelect.add(item);
           /*     for (int i1 = 0; i1 < CustomBaseAdapter.rowItemSelectList.size(); i1++) {
                    vehicleCategoryList[i1] = CustomBaseAdapter.rowItemSelectList.get(i1).getCheckBoxName();

                }*/
                for (int i = 0; i < rowItemSelect.size(); i++) {
                    System.out.println("checking the loop");
                    if (regMobileNo != null) {
                        if (regMobileNo.equals(tMobileNo) && adminStat.equals("verified")) {
                            System.out.println("if the mobileNo exist and admin has checked");
                            System.out.println(tName);


                            travelsName.setText(tName);
                            travelAddress.setText(tAddress);

                            try {
                                myBitmapAgain = decodeBase64(rowItemSelect.get(i).getImageValue());
                                registeredDetails.setImageBitmap(myBitmapAgain);
                            } catch (Exception e) {
                                System.out.println("exception for image download " + e);
                            }

                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        submitTravelDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean status = false;
                travelsNameStr = travelsName.getText().toString();
                travelAddressStr = travelAddress.getText().toString();
                //addNewVehicleStr = addNewVehicle.getText().toString();

                if (travelsName.toString().trim().equalsIgnoreCase("") || travelAddress.toString().trim().equalsIgnoreCase("") || registeredDetails.toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(Travel.this, "Enter all fields", Toast.LENGTH_SHORT).show();

                } else {
                    System.out.println("ram");
                    try {
                        for (int i = 0; i < rowItemSelect.size(); i++) {


                            System.out.println("status" + status);
                            if (regMobileNo.equals(rowItemSelect.get(i).gettMobileNo()) && rowItemSelect.get(i).getAdminStat().equals("verified")) {
                                System.out.println(rowItemSelect.get(i).gettMobileNo());
                                status = true;
                                //table selection
                                System.out.println("status" + status);
                                final String key = rowItemSelect.get(i).gettIndexKey();

                                travelsName.setText(rowItemSelect.get(i).gettName());
                                travelAddress.setText(rowItemSelect.get(i).gettAddress());

//                                Bitmap bm=((BitmapDrawable)registeredDetails.getDrawable()).getBitmap();
//                                System.out.println("myBitmapAgain "+bm);
                                if (rowItemSelect.get(i).getImageValue().equals("")) {

                                } else if (myBase64Image != null) {

                                } else {
                                    myBase64Image = rowItemSelect.get(i).getImageValue();

                                }
  /*                              for (int i1 = 0; i1 < CustomBaseAdapter.rowItemSelectList.size(); i1++) {
                                    RowItemSelect rowItemSelect1 = new RowItemSelect(CustomBaseAdapter.rowItemSelectList.get(i1).getCheckBoxName());
                                    rowItemSelect.add(rowItemSelect1);
                                }
*/

                                Query pendingTasks = travelsEdit.orderByChild("indexKey").equalTo(key);
                                pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot tasksSnapshot) {
                                        //checkboxName = "";
  /*                                      for (int i1 = 0; i1 < CustomBaseAdapter.rowItemSelectList.size(); i1++) {
                                            vehicleCategoryList[i1] = CustomBaseAdapter.rowItemSelectList.get(i1).getCheckBoxName();
                                            checkboxName = checkboxName + vehicleCategoryList[i1] + "+";
                                        }
  */                                      for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {

                                            snapshot.getRef().child("TravelsName").setValue(travelsNameStr.trim());
                                            snapshot.getRef().child("TravelsAddress").setValue(travelAddressStr.trim());
                                            snapshot.getRef().child("AdminStatus").setValue("verified");
                                            snapshot.getRef().child("MobileNo").setValue(regMobileNo);
                                            snapshot.getRef().child("RegProofImageUrl").setValue(myBase64Image);

                                            // snapshot.getRef().child("vehicleCategoryList").setValue(checkboxName);
                                            Intent intent = new Intent(Travel.this,MainActivity.class);

                                            intent.putExtra("regMobileNo", regMobileNo);
                                            Toast.makeText(Travel.this, "Travel Agency details", Toast.LENGTH_LONG).show();
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError firebaseError) {
                                        System.out.println("The read failed: " + firebaseError.getMessage());
                                    }
                                });

                                // travelsEdit.child(rowItemSelect.get(i).gettIndexKey()).child("indexKey").setValue(index);

                                Toast.makeText(Travel.this, "Updated Successfully", Toast.LENGTH_LONG).show();

                            }
                        }
                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                    System.out.println("status " + status);
                    if (status == false) {
                        index = travelsEdit.push();
                        System.out.println("index" + index);

                        Map<String, Object> map1 = new HashMap<String, Object>();

/*
                        for (int i1 = 0; i1 < CustomBaseAdapter.rowItemSelectList.size(); i1++) {
                            vehicleCategoryList[i1] = CustomBaseAdapter.rowItemSelectList.get(i1).getCheckBoxName();
                            checkboxName = checkboxName + vehicleCategoryList[i1] + "+";
                        }
*/
                        map1.put("TravelsName", travelsNameStr.trim());
                        map1.put("TravelsAddress", travelAddressStr.trim());
                        map1.put("AdminStatus", "verified");
                        map1.put("RegProofImageUrl", myBase64Image);
                        map1.put("MobileNo", regMobileNo);
                        map1.put("indexKey", index.getKey());

                        index.updateChildren(map1);

                        Intent intent = new Intent(Travel.this,MainActivity.class);
                        intent.putExtra("regMobileNo", regMobileNo);
                        Toast.makeText(Travel.this, "Travel Agency details submitted..,", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();

                    }
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            System.out.println("try initiated");
            System.out.println("data" + data);
            if (requestCode == IMG_RESULT && resultCode == Activity.RESULT_OK) {

                Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                //  registeredDetails.setImageBitmap(capturedImage);


                myBase64Image = encodeToBase64(capturedImage, Bitmap.CompressFormat.JPEG, 100);
                //System.out.println(" Bitmap image "+myBase64Image);
                myBitmapAgain = decodeBase64(myBase64Image);
                registeredDetails.setImageBitmap(myBitmapAgain);


            }
        } catch (Exception e1) {
            System.out.println(e1);
        }

    }

    private void customBaseAdapterMethod(String checkboxName) {
        rowItemSelects = new ArrayList<RowItemSelect>();
        System.out.println("checkBox " + checkboxName);
        Boolean checkBoxState = false;
        String[] vehicleList = checkboxName.split("\\+");
        if (vehicleList.length != 0) {
            for (int i = 0; i < vehicleCategoryList.length; i++) {

                for (int j = 0; j < vehicleList.length; j++) {
                    if (!(vehicleList[j].equals(vehicleCategoryList[i]))) {
                        checkBoxState = false;
                        continue;
                    } else {
                        checkBoxState = true;
                        System.out.println("checkbox list " + vehicleList[j] + vehicleCategoryList[i]);
                        RowItemSelect item = new RowItemSelect(vehicleCategoryList[i], checkBoxState);
                        rowItemSelects.add(item);
                        break;
                    }
                }
                if (checkBoxState == false) {
//                    System.out.println("checkbox list " + vehicleList[i] + vehicleCategoryList[i]);
                    RowItemSelect item = new RowItemSelect(vehicleCategoryList[i], checkBoxState);
                    rowItemSelects.add(item);
                }

            }
/*
            adapter = new CustomBaseAdapter(this, rowItemSelects);
            listView.setAdapter(adapter);
*/

        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
