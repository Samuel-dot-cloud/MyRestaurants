package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import network.Business;
import network.Category;
import com.studiofive.myrestaurants.MyRestaurantsArrayAdapter;
import com.studiofive.myrestaurants.R;
import network.YelpApi;
import network.YelpClient;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RestaurantsActivity extends AppCompatActivity {
    public static final String TAG = RestaurantsActivity.class.getSimpleName();
    @BindView(R.id.locationTextView)
    TextView mLocationTextView;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;
//private ArrayList<Restaurant> restaurants = new ArrayList<>();

//    private String[] restaurants = new String[] {"Mi Mero Mole", "Mother's Bistro", "Life of Pie", "Screen Door", "Luc Lac", "Sweet Basil", "Slappy Cakes", "Equinox", "Miss Delta's", "Andina", "Lardo", "Portland City Grill", "Fat Head's Brewery", "Chipotle", "Subway" };
//    private String[] cuisines = new String[] {"Vegan Food", "Breakfast", "Fishs Dishs", "Scandinavian", "Coffee", "English Food", "Burgers", "Fast Food", "Noodle Soups", "Mexican", "BBQ", "Cuban", "Bar Food", "Sports Bar", "Breakfast", "Mexican" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);


//        MyRestaurantsArrayAdapter adapter = new MyRestaurantsArrayAdapter(this, android.R.layout.simple_list_item_1, restaurants, cuisines);
//        mListView.setAdapter(adapter);
//
        //Setting a toaster
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String restaurant = ((TextView) view).getText().toString();
                Toast.makeText(RestaurantsActivity.this, restaurant, Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        mLocationTextView.setText("Here are all the restaurants near:" + location);
        //calling method for getting restaurants in create method
//        getRestaurants(location);

        YelpApi client = YelpClient.getClient();

        Call call = (Call) client.getRestaurants(location, "restaurants");


        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                hideProgressBar();

                if (response.isSuccessful()) {
                    List<Business> restaurantsList = response.body().getBusinesses();

                    String[] restaurants = new String[restaurantsList.size()];
                    String[] categories = new String[restaurantsList.size()];

                    for (int i = 0; i < restaurants.length; i++) {
                        restaurants[i] = restaurantsList.get(i).getName();
                    }
                    for (int i = 0; i < categories.length; i++) {
                        Category category = restaurantsList.get(i).getCategories().get(0);
                        categories[i] = category.getTitle();
                    }

                    ArrayAdapter adapter = new MyRestaurantsArrayAdapter(RestaurantsActivity.this, android.R.layout.simple_list_item_1, restaurants, categories);
                    mListView.setAdapter(adapter);

                    showRestaurants();
                } else {
                    showUnsuccessfulMessage();
                }

            }

            @Override
            public void onFailure(Call call, IOException e) {
//                Log.e(TAG, "onFailure: ", t);
                hideProgressBar();
                showFailureMessage();
            }
        });
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showRestaurants() {
        mListView.setVisibility(View.VISIBLE);
        mLocationTextView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}




//        private void getRestaurants(String location) {
//            final YelpService yelpService = new YelpService();
//            yelpService.findRestaurants(location, new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    restaurants = yelpService.processResults(response);
//
//                    RestaurantsActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            String[] restaurantNames = new String[restaurants.size()];
//                            for (int i = 0; i < restaurantNames.length; i++){
//                                restaurantNames[i] = restaurants.get(i).getName();
//                            }
//
//                            ArrayAdapter adapter = new ArrayAdapter(RestaurantsActivity.this, android.R.layout.simple_list_item_1, restaurantNames);
//                            mListView.setAdapter(adapter);
//                            for (Restaurant restaurant : restaurants){
//                                Log.d(TAG, "Name:" + restaurant.getName());
//                                Log.d(TAG, "Phone:" + restaurant.getPhone());
//                                Log.d(TAG, "Website:" + restaurant.getWebsite());
//                                Log.d(TAG, "Image url:" + restaurant.getImageUrl());
//                                Log.d(TAG, "Rating:" + Double.toString(restaurant.getRating()));
//                                Log.d(TAG, "Address:" + android.text.TextUtils.join(",", restaurant.getAddress()));
//                                Log.d(TAG, "Categories:" + restaurant.getCategories().toString());
//                            }
//                        }
//                    });
//
////                    try {
////                        String jsonData = response.body().string();
////                        Log.v(TAG, jsonData);
////                    } catch (IOException e){
////                        e.printStackTrace();
////                    }
//                }
//
//            });
//        }



