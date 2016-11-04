package thegroup.snakego;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import thegroup.snakego.models.User;
import thegroup.snakego.observers.EntitySpawnerObserver;
import thegroup.snakego.services.EntitySpawner;

import java.util.LinkedList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        OnMapLoadedCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, SensorEventListener, GoogleMap.OnCameraIdleListener {

    private GoogleMap map;

    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int REQUEST_FINE_LOCATION = 0;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    protected LocationManager locationManager;
    private Button optionsButton;
    private Polyline polyline;

    //Accelerator Global Variables
    private SensorManager sensorManager;
    private Sensor accelerometer;
    long lastTime;
    float lastX;
    float lastY;
    float lastZ;
    private static final int THRESHOLD = 600;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lastTime = System.currentTimeMillis();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        if (getIntent().getBooleanExtra("quitclick", false)) {
            finish();
            return;
        }

        optionsButton = (Button) findViewById(R.id.icon_button);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsButtonPressed();
            }
        });

        if (this.googleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to
            // implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            this.googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();

            this.googleApiClient.connect();
        }

        this.locationRequest = new LocationRequest()
                .setInterval(10 * 1000)
                .setFastestInterval(3 * 1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(0.5F);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Accelerometer Setup
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onOptionsButtonPressed() {
        //snakeOptions();  // comment this and uncomment bottom to switch back to activity view
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.map.setMyLocationEnabled(true);
        } else {
            this.requestLocationPermission();
        }

        float maxZoom = this.map.getMaxZoomLevel();
        this.map.setMaxZoomPreference(maxZoom - 1);
        this.map.setMinZoomPreference(maxZoom - 1);

        this.map.setOnMapLoadedCallback(this);
    }

    protected void requestLocationPermission() {
        // check for correct API version usage
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        }
    }

    @Override
    public void onMapLoaded() {
        EntitySpawner spawner = new EntitySpawner(this.map.getProjection().getVisibleRegion().latLngBounds);
        new EntitySpawnerObserver(spawner, this.map);
    }

    @Override
    public void onCameraIdle() {

    }

    // part of the GoogleApiClient
    @Override
    public void onConnected(Bundle connectionHint) {
        this.locationUpdateRequest();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationRequest = new LocationRequest();

            locationRequest.setInterval(10 * 1000); //every 10 second
            locationRequest.setFastestInterval(1000); //checks other apps to see if we can get better location
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //burn the battery
            locationRequest.setSmallestDisplacement(0.5F); // meter

            LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, locationRequest, this);
        } else {
            this.requestLocationPermission();
        }
    }

    private void locationUpdateRequest() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, locationRequest, this);
        } else {
            this.requestLocationPermission();
        }
    }

    // part of the GoogleApiClient
    @Override
    public void onConnectionSuspended(int integer) {

    }

    // part of the GoogleApiClient
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException exception) {
                // Google Play services canceled the original PendingIntent
                exception.printStackTrace();
            }
        } else {
            String error = "Location services connection failed with code " + connectionResult.getErrorCode();

            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    // location listener
    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        //My addition to code here ZK
        User.get().onLocationUpdated(latLng);
        drawSnake();

        // map.addMarker(new MarkerOptions().position(latLng).title("Snake was here"));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    public void drawSnake() {
        LinkedList<LatLng> snake = User.get().getSnake();
        PolylineOptions polySnake = new PolylineOptions();
        for (LatLng l : snake) {
            polySnake.add(l);
        }
        if (polyline != null) {
            polyline.remove();
        }
        polyline = map.addPolyline(polySnake);
    }


    //Accelerometer Methods Below
    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentTime = System.currentTimeMillis();
        long diffTime = currentTime - lastTime;
        if (diffTime < 1000) {
            return;
        }
        lastTime = currentTime;
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //Log.v("onSensorChanged", "Sensor information changed!");
            //Input more info here
            //Reference Website: https://www.sitepoint.com/using-android-sensors-application/
            float currX = event.values[0];
            float currY = event.values[1];
            float currZ = event.values[2];

            lastX = currX;
            lastY = currY;
            lastZ = currZ;

            float speed = Math.abs(currX + currY + currZ - lastX - lastY - lastZ) / diffTime * 10000;
            User.get().setIsMoving(speed > THRESHOLD);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStart() {  // BUG: Map doesn't OnLoad ever again from this point so spawner never reinitialized
        //TODO FIX SPAWNER BUG
        super.onStart();
        this.googleApiClient.connect();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(this.googleApiClient, getIndexApiAction());
    }

    @Override
    protected void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        this.googleApiClient.disconnect();
        AppIndex.AppIndexApi.end(this.googleApiClient, getIndexApiAction());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // if map is lost we need to handle it here
        // googleApiClient.connect();
        if (this.googleApiClient.isConnected()) {
            locationUpdateRequest();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (this.googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, this);
            //googleApiClient.disconnect();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Maps Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        map.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No permission, no play", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    public void snakeOptions() {
        DialogFragment newFragment = new OptionsDialogFragment();
        newFragment.show(getSupportFragmentManager(), "snakeOps");
    }
}
