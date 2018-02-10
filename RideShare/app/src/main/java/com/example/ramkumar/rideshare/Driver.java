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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Driver extends AppCompatActivity {

    private String[] licenseCategoryList = {"4 Wheeler","Heavy Vehicle"};
    private String[] otherGovtProofSpinnerList={"Ration Card","Aadhar Card"};
    public static final String[] nameList = new String[] {
            "Coimbatore","Salem","Madurai","Chennai","Ooty"};
    private ImageView addressProof,licenseProof,profilePicture,panCardProof,otherGovtProof;
    private TextView carDetails;
    private Button driverProfile,driverAvailability,driverPayment,confirm;
    private EditText driverName,driverDOB,permanentAddress,licenseNumber,dateOfIssue,dateOfExpiry,panCardId,panCardName;
    DatabaseReference root, driverProfileDB, index;

    private Button uploadAddressProof,uploadLicenseProof,uploadProfilePicture,uploadPanCardProof,uploadOtherGovtProof;
    private String uploadAddressStr,uploadLicenseStr,uploadProfilePictureStr,uploadPanCardStr,uploadOtherGovtStr;

    private Long regMobileNo,dMobileNo;
    private String adminStat,dIndexKey,licenseCategoryStr,otherGovtProofSpinnerStr;
    private String driverNameStr,driverDOBStr,permanentAddressStr,licenseNumberStr,dateOfIssueStr,dateOfExpiryStr,panCardIdStr,panCardNameStr;
    private String addressProofStr,licenseProofStr,profilePictureStr,panCardProofStr,otherGovtProofStr;

    //ImageView process datatypes declaration
    private static int IMG_RESULT = 200;
    Intent intent;
    private String myBase64Image;
    private Bitmap myBitmapAgain;
    String imageValue;
    private int count;

    List<RowItemSelect> rowItemSelect;
    private MaterialBetterSpinner licenseCategory,otherGovtProofSpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        addressProof =(ImageView) findViewById(R.id.addressProof);

        driverName = (EditText) findViewById(R.id.driverName);
        driverDOB = (EditText) findViewById(R.id.driverDOB);
        permanentAddress = (EditText) findViewById(R.id.permanentAddress);
        licenseNumber = (EditText) findViewById(R.id.licenseNumber);
        dateOfIssue = (EditText) findViewById(R.id.dateOfIssue);
        dateOfExpiry = (EditText) findViewById(R.id.dateOfExpiry);
        panCardId = (EditText) findViewById(R.id.panCardId);
        panCardName = (EditText) findViewById(R.id.panCardName);

        licenseProof =(ImageView) findViewById(R.id.licenseProof);
        profilePicture =(ImageView) findViewById(R.id.profilePicture);
        panCardProof =(ImageView) findViewById(R.id.panCardProof);
        otherGovtProof =(ImageView) findViewById(R.id.otherGovtProof);

        uploadAddressProof =(Button) findViewById(R.id.uploadAddressProof);
        uploadLicenseProof =(Button) findViewById(R.id.uploadLicenseProof);
        uploadProfilePicture =(Button) findViewById(R.id.uploadProfilePicture);
        uploadPanCardProof =(Button) findViewById(R.id.uploadPanCardProof);
        uploadOtherGovtProof =(Button) findViewById(R.id.uploadOtherGovtProof);

        licenseCategory = (MaterialBetterSpinner) findViewById(R.id.licenseCategory);
        otherGovtProofSpinner = (MaterialBetterSpinner) findViewById(R.id.otherGovtProofSpinner);

        confirm =(Button) findViewById(R.id.confirm);
        root = FirebaseDatabase.getInstance().getReference();
        driverProfileDB = root.child("DriverProfile");

        uploadAddressProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Image Upload is clicked");
                count=1;
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, IMG_RESULT);
            }
        });

        uploadLicenseProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Image Upload is clicked");
                count=2;
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, IMG_RESULT);
            }
        });

        uploadOtherGovtProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Image Upload is clicked");
                count=3;
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, IMG_RESULT);
            }
        });

        uploadPanCardProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Image Upload is clicked");
                count=4;
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, IMG_RESULT);
            }
        });

        uploadProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Image Upload is clicked");
                count=5;
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, IMG_RESULT);


            }
        });



        driverProfileDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                driverNameStr = (String) dataSnapshot.child("DriverName").getValue();
                driverDOBStr = (String) dataSnapshot.child("DriverDOB").getValue();
                permanentAddressStr = (String) dataSnapshot.child("PermanentAddress").getValue();
                dMobileNo = (Long) dataSnapshot.child("MobileNo").getValue();
                adminStat = (String) dataSnapshot.child("AdminStatus").getValue();
                licenseNumberStr = (String) dataSnapshot.child("LicenseNumber").getValue();
                dateOfIssueStr = (String) dataSnapshot.child("DateOfIssue").getValue();
                dateOfExpiryStr = (String) dataSnapshot.child("DateOfExpiry").getValue();
                panCardIdStr = (String) dataSnapshot.child("PanCardId").getValue();
                licenseCategoryStr=(String) dataSnapshot.child("LicenseCategory").getValue();
                otherGovtProofSpinnerStr=(String) dataSnapshot.child("OtherGovtProofName").getValue();
                panCardNameStr = (String) dataSnapshot.child("PanCardName").getValue();
                dIndexKey = (String) dataSnapshot.child("indexKey").getValue();
                uploadAddressStr = (String) dataSnapshot.child("AddressProof").getValue();
                uploadLicenseStr = (String) dataSnapshot.child("LicenseProof").getValue();
                uploadProfilePictureStr = (String) dataSnapshot.child("ProfilePicture").getValue();
                uploadPanCardStr = (String) dataSnapshot.child("PanCardProof").getValue();
                uploadOtherGovtStr = (String) dataSnapshot.child("OtherGovtProof").getValue();

                RowItemSelect item = new RowItemSelect(dIndexKey,adminStat,dMobileNo,driverNameStr,driverDOBStr,permanentAddressStr,licenseNumberStr,dateOfIssueStr,dateOfExpiryStr,panCardIdStr,panCardNameStr,addressProofStr,licenseProofStr,profilePictureStr,panCardProofStr,otherGovtProofStr,uploadAddressStr,uploadLicenseStr,uploadProfilePictureStr,uploadPanCardStr,uploadOtherGovtStr,licenseCategoryStr,otherGovtProofSpinnerStr);
                rowItemSelect.add(item);

                for (int i = 0; i < rowItemSelect.size(); i++) {
                    System.out.println("checking the loop");
                    if (regMobileNo != null) {
                        if (regMobileNo.equals(dMobileNo) && adminStat.equals("verified")) {
                            System.out.println("if the mobileNo exist and admin has checked");
                            System.out.println(driverNameStr);

                            driverName.setText(driverNameStr);
                            driverDOB.setText(driverDOBStr);
                            permanentAddress.setText(permanentAddressStr);
                            licenseNumber.setText(licenseNumberStr);
                            dateOfIssue.setText(dateOfIssueStr);
                            dateOfExpiry.setText(dateOfExpiryStr);
                            panCardId.setText(panCardIdStr);
                            panCardName.setText(panCardNameStr);
                            licenseCategory.setText(licenseCategoryStr);
                            otherGovtProofSpinner.setText(otherGovtProofSpinnerStr);
                            //Converting Image Values
                            try {
                                myBitmapAgain = decodeBase64(rowItemSelect.get(i).getUploadAddressStr());
                                addressProof.setImageBitmap(myBitmapAgain);
                                myBitmapAgain = decodeBase64(rowItemSelect.get(i).getUploadLicenseStr());
                                licenseProof.setImageBitmap(myBitmapAgain);
                                myBitmapAgain = decodeBase64(rowItemSelect.get(i).getUploadProfilePictureStr());
                                profilePicture.setImageBitmap(myBitmapAgain);
                                myBitmapAgain = decodeBase64(rowItemSelect.get(i).getUploadPanCardStr());
                                panCardProof.setImageBitmap(myBitmapAgain);
                                myBitmapAgain = decodeBase64(rowItemSelect.get(i).getUploadOtherGovtStr());
                                otherGovtProof.setImageBitmap(myBitmapAgain);
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
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean status = false;
                driverNameStr = driverName.getText().toString();
                driverDOBStr = driverDOB.getText().toString();
                permanentAddressStr = permanentAddress.getText().toString();
                licenseNumberStr = licenseNumber.getText().toString();
                dateOfIssueStr = dateOfIssue.getText().toString();
                dateOfExpiryStr = dateOfExpiry.getText().toString();
                panCardIdStr = panCardId.getText().toString();
                panCardNameStr = panCardName.getText().toString();
                licenseCategoryStr=licenseCategory.getText().toString();
                otherGovtProofSpinnerStr=otherGovtProofSpinner.getText().toString();

                if (driverNameStr.toString().trim().equalsIgnoreCase("") || driverDOBStr.toString().trim().equalsIgnoreCase("") ||
                        permanentAddressStr.toString().trim().equalsIgnoreCase("") || licenseNumberStr.toString().trim().equalsIgnoreCase("") ||
                        dateOfIssueStr.toString().trim().equalsIgnoreCase("") || dateOfExpiryStr.toString().trim().equalsIgnoreCase("") ||
                        panCardIdStr.toString().trim().equalsIgnoreCase("") || panCardNameStr.toString().trim().equalsIgnoreCase("") ||
                        licenseCategory.toString().trim().equalsIgnoreCase("") || otherGovtProofSpinner.toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(Driver.this, "Enter all fields", Toast.LENGTH_SHORT).show();

                } else {
                    System.out.println("ram");
                    try {
                        for (int i = 0; i < rowItemSelect.size(); i++) {


                            System.out.println("status" + status);
                            if (regMobileNo.equals(rowItemSelect.get(i).getdMobileNo()) && rowItemSelect.get(i).getdAdminStat().equals("verified")) {
                                System.out.println("dMobileNo"+rowItemSelect.get(i).getdMobileNo());
                                status = true;

                                //table selection
                                System.out.println("status" + status);
                                final String key = rowItemSelect.get(i).getDriverProfileIndexKey();

                                driverName.setText(rowItemSelect.get(i).getDriverNameStr());
                                driverDOB.setText(rowItemSelect.get(i).getDriverDOBStr());
                                permanentAddress.setText(rowItemSelect.get(i).getPermanentAddressStr());
                                licenseNumber.setText(rowItemSelect.get(i).getLicenseNumberStr());
                                dateOfIssue.setText(rowItemSelect.get(i).getDateOfIssueStr());
                                dateOfExpiry.setText(rowItemSelect.get(i).getDateOfExpiryStr());
                                panCardId.setText(rowItemSelect.get(i).getPanCardIdStr());
                                panCardName.setText(rowItemSelect.get(i).getPanCardNameStr());
                                licenseCategory.setText(licenseCategoryStr);
                                otherGovtProofSpinner.setText(otherGovtProofSpinnerStr);

                                Query pendingTasks = driverProfileDB.orderByChild("indexKey").equalTo(key);
                                pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot tasksSnapshot) {


                                        /*  To delete the data from the database
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                        Query applesQuery = ref.child("firebase-test").orderByChild("title").equalTo("Apple");

                                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                                    appleSnapshot.getRef().removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.e(TAG, "onCancelled", databaseError.toException());
                                            }
                                        });
                                        */


                                        for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {

                                            snapshot.getRef().child("DriverName").setValue( driverNameStr.trim());
                                            snapshot.getRef().child("DriverDOB").setValue( driverDOBStr.trim());
                                            snapshot.getRef().child("AdminStatus").setValue("verified");
                                            snapshot.getRef().child("PermanentAddress").setValue( permanentAddressStr);
                                            snapshot.getRef().child("MobileNo").setValue( regMobileNo);
                                            snapshot.getRef().child("LicenseNumber").setValue( licenseNumberStr.trim());
                                            snapshot.getRef().child("DateOfIssue").setValue( dateOfIssueStr.trim());
                                            snapshot.getRef().child("DateOfExpiry").setValue( dateOfExpiryStr.trim());
                                            snapshot.getRef().child("PanCardId").setValue( panCardIdStr.trim());
                                            snapshot.getRef().child("PanCardName").setValue( panCardNameStr.trim());
                                            snapshot.getRef().child("LicenseCategory").setValue( licenseCategoryStr.trim());
                                            snapshot.getRef().child("OtherGovtProofName").setValue( otherGovtProofSpinnerStr.trim());

                                            snapshot.getRef().child("AddressProof").setValue( uploadAddressStr.trim());
                                            snapshot.getRef().child("LicenseProof").setValue( uploadLicenseStr.trim());
                                            snapshot.getRef().child("ProfilePicture").setValue( uploadProfilePictureStr.trim());
                                            snapshot.getRef().child("PanCardProof").setValue( uploadPanCardStr.trim());
                                            snapshot.getRef().child("OtherGovtProof").setValue( uploadOtherGovtStr.trim());
                                            System.out.println("PanCard "+panCardNameStr.trim());

                                            Intent intent = new Intent(Driver.this, Driver.class);
                                            intent.putExtra("regMobileNo", regMobileNo);
                                            Toast.makeText(Driver.this, "Driver details", Toast.LENGTH_LONG).show();
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError firebaseError) {
                                        System.out.println("The read failed: " + firebaseError.getMessage());
                                    }
                                });





                                Toast.makeText(Driver.this, "Updated Successfully", Toast.LENGTH_LONG).show();

                            }
                        }
                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                    System.out.println("status " + status);
                    if (status == false) {
                        index = driverProfileDB.push();
                        System.out.println("index" + index);

                        Map<String, Object> map1 = new HashMap<String, Object>();

                        map1.put("DriverName", driverNameStr.trim());
                        map1.put("DriverDOB", driverDOBStr.trim());
                        map1.put("AdminStatus", "verified");
                        map1.put("PermanentAddress", permanentAddressStr);
                        map1.put("MobileNo", regMobileNo);
                        map1.put("indexKey", index.getKey());
                        map1.put("LicenseNumber", licenseNumberStr.trim());
                        map1.put("DateOfIssue", dateOfIssueStr.trim());
                        map1.put("DateOfExpiry", dateOfExpiryStr.trim());
                        map1.put("PanCardId", panCardIdStr.trim());
                        map1.put("PanCardName", panCardNameStr.trim());
                        map1.put("LicenseCategory", licenseCategoryStr.trim());
                        map1.put("OtherGovtProofName", otherGovtProofSpinnerStr.trim());

                        map1.put("AddressProof", uploadAddressStr.trim());
                        map1.put("LicenseProof", uploadLicenseStr.trim());
                        map1.put("ProfilePicture", uploadProfilePictureStr.trim());
                        map1.put("PanCardProof", uploadPanCardStr.trim());
                        map1.put("OtherGovtProof", uploadOtherGovtStr.trim());

                        index.updateChildren(map1);

                        Intent intent = new Intent(Driver.this, Driver.class);
                        intent.putExtra("regMobileNo", regMobileNo);
                        Toast.makeText(Driver.this, "Driver details sent to admin verification", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();

                    }
                }

            }


        });

        Bundle bundle = getIntent().getExtras();
        regMobileNo = bundle.getLong("regMobileNo");
        System.out.println("regMobileNo bundle in DriverProfile OnCreate " + regMobileNo);


        licenseCategory=(MaterialBetterSpinner) findViewById(R.id.licenseCategory);
        otherGovtProofSpinner=(MaterialBetterSpinner) findViewById(R.id.otherGovtProofSpinner);
        rowItemSelect = new ArrayList<RowItemSelect>();
        for (int i = 0; i < nameList.length; i++) {
            RowItemSelect item = new RowItemSelect(nameList[i],false );
            rowItemSelect.add(item);
        }
        /*adapter= new CustomBaseAdapter(this, rowItemSelect);*/
        loadSpinnerData();





    }
    private void loadSpinnerData() {
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, licenseCategoryList);
        licenseCategory.setAdapter(unitAdapter);
        ArrayAdapter<String> unitAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, otherGovtProofSpinnerList);
        otherGovtProofSpinner.setAdapter(unitAdapter1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            System.out.println("try initiated");
            System.out.println("data" + data);
            if (requestCode == IMG_RESULT && resultCode == Activity.RESULT_OK) {

                Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                myBase64Image = encodeToBase64(capturedImage, Bitmap.CompressFormat.JPEG, 100);
//                 System.out.println(" Bitmap image "+myBase64Image);
                myBitmapAgain = decodeBase64(myBase64Image);

                imageProcess(myBase64Image);
                System.out.println("count :"+count);

            }
        } catch (Exception e1) {
            Intent intent = new Intent(Driver.this, Driver.class);
            intent.putExtra("regMobileNo", regMobileNo);
            Toast.makeText(Driver.this, "Travel Agency details sent to admin verification", Toast.LENGTH_LONG).show();
            startActivity(intent);

            System.out.println(e1);
        }


    }

    private void imageProcess(String myBase64Image) {
        System.out.println("count in imageProcess:"+count);
        if(count==1)

        {
            addressProof.setImageBitmap(myBitmapAgain);
            uploadAddressStr = myBase64Image;
            System.out.println("myBitmapAgain :"+myBitmapAgain);
        }
        else  if(count==2)

        {
            licenseProof.setImageBitmap(myBitmapAgain);
            uploadLicenseStr = myBase64Image;
            System.out.println("myBitmapAgain :"+myBitmapAgain);
        }
        else  if(count==3)

        {
            otherGovtProof.setImageBitmap(myBitmapAgain);
            uploadOtherGovtStr = myBase64Image;
            System.out.println("myBitmapAgain :"+myBitmapAgain);
        }
        else  if(count==4)

        {
            panCardProof.setImageBitmap(myBitmapAgain);
            uploadPanCardStr = myBase64Image;
            System.out.println("myBitmapAgain :"+myBitmapAgain);
        }
        else  if(count==5)

        {
            profilePicture.setImageBitmap(myBitmapAgain);
            uploadProfilePictureStr = myBase64Image;
            System.out.println("myBitmapAgain :"+myBitmapAgain);
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
