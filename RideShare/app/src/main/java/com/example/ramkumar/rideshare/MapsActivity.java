package com.example.ramkumar.rideshare;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        View.OnClickListener {

    private GoogleMap mMap;

    private Button book;

    private double longitude;
    private double latitude;
    private int i;
    private double fromLongitude;
    private double fromLatitude;
    private double toLongitude;
    private double toLatitude;
    private GoogleApiClient googleApiClient;

    private TextView fromPlace,destinationPlace;
    private String from,to,distanceIntent;

    private TextView distance;
    private Marker currentLocationMarker;
    private Button buttonCalcDistance,clearMap;
    private GoogleApiClient client;

    private Long regMobileNo,othersMobileNo;
    private String othersName,bookFor;
    private String activityName;

    double roundOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();

        Bundle bundle = getIntent().getExtras();
        regMobileNo = bundle.getLong("regMobileNo");
        activityName=bundle.getString("activityName");
        othersMobileNo=bundle.getLong("othersMobileNo");
        othersName=bundle.getString("othersName");
        bookFor = bundle.getString("BookFor");

        System.out.println("regMobileNo bundle in MapActivity OnCreate " + regMobileNo);

        fromPlace = (TextView) findViewById(R.id.fromPlace);
        destinationPlace = (TextView) findViewById(R.id.destinationPlace);

        buttonCalcDistance=(Button) findViewById(R.id.buttonCalcDistance);
        clearMap=(Button) findViewById(R.id.clearMap);

        buttonCalcDistance.setOnClickListener(this);

        distance = (TextView) findViewById(R.id.distance);

        book = (Button) findViewById(R.id.book);

        fromPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=1;
                currentLocationMarker.remove();
                try {
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(MapsActivity.this);
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        });
        destinationPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    i=2;
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(MapsActivity.this);
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        });

        clearMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                moveMap();
                fromPlace.setText("");
                destinationPlace.setText("");
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( fromPlace.getText().toString().equals("") && destinationPlace.getText().toString().equals("") ){
                    Toast.makeText(MapsActivity.this,"Enter the Source and Destination Place", Toast.LENGTH_LONG).show();
                }
                else
                {

                    Intent i=new Intent(MapsActivity.this,Book.class);
                    i.putExtra("regMobileNo", regMobileNo);
                    i.putExtra("from", from);
                    i.putExtra("to", to);
                    i.putExtra("distanceIntent",distanceIntent);
                    i.putExtra("roundOff",roundOff);
                    startActivity(i);
                    finish();
                }


            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber()+place.getId());
                List<Address> addressList = null;
                String textAddress = place.getName().toString();
                if (textAddress != null || !textAddress.equals("")) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList = geocoder.getFromLocationName(textAddress, 5);
                        System.out.println("addressList " + addressList);
                        if(i==1)
                        {
                            fromPlace.setText(place.getName().toString());
                            from = fromPlace.getText().toString();
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            fromLatitude = address.getLatitude();
                            fromLongitude = address.getLongitude();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(place.getName().toString()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                        }
                        else if(i==2)
                        {
                            destinationPlace.setText(place.getName().toString());
                            to = destinationPlace.getText().toString();
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            toLatitude = address.getLatitude();
                            toLongitude = address.getLongitude();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(place.getName().toString()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e("Tag", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap = googleMap;
        LatLng latLng = new LatLng(-34, 151);
        currentLocationMarker= mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Maps Page",

                Uri.parse(""),
                Uri.parse("")
        );
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Maps Page",
                Uri.parse(""),
                Uri.parse("")
        );
    }
    private void getCurrentLocation() {
        mMap.clear();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            moveMap();
        }
    }
    private void moveMap() {
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))); //Adding a title
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");

        //urlString.append("&key=AIzaSyAYWd9vNIgREGBrZWrwASdkFn0rWePpATY");
        //Direction API - AIzaSyB2Al3VdnsZX4jJJxt603Ov4l2qNtoHPss
        //Places API - AIzaSyD6pu8uzgIyYIoQhkGbOaqAsx-Llrqlgdk
        urlString.append("&key=AIzaSyD6pu8uzgIyYIoQhkGbOaqAsx-Llrqlgdk");
        return urlString.toString();
    }

    private void getDirection() {
        String url = makeURL(fromLatitude, fromLongitude, toLatitude, toLongitude);
        final ProgressDialog loading = ProgressDialog.show(this, "Getting Route", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        drawPath(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void drawPath(String result) {
        LatLng from = new LatLng(fromLatitude, fromLongitude);
        LatLng to = new LatLng(toLatitude, toLongitude);
        Double distance1 = SphericalUtil.computeDistanceBetween(from, to);
        Double distancekm = distance1 / 1000;
        roundOff = Math.round(distancekm * 100.0) / 100.0;
        Toast.makeText(this, String.valueOf(roundOff + " kms"), Toast.LENGTH_LONG).show();
        distance.setText(String.valueOf(roundOff + " kms"));
        distanceIntent = distance.getText().toString();
        try {
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(20)
                    .color(Color.MAGENTA)
                    .geodesic(true)
            );
        } catch (JSONException e) {
        }
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }

    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;
        moveMap();
    }
    @Override
    public void onClick(View v) {
        if(v == buttonCalcDistance){
            getDirection();
        }
    }

}
