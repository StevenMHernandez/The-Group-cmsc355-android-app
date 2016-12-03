package thegroup.snakego;

import com.google.android.gms.appindexing.AppIndex;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
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

import thegroup.snakego.elements.SnakeTextView;
import thegroup.snakego.models.User;
import thegroup.snakego.observers.EntitySpawnerObserver;
import thegroup.snakego.observers.UserObserver;
import thegroup.snakego.services.EntitySpawner;
import thegroup.snakego.utils.Utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        OnMapLoadedCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, SensorEventListener, GoogleMap.OnCameraIdleListener,
        PropertyChangeListener {

    private EntitySpawner spawner;
    private Context context;

    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int REQUEST_FINE_LOCATION = 0;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    protected LocationManager locationManager;

    private GoogleMap map;
    private Button optionsButton;
    public SnakeTextView currentScore;

    public static boolean PropertyChangeFlag;
    public static boolean jsonFlag;

    private ArrayList<Polygon> snakeSegments = new ArrayList<Polygon>();

    //Accelerator Global Variables
    private SensorManager sensorManager;
    private Sensor accelerometer;
    long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lastTime = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

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

        currentScore = (SnakeTextView) findViewById(R.id.score);
        currentScore.setText(Integer.toString(User.get().getScore()));

        if (this.googleApiClient == null) {
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

        User.get().addChangeListener(new UserObserver(this));
        User.get().addChangeListener(this);
    }

    public void onOptionsButtonPressed() {
        //snakeOptions();  // comment this and uncomment bottom to switch back to activity view
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        map.getUiSettings().setMyLocationButtonEnabled(true);

        try {

            // this disables the 1,2,3,lg indoor map zoom options
            map.getUiSettings().setIndoorLevelPickerEnabled(false);
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.json_style));
            jsonFlag = true;

            if (!success) {
                Log.e("MapsActivityRaw", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException exception) {
            Log.e("MapsActivityRaw", "Can't find style.", exception);
        }

        int grantedPermission = ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (grantedPermission == PackageManager.PERMISSION_GRANTED) {
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
            String[] permissionString = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissionString, REQUEST_FINE_LOCATION);
        }
    }

    @Override
    public void onMapLoaded() {
        LatLngBounds latLngBounds = this.map.getProjection().getVisibleRegion().latLngBounds;

        this.spawner = new EntitySpawner(latLngBounds);

        new EntitySpawnerObserver(this.spawner, this.map);

    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("score")) {
            this.updateScore((int)event.getOldValue(), (int)event.getNewValue());

            for (int i = 6; i > 0; i--) {
                if ((int) event.getNewValue() > i * 300 && (int) event.getOldValue() < i * 300) {
                        Resources res = getResources();
                        String[] milestones = res.getStringArray(R.array.milestones);
                        Toast.makeText(context, milestones[i], Toast.LENGTH_SHORT).show();
                        PropertyChangeFlag = true;
                        break;
                }
            }
        }
    }

    public void updateScore(int oldScore, int newScore) {
        currentScore.setText(Integer.toString(newScore));

        if (oldScore < newScore) {
            this.blinkScoreText(Color.BLUE);
        } else if (oldScore > newScore) {
            this.blinkScoreText(Color.RED);
        }

        if (newScore - oldScore == 215) {
            Intent intent = new Intent(this, SnakeSpace.class);
            startActivity(intent);
        }
    }

    public void blinkScoreText(int color) {
        ObjectAnimator animator = ObjectAnimator
                .ofInt(currentScore, "textColor", color, Color.WHITE);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }

    @Override
    public void onCameraIdle() {
    }

    // part of the GoogleApiClient
    @Override
    public void onConnected(Bundle connectionHint) {
        this.locationUpdateRequest();
        //Fixed line longer than 100 characters
        String perLoc = Manifest.permission.ACCESS_FINE_LOCATION;

        if (ActivityCompat.checkSelfPermission(this, perLoc) == PackageManager.PERMISSION_GRANTED) {
            locationRequest = new LocationRequest();
            //every 10 second
            locationRequest.setInterval(10 * 1000);
            //checks other apps to see if we can get better location
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //burn the battery
            locationRequest.setSmallestDisplacement(0.5F); // meter

            LocationServices.FusedLocationApi
                    .requestLocationUpdates(this.googleApiClient, locationRequest, this);
        } else {
            this.requestLocationPermission();
        }
    }

    private void locationUpdateRequest() {
        String perLoc = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.checkSelfPermission(this, perLoc) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(this.googleApiClient, locationRequest, this);
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
                connectionResult
                        .startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException exception) {
                // Google Play services canceled the original PendingIntent
                exception.printStackTrace();
            }
        } else {
            String error = "Location services connection failed with code "
                    + connectionResult.getErrorCode();

            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    // location listener
    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        User.get().onLocationUpdated(latLng);

        drawSnake();

        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        if (null != spawner) {
            spawner.updateLocation(this.map.getProjection().getVisibleRegion().latLngBounds);
        }
    }

    public void drawSnake() {

        for (Polygon p : snakeSegments) {
            p.remove();
        }

        snakeSegments.clear();

        LatLng prev = User.get().getUserLocationHistory().getFirst();
        for (int i = 1; i<User.get().getUserLocationHistory().size(); i++) {
            LatLng current = User.get().getUserLocationHistory().get(i);

            for (PolygonOptions po : Utils.getRectanglesFromLine(prev, current)) {
                snakeSegments.add(map.addPolygon(po));
            }
            prev = current;
        }

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
            float currX = event.values[0];
            float currY = event.values[1];
            float currZ = event.values[2];

            User.get().accelerometerChanged(currX, currY, currZ, diffTime);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Not needed for now
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.googleApiClient.connect();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    protected void onStop() {
        super.onStop();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    int grantedPermission = ActivityCompat
                            .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

                    if (grantedPermission == PackageManager.PERMISSION_GRANTED) {
                        map.setMyLocationEnabled(true);
                    }
                } else {
                    String notification = "No permission, no play";
                    Toast.makeText(getApplicationContext(), notification, Toast.LENGTH_LONG).show();
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
