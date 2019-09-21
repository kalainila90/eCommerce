package in.thatha.ecommerce.home;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;

import in.thatha.ecommerce.R;
import in.thatha.ecommerce.WebServices.ServiceWrapper;
import in.thatha.ecommerce.beanResponse.NewProductResponse;
import in.thatha.ecommerce.beanResponse.UserSignin;
import in.thatha.ecommerce.cart.CartDetailsActivity;
import in.thatha.ecommerce.login.SignInActivity;
import in.thatha.ecommerce.utility.AppUtilits;
import in.thatha.ecommerce.utility.Constant;
import in.thatha.ecommerce.utility.NetworkUtility;
import in.thatha.ecommerce.utility.SharePreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private  String TAG = "HomeActivity";

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView recycler_bestSell,recycler_newProd,recycler_trending,recycler_conditional;

    private ArrayList<NewProd_Model> newProdModelArrayList = new ArrayList<NewProd_Model>();
    private NewProd_Adapter newProdAdapter;

    private ArrayList<BestSell_Model> bestSellModelArrayList = new ArrayList<BestSell_Model>();
    private BestSell_Adapter bestSellAdapter;

    private ArrayList<BestSell_Model> trendingArrayList = new ArrayList<BestSell_Model>();
    private BestSell_Adapter trendingAdapter;

    private ArrayList<BestSell_Model> conditionalArrayList = new ArrayList<BestSell_Model>();
    private BestSell_Adapter conditionalAdapter;
    private Menu mainmenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        navigationView = (NavigationView)findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolBar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        //to close when user touch outside navigation drawer
        toggle.syncState();


        // new product recycler
        recycler_newProd = (RecyclerView)findViewById(R.id.recycler_newArri);
        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        recycler_newProd.setLayoutManager(lm);
        recycler_newProd.setItemAnimator(new DefaultItemAnimator());
        newProdAdapter = new NewProd_Adapter(this,newProdModelArrayList,getScreenWidth());
        recycler_newProd.setAdapter(newProdAdapter);


//best selling recycler
               recycler_bestSell = (RecyclerView)findViewById(R.id.recycler_bestSell);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
       recycler_bestSell.setLayoutManager(mLayoutManager);
       recycler_bestSell.setItemAnimator(new DefaultItemAnimator());
       bestSellAdapter = new BestSell_Adapter(this,bestSellModelArrayList,getScreenWidth());
       recycler_bestSell.setAdapter(bestSellAdapter);




     /*   for(int i=0; i<4; i++) {

            bestSellModel = new BestSell_Model("Oppo" + i, "", "5 USD", "3 USD", "2.5");
            bestSellModelArrayList.add(bestSellModel);


        }

         //tell adapter to update with changes
        bestSellAdapter.notifyDataSetChanged();*/

     //trending recycler

        recycler_trending = (RecyclerView)findViewById(R.id.recycler_trend);
        LinearLayoutManager layman= new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        recycler_trending.setLayoutManager(layman);
        recycler_trending.setItemAnimator(new DefaultItemAnimator());
       trendingAdapter = new BestSell_Adapter(this,trendingArrayList,getScreenWidth());
        recycler_trending.setAdapter(trendingAdapter);


        //conditional recycler

       recycler_conditional = (RecyclerView)findViewById(R.id.recycler_conditional);
        LinearLayoutManager layoutman= new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        recycler_conditional.setLayoutManager(layoutman);
        recycler_conditional.setItemAnimator(new DefaultItemAnimator());
        conditionalAdapter = new BestSell_Adapter(this,conditionalArrayList,getScreenWidth());
        recycler_conditional.setAdapter(conditionalAdapter);


        getNewProducts();
        getBestSellingProducts();
        getTrendingProducts();
        getConditionalProducts();

    }


    public void getNewProducts(){
        if(!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,getString(R.string.network_not_connected));
        }else {

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<NewProductResponse> callNewProduct =  serviceWrapper.newProdResCall("1234");
            callNewProduct.enqueue(new Callback<NewProductResponse>() {
                @Override
                public void onResponse(Call<NewProductResponse> call, Response<NewProductResponse> response) {
                    Log.d(TAG,"response :" +response.body().getInformation().toString());

                    //response created
                    if(response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

                            if(response.body().getInformation().size() > 0){

                                newProdModelArrayList.clear();
                                for(int i=0; i <response.body().getInformation().size();i++){

                               //     newProdModel = new NewProd_Model( response.body().getInformation().get(i).getId(),
                                         //   response.body().getInformation().get(i).getName(),response.body().getInformation().get(i).getImgUrl());

                                    // above statement can be written staightly
                                    newProdModelArrayList.add(new NewProd_Model( response.body().getInformation().get(i).getId(),
                                              response.body().getInformation().get(i).getName(),response.body().getInformation().get(i).getImgUrl()));

                                }

                                //tell adapter to update with changes
                                newProdAdapter.notifyDataSetChanged();

                            }


                        } else {
                           // AppUtilits.displayMessage(HomeActivity.this, response.body().getMsg());
                        }
                    }else{
                        //if response is null and not success
                       // AppUtilits.displayMessage(HomeActivity.this,getString(R.string.failed_request));
                    }


                }

                @Override
                public void onFailure(Call<NewProductResponse> call, Throwable t) {
                    //no response
                    Log.e(TAG,"fail new product" +t.toString());
                    AppUtilits.displayMessage(HomeActivity.this,getString(R.string.failed_request));
                }
            });

        }

    }




    public void getBestSellingProducts(){
        if(!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,getString(R.string.network_not_connected));
        }else {

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<NewProductResponse> callBestSellProduct =  serviceWrapper.bestSellResCall("1234");
            callBestSellProduct.enqueue(new Callback<NewProductResponse>() {
                @Override
                public void onResponse(Call<NewProductResponse> call, Response<NewProductResponse> response) {
                    Log.d(TAG,"response :" +response.body().getInformation().toString());

                    //response created
                    if(response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

                            if(response.body().getInformation().size() > 0){

                                bestSellModelArrayList.clear();
                                for(int i=0; i <response.body().getInformation().size();i++){

                                    //     newProdModel = new NewProd_Model( response.body().getInformation().get(i).getId(),
                                    //   response.body().getInformation().get(i).getName(),response.body().getInformation().get(i).getImgUrl());

                                    // above statement can be written staightly
                                    bestSellModelArrayList.add(new BestSell_Model(response.body().getInformation().get(i).getId(),response.body().getInformation().get(i).getName(),response.body().getInformation().get(i).getImgUrl(),
                                            response.body().getInformation().get(i).getMrp(),response.body().getInformation().get(i).getPrice(),response.body().getInformation().get(i).getRating()));

                                }

                                //tell adapter to update with changes
                                bestSellAdapter.notifyDataSetChanged();

                            }


                        } else {

                            // AppUtilits.displayMessage(HomeActivity.this, response.body().getMsg());
                        }
                    }else{
                        //if response is created but null and not success
                        // AppUtilits.displayMessage(HomeActivity.this,getString(R.string.failed_request));
                    }


                }

                @Override
                public void onFailure(Call<NewProductResponse> call, Throwable t) {
                    //no response
                    Log.e(TAG,"fail new product" +t.toString());
                    AppUtilits.displayMessage(HomeActivity.this,getString(R.string.failed_request));
                }
            });

        }

    }



    public void getTrendingProducts(){
        if(!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,getString(R.string.network_not_connected));
        }else {

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<NewProductResponse> callTrend =  serviceWrapper.trendingResCall("1234");
            callTrend.enqueue(new Callback<NewProductResponse>() {
                @Override
                public void onResponse(Call<NewProductResponse> call, Response<NewProductResponse> response) {
                    Log.d(TAG,"response :" +response.body().getInformation().toString());

                    //response created
                    if(response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

                            if(response.body().getInformation().size() > 0){

                              trendingArrayList.clear();
                                for(int i=0; i <response.body().getInformation().size();i++){

                                    //     newProdModel = new NewProd_Model( response.body().getInformation().get(i).getId(),
                                    //   response.body().getInformation().get(i).getName(),response.body().getInformation().get(i).getImgUrl());

                                    // above statement can be written staightly
                                   trendingArrayList.add(new BestSell_Model(response.body().getInformation().get(i).getId(),response.body().getInformation().get(i).getName(),response.body().getInformation().get(i).getImgUrl(),
                                            response.body().getInformation().get(i).getMrp(),response.body().getInformation().get(i).getPrice(),response.body().getInformation().get(i).getRating()));

                                }

                                //tell adapter to update with changes
                                trendingAdapter.notifyDataSetChanged();

                            }


                        } else {
                            // AppUtilits.displayMessage(HomeActivity.this, response.body().getMsg());
                        }
                    }else{
                        //if response is null and not success
                        // AppUtilits.displayMessage(HomeActivity.this,getString(R.string.failed_request));
                    }


                }

                @Override
                public void onFailure(Call<NewProductResponse> call, Throwable t) {
                    //no response
                    Log.e(TAG,"fail new product" +t.toString());
                    AppUtilits.displayMessage(HomeActivity.this,getString(R.string.failed_request));
                }
            });

        }

    }



    public void getConditionalProducts(){
        if(!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,getString(R.string.network_not_connected));
        }else {

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<NewProductResponse> callConditional =  serviceWrapper.conditionalResCall("1234");
            callConditional.enqueue(new Callback<NewProductResponse>() {
                @Override
                public void onResponse(Call<NewProductResponse> call, Response<NewProductResponse> response) {
                    Log.d(TAG,"response :" +response.body().getInformation().toString());

                    //response created
                    if(response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

                            if(response.body().getInformation().size() > 0){

                                conditionalArrayList.clear();
                                for(int i=0; i <response.body().getInformation().size();i++){

                                    //     newProdModel = new NewProd_Model( response.body().getInformation().get(i).getId(),
                                    //   response.body().getInformation().get(i).getName(),response.body().getInformation().get(i).getImgUrl());

                                    // above statement can be written staightly
                                    conditionalArrayList.add(new BestSell_Model(response.body().getInformation().get(i).getId(),response.body().getInformation().get(i).getName(),response.body().getInformation().get(i).getImgUrl(),
                                            response.body().getInformation().get(i).getMrp(),response.body().getInformation().get(i).getPrice(),response.body().getInformation().get(i).getRating()));

                                }

                                //tell adapter to update with changes
                               conditionalAdapter.notifyDataSetChanged();

                            }


                        } else {
                            // AppUtilits.displayMessage(HomeActivity.this, response.body().getMsg());
                        }
                    }else{
                        //if response is null and not success
                        // AppUtilits.displayMessage(HomeActivity.this,getString(R.string.failed_request));
                    }


                }

                @Override
                public void onFailure(Call<NewProductResponse> call, Throwable t) {
                    //no response
                    Log.e(TAG,"fail new product" +t.toString());
                    AppUtilits.displayMessage(HomeActivity.this,getString(R.string.failed_request));
                }
            });

        }

    }


    public int getScreenWidth(){
         int width = 100;
         //measure width of screen acording to number of items
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        return width;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflater is used to display another menu over existing menu or layout
        getMenuInflater().inflate(R.menu.myaccount_toolbar_menu,menu);
         mainmenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.toolBar_search){
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            SearchView searchView = (SearchView) mainmenu.getItem(R.id.toolBar_search).getActionView();
            EditText searchEdittext = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchEdittext.setHint(R.string.search_name);

        }else if(id == R.id.toolbar_cart){
          Intent i = new Intent(HomeActivity.this, CartDetailsActivity.class);
          startActivity(i);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.navi_home){

        }else if(id == R.id.navi_new_prod){

        }else if(id == R.id.navi_my_accout){

        }else if(id == R.id.navi_wishlist){

        }else if(id == R.id.navi_logout){

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
