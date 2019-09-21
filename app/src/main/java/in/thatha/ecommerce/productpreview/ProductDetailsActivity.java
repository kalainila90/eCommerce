package in.thatha.ecommerce.productpreview;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.ArrayList;
import in.thatha.ecommerce.R;
import in.thatha.ecommerce.WebServices.ServiceWrapper;
import in.thatha.ecommerce.beanResponse.ProductDetailsResponse;
import in.thatha.ecommerce.home.BestSell_Adapter;
import in.thatha.ecommerce.home.BestSell_Model;
import in.thatha.ecommerce.utility.AppUtilits;
import in.thatha.ecommerce.utility.NetworkUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private  String TAG = "ProductDetailsActivity";
    private String prod_id = "";
    private TextView prod_name,old_price,prod_price,rating_count,prod_stock,prod_qty;
    private AppCompatRatingBar rating;
    private RecyclerView recycler_related;
    public String prod_overview = "";
    public String prod_fulldetails="";
    public String prod_review;

    private TabLayout tabLayout;
    private FrameLayout frag_container;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    private ArrayList<BestSell_Model> relatedProductArrayList = new ArrayList<BestSell_Model>();
    private BestSell_Adapter relatedProductAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_preview);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolBar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        //to close when user touch outside navigation drawer
        toggle.syncState();


        navigationView = (NavigationView)findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(this);


        prod_name = (TextView)findViewById(R.id.prod_name);
        old_price = (TextView)findViewById(R.id.old_price);
        prod_price = (TextView)findViewById(R.id.prod_price);
        prod_stock = (TextView)findViewById(R.id.stock);
        rating_count = (TextView)findViewById(R.id.rating_count);
        rating = (AppCompatRatingBar)findViewById(R.id.prod_rating);
        prod_qty = (TextView)findViewById(R.id.prod_qty_value);
        frag_container = (FrameLayout)findViewById(R.id.frag_container);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.overview)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.details)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.review)));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment PrevFrag = fragmentManager.findFragmentByTag("in.thatha.ecommerce.productpreview.tabfragment");
                if(PrevFrag != null){
                    fragmentTransaction.remove(PrevFrag);
                }

                 if(tab.getPosition() == 0){
                     Overview overview = new Overview();
                     fragmentTransaction.add(R.id.frag_container,overview,"in.thatha.ecommerce.productpreview.tabfragment");
                     fragmentTransaction.commit();
                 }else if(tab.getPosition() == 1){

                     Details detail = new Details();
                     fragmentTransaction.add(R.id.frag_container,detail,"in.thatha.ecommerce.productpreview.tabfragment");
                     fragmentTransaction.commit();

                 }else if(tab.getPosition() == 2){

                     Review review = new Review();
                     fragmentTransaction.add(R.id.frag_container,review,"in.thatha.ecommerce.productpreview.tabfragment");
                     fragmentTransaction.commit();
                 }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

       Intent intent = getIntent();
       prod_id =  intent.getExtras().getString("prod_id");

        getProductDetails();

        recycler_related = (RecyclerView) findViewById(R.id.recycler_related);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        recycler_related.setLayoutManager(mLayoutManager);
        recycler_related.setItemAnimator(new DefaultItemAnimator());
        relatedProductAdapter = new BestSell_Adapter(this,relatedProductArrayList,getScreenWidth());
        recycler_related.setAdapter(relatedProductAdapter);






    }



     public void getProductDetails(){
         if(!NetworkUtility.isNetworkConnected(ProductDetailsActivity.this)){
             AppUtilits.displayMessage(ProductDetailsActivity.this,getString(R.string.network_not_connected));
         }else {

             ServiceWrapper serviceWrapper = new ServiceWrapper(null);
             Call<ProductDetailsResponse> callProductdetails =  serviceWrapper.prodDetailCall("1234",prod_id);
             callProductdetails.enqueue(new Callback<ProductDetailsResponse>() {
                 @Override
                 public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {

                     Log.e(TAG,"response :" +response.body().getInformation());

                     //response created
                     if(response.body() != null && response.isSuccessful()) {

                         if (response.body().getStatus() == 1) {

                             if(response.body().getInformation().getDetails().getName() != null) {

                                 prod_name.setText(response.body().getInformation().getDetails().getName());
                                 old_price.setText(response.body().getInformation().getDetails().getMrp());
                                 prod_price.setText(response.body().getInformation().getDetails().getPrice());

                                 if(response.body().getInformation().getDetails().getStock() > 0){
                                     prod_stock.setText(getString(R.string.in_stock));
                                 }else{
                                     prod_stock.setText(getString(R.string.out_stock));
                                 }

                                 rating.setRating(response.body().getInformation().getDetails().getRating());
                                 rating_count.setText(response.body().getInformation().getDetails().getRatingCount());
                                 prod_qty.setText("1");

                                 if(response.body().getInformation().getRelated().size() > 0){
                                     relatedProductArrayList.clear();

                                     for(int i=0; i <response.body().getInformation().getRelated().size();i++){

                                         relatedProductArrayList.add(new BestSell_Model(response.body().getInformation().getRelated().get(i).getId(),
                                                 response.body().getInformation().getRelated().get(i).getName(),
                                                 response.body().getInformation().getRelated().get(i).getImgUrl(),
                                                 response.body().getInformation().getRelated().get(i).getMrp(),
                                                 response.body().getInformation().getRelated().get(i).getPrice(),
                                                 String.valueOf(response.body().getInformation().getRelated().get(i).getRating())));

                                     }
                                     relatedProductAdapter.notifyDataSetChanged();

                                 }

                                 //tab layout overvire,details,review
                                 //here to set default overview tab
                                 prod_overview = response.body().getInformation().getDetails().getDesc();
                                 prod_fulldetails = response.body().getInformation().getDetails().getFulldetails();
                                 prod_review = response.body().getInformation().getReview();
                                 //should form complete details for detail tab
                                 // prod_desc = response.body().getInformation().getDetails().getCompletedetails();

                                 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                 Overview overview = new Overview();
                                 fragmentTransaction.add(R.id.frag_container,overview,"in.thatha.ecommerce.productpreview.tabfragment");
                                 fragmentTransaction.commit();

                             }


                         }else {
                             Log.e(TAG,"response :" +response.body().getMsg());
                             // AppUtilits.displayMessage(HomeActivity.this, response.body().getMsg());
                         }

                     }  else{
                         //if response is created but null and not success
                         Log.e(TAG,"response :" +response.body().getMsg());
                         // AppUtilits.displayMessage(HomeActivity.this,getString(R.string.failed_request));
                     }


                 }

                 @Override
                 public void onFailure(Call<ProductDetailsResponse> call, Throwable t) {
                     //no response
                     Log.e(TAG,"fail new product" +t.toString());
                    // AppUtilits.displayMessage(ProductDetailsActivity.this,getString(R.string.failed_request));
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

