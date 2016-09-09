package com.test.myapplication.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;
import com.test.myapplication.R;

/**
 * Created by NehaRege on 8/22/16.
 */
public class DetailActivity extends AppCompatActivity {

    private static final String TAG="DetailActivity";

    private ImageView imageView;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewWhen;
    private TextView textViewAdd1;
    private TextView textViewCategory;

    private FloatingActionMenu fam;
    private FloatingActionButton fab1, fab2, fab3;

    private Button locationButton;

//    private MapView mMapView;

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    MessageDialog messageDialog;

    public String latitude, longitude;
    public String eventLatitude, eventLongitude;

//    MapView mapView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        this.getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE
//
//
//        );

        setContentView(R.layout.activity_detail);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        latitude = sharedPreferences.getString(getString(R.string.key_location_services_latitude),null);
        longitude = sharedPreferences.getString(getString(R.string.key_location_services_longitude),null);



        initializeFacebookStuff();

        initializeViews();
//
//        mMapView = (MapView) findViewById(R.id.detail_map);
//        mMapView.onCreate(savedInstanceState);
//
//        mMapView.getMapAsync(DetailActivity.this);

//
//        // Gets the MapView from the XML layout and creates it
//        mapView = (MapView) v.findViewById(R.id.mapview);
//        mapView.onCreate(savedInstanceState);
//
//        // Gets to GoogleMap from the MapView and does initialization stuff
//        map = mapView.getMap();
//        map.getUiSettings().setMyLocationButtonEnabled(false);
//        map.setMyLocationEnabled(true);
//
//        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
//        try {
//            MapsInitializer.initialize(this.getActivity());
//        } catch (GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//        }
//
//        // Updates the location and zoom of the MapView
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
//        map.animateCamera(cameraUpdate);

//        initializeMapFragment();

        final Intent intent = getIntent();

        eventLatitude = intent.getStringExtra(getString(R.string.key_event_latitude));
        eventLongitude = intent.getStringExtra(getString(R.string.key_event_longitude));

        setViews(intent);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(intent.getStringExtra(getString(R.string.key_event_url))!=null) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, intent.getStringExtra(getString(R.string.key_event_url)));
                    startActivity(Intent.createChooser(shareIntent, "Share"));
                }

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(intent.getStringExtra(getString(R.string.key_event_url))!=null &
                        intent.getStringExtra(getString(R.string.key_event_title))!=null) {

                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle(intent.getStringExtra(getString(R.string.key_event_title)))
                            .setContentUrl(Uri.parse(intent.getStringExtra(getString(R.string.key_event_url))))
                            .build();

                    shareDialog.show(linkContent);
                }

            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MessageDialog.canShow(ShareLinkContent.class)) {

                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle(intent.getStringExtra(getString(R.string.key_event_title)))
                            .setContentDescription("description"/*intent.getStringExtra(getString(R.string.key_event_description))*/)
                            .setContentUrl(Uri.parse(getString(R.string.key_event_url)))
                            .build();

                    messageDialog.show(linkContent);

                }
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(DetailActivity.this,LocationActivity.class);
                intent1.putExtra(getString(R.string.key_event_latitude),eventLatitude);
                intent1.putExtra(getString(R.string.key_event_longitude),eventLongitude);
                intent1.putExtra(getString(R.string.key_event_short_address),
                        intent.getStringExtra(getString(R.string.key_event_short_address)));
                startActivity(intent1);


            }
        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mMapView.onResume();
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
////        String text = "12.34"; // example String
////        double value = Double.parseDouble(text);
//
////        private LatLngBounds AUSTRALIA = new LatLngBounds(
////                new LatLng(-44, 113), new LatLng(-10, 154));
//
//
//
//        LatLngBounds latLngBounds = new LatLngBounds(
//                new LatLng(Double.parseDouble(eventLatitude),Double.parseDouble(eventLongitude)),
//                new LatLng(Double.parseDouble(eventLatitude),Double.parseDouble(eventLongitude))
//        );
//
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(Double.parseDouble(eventLatitude),Double.parseDouble(eventLongitude)))
//                .title("Marker")
//        );
//
////        mMap.setLatLngBoundsForCameraTarget(ADELAIDE);
//
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngBounds.getCenter(),10));
//
////        googleMap.setLatLngBoundsForCameraTarget(latLngBounds);
//
//
////        @Override
////        public void onMapReady(GoogleMap map) {
////            map.addMarker(new MarkerOptions()
////                    .position(new LatLng(0, 0))
////                    .title("Marker"));
////        }
//    }
//
//    @Override
//    protected void onPause() {
//        mMapView.onPause();
//        super.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        mMapView.onDestroy();
//        super.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mMapView.onLowMemory();
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mMapView.onSaveInstanceState(outState);
//    }

//    public void initializeMapFragment() {
//
//        SupportMapFragment mapFragment =
//                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
////        SupportMapFragment fragmentManager = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
////
////        GoogleMap map= fragmentManager.getMap()
////        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////        fragmentTransaction.add(R.id.rootFrame, map);
////        fragmentTransaction.commit();
//
////        SupportMapFragment mapFragment =
////                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
////        mapFragment.getMapAsync(this);
//
////        FragmentManager fm = getChildFragmentManager();
////        SupportMapFragment supportMapFragment =  SupportMapFragment.newInstance();
////        fm.beginTransaction().replace(R.id.mapContainer, supportMapFragment).commit();
//
//
//
//
////        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
////        supportMapFragment.getMapAsync(DetailActivity.this);
//
////        mMapFragment = MapFragment.newInstance();
////        FragmentTransaction fragmentTransaction =
////                getFragmentManager().beginTransaction();
////        fragmentTransaction.add(R.id.my_container, mMapFragment);
////        fragmentTransaction.commit();
//    }


    public void initializeViews() {

        imageView = (ImageView) findViewById(R.id.detail_image);
        textViewTitle = (TextView) findViewById(R.id.detail_title);
        textViewDescription = (TextView) findViewById(R.id.detail_description);
        textViewWhen = (TextView) findViewById(R.id.detail_when);
        textViewAdd1 = (TextView) findViewById(R.id.detail_add1);
        textViewCategory = (TextView) findViewById(R.id.detail_category);

        fam = (FloatingActionMenu) findViewById(R.id.floating_action_menu);
        fab1 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item1);
        fab2 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item2_fb);
        fab3 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item3_messenger);

        locationButton = (Button) findViewById(R.id.detail_button_location);

    }

    public void initializeFacebookStuff() {

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        messageDialog = new MessageDialog(this);

    }

    public void setViews(Intent intent) {

        textViewTitle.setText(intent.getStringExtra(getString(R.string.key_event_title)));

//        textViewDescription.setText(intent.getStringExtra(getString(R.string.key_event_description)));

//        final  String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.jpg";


//        final String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.jpg";
//
//        ImageGetter imageGetter = new ImageGetter() {
//            public Drawable getDrawable(String source) {
//                Drawable d = Drawable.createFromPath(path);
//                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//                return d;
//            }
//        };
//
//        Spanned htmlstr= Html.fromHtml("<img src='" + path + "'/>", imageGetter, null);
//        TextView out_unit1 = (TextView) findViewById(R.id.mTxtViewCm2);
//        out_unit1.setText(htmlstr);



//        txtimg.setText(Html.fromHtml(htmlstr, new ImageGetter() {
//            @Override
//            public Drawable getDrawable(String source) {
//                String path =  source;
//
//                Drawable bmp = Drawable.createFromPath(path);
//                bmp.setBounds(0, 0, bmp.getIntrinsicWidth(), bmp.getIntrinsicHeight());
//
//                return bmp;
//            }
//        }, null));

//        textViewDescription.setText(Html.fromHtml(intent.getStringExtra(getString(R.string.description_html)),
//                new Html.ImageGetter() {
//                    @Override
//                    public Drawable getDrawable(String s) {
//                        String path = s;
//
//                        Drawable bmp = Drawable.createFromPath(path);
//                        bmp.setBounds(0,0,bmp.getIntrinsicWidth(),bmp.getIntrinsicHeight());
//
//                        return bmp;
//                    }
//                },
//        null));

        textViewDescription.setText(Html.fromHtml(intent.getStringExtra(getString(R.string.description_html))));
//        textViewDescription.setText(intent.getStringExtra(getString(R.string.description_html)));
        textViewDescription.setMovementMethod(LinkMovementMethod.getInstance());

        textViewWhen.setText(intent.getStringExtra(getString(R.string.key_event_time_date)));
        textViewAdd1.setText(intent.getStringExtra(getString(R.string.key_event_full_address)));
        textViewCategory.setText(intent.getStringExtra(getString(R.string.key_event_category)));

        YoYo.with(Techniques.RollIn)
                .duration(600)
                .playOn(findViewById(R.id.detail_image));

        if (intent.getStringExtra(getString(R.string.key_event_image)) != null) {
            Picasso.with(getApplicationContext()).load(intent.getStringExtra(getString(R.string.key_event_image))).into(imageView);

        } else {
            imageView.setImageResource(R.drawable.no_img);
        }

//        Picasso.with(getApplicationContext()).load(intent.getStringExtra(getString(R.string.key_event_image))).into(imageView);

    }

}
