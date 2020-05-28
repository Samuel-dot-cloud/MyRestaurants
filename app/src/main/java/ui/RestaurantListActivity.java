package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import adapters.RestaurantListAdapter;
import models.Business;

import com.studiofive.myrestaurants.R;
import network.YelpApi;
import network.YelpBusinessesSearchResponse;
import network.YelpBusinessesSearchResponse$$Parcelable;
import network.YelpClient;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantListActivity extends AppCompatActivity {
    public static final String TAG = RestaurantListActivity.class.getSimpleName();
    private RestaurantListAdapter mAdapter;
//    @BindView(R.id.locationTextView)
//    TextView mLocationTextView;
//    @BindView(R.id.listView)
//    ListView mListView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;
    public List<Business> restaurants;
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
//        //Setting a toaster
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String restaurant = ((TextView) view).getText().toString();
//                Toast.makeText(RestaurantsActivity.this, restaurant, Toast.LENGTH_LONG).show();
//            }
//        });

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
//        mLocationTextView.setText("Here are all the restaurants near:" + location);
        //calling method for getting restaurants in create method
//        getRestaurants(location);


        //creating a client object and using it to make a request to the API
        YelpApi client = YelpClient.getClient();

        retrofit2.Call<YelpBusinessesSearchResponse> call = client.getRestaurants(location, "restaurants");


//        call.enqueue(new Callback<YelpBusinessesSearchResponse>() {
//            public void onResponse(retrofit2.Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) throws IOException {
//
//                hideProgressBar();
//
//                if (response.isSuccessful()) {
////                    List<Business> restaurantsList = response.body().getBusinesses();
//                    restaurants = response.body().getBusinesses();
//                    mAdapter = new RestaurantListAdapter(RestaurantListActivity.this, restaurants);
//
//                    mRecyclerView.setAdapter(mAdapter);
//                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RestaurantListActivity.this);
//                    mRecyclerView.setLayoutManager(layoutManager);
//                    mRecyclerView.setHasFixedSize(true);
//
////                    String[] restaurants = new String[restaurantsList.size()];
////                    String[] categories = new String[restaurantsList.size()];
////
////                    for (int i = 0; i < restaurants.length; i++) {
////                        restaurants[i] = restaurantsList.get(i).getName();
////                    }
////                    for (int i = 0; i < categories.length; i++) {
////                        Category category = restaurantsList.get(i).getCategories().get(0);
////                        categories[i] = category.getTitle();
////                    }
////
////                    ArrayAdapter adapter = new MyRestaurantsArrayAdapter(RestaurantsActivity.this, android.R.layout.simple_list_item_1, restaurants, categories);
////                    mListView.setAdapter(adapter);
//
//                    showRestaurants();
//                } else {
//                    showUnsuccessfulMessage();
//                }
//
//            }
//
//            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
////                Log.e(TAG, "onFailure: ", t);
//                hideProgressBar();
//                showFailureMessage();
//            }
//        });
//    }

        call.enqueue(new Callback<YelpBusinessesSearchResponse>() {
            @Override
            public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {

                hideProgressBar();

                if (response.isSuccessful()) {
//                    List<Business> restaurantsList = response.body().getBusinesses();
                    restaurants = response.body().getBusinesses();
                    mAdapter = new RestaurantListAdapter(RestaurantListActivity.this, restaurants);

                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RestaurantListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

//                    String[] restaurants = new String[restaurantsList.size()];
//                    String[] categories = new String[restaurantsList.size()];
//
//                    for (int i = 0; i < restaurants.length; i++) {
//                        restaurants[i] = restaurantsList.get(i).getName();
//                    }
//                    for (int i = 0; i < categories.length; i++) {
//                        Category category = restaurantsList.get(i).getCategories().get(0);
//                        categories[i] = category.getTitle();
//                    }
//
//                    ArrayAdapter adapter = new MyRestaurantsArrayAdapter(RestaurantsActivity.this, android.R.layout.simple_list_item_1, restaurants, categories);
//                    mListView.setAdapter(adapter);

                    showRestaurants();
                } else {
                    showUnsuccessfulMessage();
                }

            }

            @Override
            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
                hideProgressBar();
                showFailureMessage();

            }
        });
    }
    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showRestaurants() {
        mRecyclerView.setVisibility(View.VISIBLE);

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



