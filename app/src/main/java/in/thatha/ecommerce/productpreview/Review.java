package in.thatha.ecommerce.productpreview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.thatha.ecommerce.R;
import in.thatha.ecommerce.home.BestSell_Adapter;

import static android.content.Context.WINDOW_SERVICE;

public class Review extends Fragment {
    private Context mContext;
    private RecyclerView recycler_userreview;
   private String reviewstring = "";
   private String TAG = "review";

    private ArrayList<Review_Model> reviewArrayList = new ArrayList<Review_Model>();
    private Review_Adapter reviewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product_review,container,false);
        mContext = view.getContext();

        recycler_userreview = (RecyclerView) view.findViewById(R.id.recycler_userreview);
        LinearLayoutManager layoutman= new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        recycler_userreview.setLayoutManager(layoutman);
        recycler_userreview.setItemAnimator(new DefaultItemAnimator());

        //receive review as string ( array)

         reviewstring =  (((ProductDetailsActivity) getActivity()).prod_review);

        try {
            JSONArray jsonArray = new JSONArray(reviewstring);
            Log.e(TAG,"array size is: " +jsonArray.length());

            for(int i=0; i<jsonArray.length();i++){
                //jason array compulasory has json object
                //convert json array and object to string
               JSONObject jsonObject =  jsonArray.getJSONObject(i);
             jsonObject.get("title");
               jsonObject.get("desc");
               jsonObject.get("username");
               jsonObject.get("date");
                jsonObject.get("rating");
               Log.e(TAG,"review json title is:" +jsonObject.get("title"));
               reviewArrayList.add(new Review_Model(String.valueOf(jsonObject.get("title")),String.valueOf(jsonObject.get("desc")),String.valueOf(jsonObject.get("username")),String.valueOf(jsonObject.get("date")),String.valueOf(jsonObject.get("rating"))));

            }


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,"json error" +e.toString());
        }
        reviewAdapter = new Review_Adapter(mContext,reviewArrayList);
        recycler_userreview.setAdapter(reviewAdapter);
        reviewAdapter.notifyDataSetChanged();

        return view;
    }


}
