package com.pandu.wisata;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.pandu.wisata.fragment.MainFragment;
import com.pandu.wisata.fragment.OtherTempFragment;
import com.pandu.wisata.gcm.GcmRegistrationIntentService;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "PanduWisata";

	private DrawerLayout	mDrawerLayout;
	private NavigationView	mNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initializing Toolbar and setting it as the actionbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		//ActionBar actionBar = getSupportActionBar();
	    //actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher);
	    //actionBar.setDisplayHomeAsUpEnabled(true);
	    //actionBar.setTitle("Hallo");

		// Initializing Drawer Layout and ActionBarToggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

		//Initializing NavigationView
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationViewItemSelectedListener());

        /// Switch
        SwitchCompat item = (SwitchCompat) mNavigationView.getMenu().getItem(1).getActionView();
        item.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                	Toast.makeText(MainActivity.this, "Your location will be detemined by GPS", Toast.LENGTH_SHORT).show();
                else
                	Toast.makeText(MainActivity.this, "Select your location manually using map", Toast.LENGTH_SHORT).show();
            }
        });
        ///

        // Initializing ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, android.R.string.yes, android.R.string.no){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
 
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }        	
        };
        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        // Set default fragment
        getSupportActionBar().setSubtitle("All events");

        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager()
    	.beginTransaction()
    	.replace(R.id.frame, mainFragment)
    	.commit();

	    // Check GCM status
	    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	    String token = sharedPreferences.getString("gcmToken", "");
	    //if (token.length()==0)  {
	    	Log.d(TAG, "GCM token is empty");
	    	Intent gcmRegisterIntent = new Intent(MainActivity.this, GcmRegistrationIntentService.class);
	    	startService(gcmRegisterIntent);
	    //} else Log.d(TAG, "GCM token is " + token);

	    /*
	    boolean tokenShared = sharedPreferences.getBoolean("sentTokenToServer", false);
	    if (!tokenShared) {
	    	Log.d(TAG, "GCM token is not shared");
	    	// Share token to server
	    } else Log.d(TAG, "GCM token already shared");
	    */
	}
	
	@Override
	public void onBackPressed() {
	    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
	        mDrawerLayout.closeDrawer(GravityCompat.START);
	    } else {
	        super.onBackPressed();
	    }
	}

	private class NavigationViewItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

		@Override
		public boolean onNavigationItemSelected(MenuItem item) {
			int itemId = item.getItemId();
			Intent intent;
			String subTitle = "";
			OtherTempFragment otherFragment;
			switch (itemId) {
				case R.id.login:
					intent = new Intent(MainActivity.this, LoginActivity.class);
					startActivity(intent);
					mDrawerLayout.closeDrawers();
					return false;

				case R.id.gps_location:
					return false;

				case R.id.map_location:
					intent = new Intent(MainActivity.this, SelectMapActivity.class);
					startActivity(intent);
					mDrawerLayout.closeDrawers();
					return false;

				case R.id.events:
					getSupportActionBar().setSubtitle("All events");
			        MainFragment mainFragment = new MainFragment();
			        getSupportFragmentManager()
				    	.beginTransaction()
				    	.replace(R.id.frame, mainFragment)
				    	.commit();
					break;

        		case R.id.ethnic_event:
        			getSupportActionBar().setSubtitle("Ethnic and Cultural Events");
			        otherFragment = new OtherTempFragment(itemId);
			        getSupportFragmentManager()
				    	.beginTransaction()
				    	.replace(R.id.frame, otherFragment)
				    	.commit();
        			break;
        		case R.id.other_event:
        			getSupportActionBar().setSubtitle("Other Event");
			        otherFragment = new OtherTempFragment(itemId);
			        getSupportFragmentManager()
				    	.beginTransaction()
				    	.replace(R.id.frame, otherFragment)
				    	.commit();
        			break;
        		case R.id.objects:
        			getSupportActionBar().setSubtitle("Interesting Places");
			        otherFragment = new OtherTempFragment(itemId);
			        getSupportFragmentManager()
				    	.beginTransaction()
				    	.replace(R.id.frame, otherFragment)
				    	.commit();
        			break;
        		case R.id.hotels:
        			getSupportActionBar().setSubtitle("Hotels");
			        otherFragment = new OtherTempFragment(itemId);
			        getSupportFragmentManager()
				    	.beginTransaction()
				    	.replace(R.id.frame, otherFragment)
				    	.commit();
        			break;
        		case R.id.restaurants:
        			getSupportActionBar().setSubtitle("Cafes \u0026 Restaurants");
			        otherFragment = new OtherTempFragment(itemId);
			        getSupportFragmentManager()
				    	.beginTransaction()
				    	.replace(R.id.frame, otherFragment)
				    	.commit();
        			break;
        		case R.id.guides:
        			getSupportActionBar().setSubtitle("Profesional Guide");
			        otherFragment = new OtherTempFragment(itemId);
			        getSupportFragmentManager()
				    	.beginTransaction()
				    	.replace(R.id.frame, otherFragment)
				    	.commit();
        			break;
        		case R.id.rental:
        			getSupportActionBar().setSubtitle("Car \u0026 Bike Rental");
			        otherFragment = new OtherTempFragment(itemId);
			        getSupportFragmentManager()
				    	.beginTransaction()
				    	.replace(R.id.frame, otherFragment)
				    	.commit();
        			break;
			}

			//Closing drawer on item click
            mDrawerLayout.closeDrawers();
            
			return true;//false;
		}
		
	}

	// UI
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		setupTransparentSystemBarsForLmp();
	}

	public static boolean isLmpOrAbove() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	}

	/**
	 * Sets up transparent navigation and status bars in LMP. This method is a
	 * no-op for other platform versions.
	 */
	@TargetApi(19)
	private void setupTransparentSystemBarsForLmp() {
		// TODO(sansid): use the APIs directly when compiling against L sdk.
		// Currently we use reflection to access the flags and the API to set
		// the transparency
		// on the System bars.
		if (isLmpOrAbove()) {
			try {
				getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				getWindow().getDecorView().setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_LAYOUT_STABLE
								| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
				Field drawsSysBackgroundsField = WindowManager.LayoutParams.class
						.getField("FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
				getWindow().addFlags(drawsSysBackgroundsField.getInt(null));

				Method setStatusBarColorMethod = Window.class
						.getDeclaredMethod("setStatusBarColor", int.class);
				Method setNavigationBarColorMethod = Window.class
						.getDeclaredMethod("setNavigationBarColor", int.class);
				setStatusBarColorMethod.invoke(getWindow(), Color.TRANSPARENT);
				setNavigationBarColorMethod.invoke(getWindow(),
						Color.TRANSPARENT);
			} catch (NoSuchFieldException e) {

			} catch (NoSuchMethodException ex) {

			} catch (IllegalAccessException e) {

			} catch (IllegalArgumentException e) {

			} catch (InvocationTargetException e) {

			} finally {
			}
		}

	}
}
