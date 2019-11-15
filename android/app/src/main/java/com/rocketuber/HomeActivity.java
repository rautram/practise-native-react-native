package com.rocketuber;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.gms.maps.model.JointType.ROUND;
import static com.rocketuber.MapUtils.getBearing;


/**
 * A demonstration about car movement on google map
 by @Shihab Uddin
 TO RUN -> GIVE YOUR GOOGLE API KEY to >  google_maps_api.xml file
 -> GIVE YOUR SERVER URL TO FETCH LOCATION UPDATE
 */

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final long DELAY = 3000;
    private static final long ANIMATION_TIME_PER_ROUTE = 3000;
    String polyLine = "andhDyy{gOpAb@v@Nh@DHCBE^ZRh@RnAHTTHhCZd@LRFbDnA^aAr@uGBs@H}@T{ABIPDfH|BxEnBZH|@N|BZrAX~DpABABCDC@?^{@xAkBlAiBpAeBhA{ARg@Ry@LaABcBSuCg@cG]wDAq@BcAJk@\\\\}@d@w@f@e@XObAYl@G~EMrBGtAWd@O\\\\M~BcBxDgCl@c@\\\\WbFoDl@]n@Wt@WbASv@Ix@C|A@fFZnCLfAPbDtAnG~CxHnDtEzBzIbEzBdA`CtA~DhB~@^|Bn@hBT^BvABPEtAEpA?d@Fp@I|@Gx@ElIQ|GIl@EnECnBIl@KlAa@r@a@bA{@x@mA\\\\y@b@_CHo@HwAPwMvAif@vCykAhCi~@H_FEsCy@wL{Bs]w@_MUsDKwDBmCHcCNgBXaCd@yC`DiQrBeLlBuKh@}CLi@@[Ae@Ke@g@y@{As@uEwBy@Qm@GgAb@gBr@K]_AwIOgCUgHo@yHEGIkAYaH@y@f@kGPuDBeCBi@\\\\oB~@sF?]Ei@S}BAqABeAHmAr@yFPmAFUNuAXkBFk@@g@@_@j@{@x@mAf@VH?FAPW\\\\iAHKd@KhAa@z@MjABZ?FC@CLo@XLHN@PIZIJG^AfA";
    GoogleMap googleMap;
    private PolylineOptions polylineOptions;
    private Polyline greyPolyLine;
    private SupportMapFragment mapFragment;
    private Handler handler;
    private Marker carMarker;
    private int index;
    private int next;
    private LatLng startPosition;
    private LatLng endPosition;
    private float v;
    Button button2;
    List<LatLng> polyLineList;
    private double lat, lng;
    // banani
    double latitude = 27.717124;
    double longitude = 85.3444037;
    private String TAG = "HomeActivity";

    private Boolean isCompleted;

    // Give your Server URL here >> where you get car location update
    public static final String URL_DRIVER_LOCATION_ON_RIDE = "*******";
    private boolean isFirstPosition = true;
    private Double startLatitude;
    private Double startLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(HomeActivity.this, SecondActivity.class);
//                startActivity(intent);

               // getStarted();

            //  staticPolyLine();
              // dynamicPolyLine();
                startGettingOnlineDataFromCar();

            }
        });

        handler = new Handler();
    }

    public void getStarted()
    {
        String url = "http://192.168.1.3:8080/api/tracking/client/history/5d75dfb9384c2803dd20d215";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject object1 = new JSONObject(response);
                            System.out.println("the json object is");
                            System.out.println(object1);
                            JSONObject object2 = (JSONObject) object1.get("data");
                            JSONObject coordinates = (JSONObject) object2.get("presentposition");
                            startLatitude = (Double) coordinates.get("latitude");
                            startLongitude = (Double) coordinates.get("longitude");
                            if(startLatitude == 27.6762408)
                            {
                                isCompleted = true;
                            }
                            if (isFirstPosition) {
                                startPosition = new LatLng(startLatitude, startLongitude);

                                carMarker = googleMap.addMarker(new MarkerOptions().position(startPosition).
                                        flat(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.new_car_small)));
                                carMarker.setAnchor(0.5f, 0.5f);

                                googleMap.moveCamera(CameraUpdateFactory
                                        .newCameraPosition
                                                (new CameraPosition.Builder()
                                                        .target(startPosition)
                                                        .zoom(15.5f)
                                                        .build()));

                                isFirstPosition = false;

                            } else {
                                endPosition = new LatLng(startLatitude, startLongitude);

                                Log.d(TAG, startPosition.latitude + "--" + endPosition.latitude + "--Check --" + startPosition.longitude + "--" + endPosition.longitude);

                                if ((startPosition.latitude != endPosition.latitude) || (startPosition.longitude != endPosition.longitude)) {

                                    Log.e(TAG, "NOT SAME");
                                    startBikeAnimation(startPosition, endPosition);

                                } else {

                                    Log.e(TAG, "SAMME");
                                }
                            }



                        }
                        catch (JSONException ex)
                        {
                            System.out.println("Can't covert to josn object");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        queue.add(stringRequest);

    }

    void staticPolyLine() {

        googleMap.clear();

        polyLineList = MapUtils.decodePoly(polyLine);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : polyLineList) {
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
        googleMap.animateCamera(mCameraUpdate);

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLACK);
        polylineOptions.width(10);
        polylineOptions.startCap(new SquareCap());
        polylineOptions.endCap(new SquareCap());
        polylineOptions.jointType(ROUND);
        polylineOptions.addAll(polyLineList);
        greyPolyLine = googleMap.addPolyline(polylineOptions);

        startCarAnimation(latitude, longitude);

    }

    Runnable staticCarRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "staticCarRunnable handler called...");
            if (index < (polyLineList.size() - 1)) {
                index++;
                next = index + 1;
            } else {
                index = -1;
                next = 1;
                stopRepeatingTask();
                return;
            }

            if (index < (polyLineList.size() - 1)) {
//                startPosition = polyLineList.get(index);
                startPosition = carMarker.getPosition();
                endPosition = polyLineList.get(next);
            }

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(3000);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {

//                    Log.i(TAG, "Car Animation Started...");

                    v = valueAnimator.getAnimatedFraction();
                    lng = v * endPosition.longitude + (1 - v)
                            * startPosition.longitude;
                    lat = v * endPosition.latitude + (1 - v)
                            * startPosition.latitude;
                    LatLng newPos = new LatLng(lat, lng);
                    carMarker.setPosition(newPos);
                    carMarker.setAnchor(0.5f, 0.5f);
                    if(getDistance(startPosition.latitude, startPosition.longitude, newPos.latitude, newPos.longitude) > 0.015) {
                        carMarker.setRotation(getBearing(startPosition, newPos));
                    }
                    Log.d("distance", "the distance is "+getDistance(startPosition.latitude, startPosition.longitude, newPos.latitude, newPos.longitude));
                    googleMap.moveCamera(CameraUpdateFactory
                            .newCameraPosition
                                    (new CameraPosition.Builder()
                                            .target(newPos)
                                            .zoom(15.5f)
                                            .build()));


                }
            });
            valueAnimator.start();
            handler.postDelayed(this, 3000);

        }
    };

    private void startCarAnimation(Double latitude, Double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);

        carMarker = googleMap.addMarker(new MarkerOptions().position(latLng).
                flat(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.new_car_small)));


        index = -1;
        next = 1;
        handler.postDelayed(staticCarRunnable, 3000);
    }

    void stopRepeatingTask() {

        if (staticCarRunnable != null) {
            handler.removeCallbacks(staticCarRunnable);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRepeatingTask();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setTrafficEnabled(false);
        googleMap.setIndoorEnabled(false);
        googleMap.setBuildingsEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Double getDistance(Double lat1, Double lon1, Double lat2, Double lon2)
    {
        final int R = 6371;
        Double latDistance = toRad(lat2-lat1);
        Double lonDistance = toRad(lon2-lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = R * c;
        return distance;

    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    private void getDriverLocationUpdate() {

        StringRequest request = new StringRequest(Request.Method.POST, URL_DRIVER_LOCATION_ON_RIDE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("PartnerInfoRes::", response);
                JSONObject jObj;
                try {
                    jObj = new JSONObject(response);
                    String ApiSuccess = jObj.getString("success");
                    if (ApiSuccess.trim().equals("true")) {

                        JSONObject jObj2 = new JSONObject(jObj.getString("data"));
                        JSONObject jObj3 = new JSONObject(jObj2.getString("driver"));

                        startLatitude = Double.valueOf(jObj3.getString("lat"));
                        startLongitude = Double.valueOf(jObj3.getString("lng"));

                        Log.d(TAG, startLatitude + "--" + startLongitude);

                        if (isFirstPosition) {
                            startPosition = new LatLng(startLatitude, startLongitude);

                            carMarker = googleMap.addMarker(new MarkerOptions().position(startPosition).
                                    flat(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.new_car_small)));
                            carMarker.setAnchor(0.5f, 0.5f);

                            googleMap.moveCamera(CameraUpdateFactory
                                    .newCameraPosition
                                            (new CameraPosition.Builder()
                                                    .target(startPosition)
                                                    .zoom(15.5f)
                                                    .build()));

                            isFirstPosition = false;

                        } else {
                            endPosition = new LatLng(startLatitude, startLongitude);

                            Log.d(TAG, startPosition.latitude + "--" + endPosition.latitude + "--Check --" + startPosition.longitude + "--" + endPosition.longitude);

                            if ((startPosition.latitude != endPosition.latitude) || (startPosition.longitude != endPosition.longitude)) {

                                Log.e(TAG, "NOT SAME");
                                startBikeAnimation(startPosition, endPosition);

                            } else {

                                Log.e(TAG, "SAMME");
                            }
                        }

                    }
                    if (jObj.getString("message").trim().equals("Unauthorized")) {

                        Log.e(TAG, "--- Unauthorized ---");

                    }

                } catch (Exception e) {
                    Log.d("jsonError::", e + "");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //params.put("driver_id", driverId);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //Log.d("acc::", ClientAccToken);
                //params.put("authorization", "ClientAccToken");

                return params;
            }

        };

        App.getAppInstance().addToRequestQueue(request, TAG);
    }

    private void startBikeAnimation(final LatLng start, final LatLng end) {

        if(isCompleted == true)
        {
            stopHandler();
        }
        else {

            Log.i(TAG, "startBikeAnimation called...");

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(ANIMATION_TIME_PER_ROUTE);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {

                    //LogMe.i(TAG, "Car Animation Started...");
                    v = valueAnimator.getAnimatedFraction();
                    lng = v * end.longitude + (1 - v)
                            * start.longitude;
                    lat = v * end.latitude + (1 - v)
                            * start.latitude;

                    LatLng newPos = new LatLng(lat, lng);
                    carMarker.setPosition(newPos);
                    carMarker.setAnchor(0.5f, 0.5f);
                    carMarker.setRotation(getBearing(start, end));

                    // todo : Shihab > i can delay here
                    googleMap.moveCamera(CameraUpdateFactory
                            .newCameraPosition
                                    (new CameraPosition.Builder()
                                            .target(newPos)
                                            .zoom(15.5f)
                                            .build()));

                    startPosition = carMarker.getPosition();

                }

            });
            valueAnimator.start();
        }
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {

               // getDriverLocationUpdate();
                getStarted();


            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            handler.postDelayed(mStatusChecker, 30000);

        }
    };

    public void stopHandler()
    {
        System.out.println("Finally I am called");
        handler.removeCallbacks(mStatusChecker);
    }

    void startGettingOnlineDataFromCar() {
        handler.post(mStatusChecker);
    }

    void CreatePolyLineOnly() {

        googleMap.clear();

        polyLineList = MapUtils.decodePoly(polyLine);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : polyLineList) {
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
        googleMap.animateCamera(mCameraUpdate);

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLACK);
        polylineOptions.width(5);
        polylineOptions.startCap(new SquareCap());
        polylineOptions.endCap(new SquareCap());
        polylineOptions.jointType(ROUND);
        polylineOptions.addAll(polyLineList);
        greyPolyLine = googleMap.addPolyline(polylineOptions);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(mStatusChecker);
    }
}
