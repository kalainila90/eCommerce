package in.thatha.ecommerce.productpreview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.thatha.ecommerce.R;
import in.thatha.ecommerce.home.BestSell_Adapter;
import in.thatha.ecommerce.home.BestSell_Model;

public class Review_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Review_Model> mReviewModelList;
    private String TAG = "Review_adapter";
    private int mscreenWidth;

    public Review_Adapter(Context context, List<Review_Model> list){

        mContext = context;
        mReviewModelList = list;


    }
    //separate class for each item
    private class ReviewHolder extends RecyclerView.ViewHolder {

        TextView review_title,review_description,review_username,review_date;
        RatingBar review_rating;


        public ReviewHolder(View itemView) {
            super(itemView);


            review_title = (TextView)itemView.findViewById(R.id.review_title);
           review_description = (TextView) itemView.findViewById(R.id.review_descrip);
            review_username = (TextView) itemView.findViewById(R.id.review_user);
            review_date = (TextView) itemView.findViewById(R.id.review_date);
            review_rating = (RatingBar) itemView.findViewById(R.id.review_rating);

       }
    }

    //inflate the original view
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_review_item,parent,false);
        return new Review_Adapter.ReviewHolder(view);
    }

    //set items using list position

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Review_Model model = mReviewModelList.get(position);
        //Log.e(TAG, " assign value ");
       ((ReviewHolder) holder).review_title.setText(model.getReview_title());
        ((ReviewHolder) holder).review_description.setText(model.getReview_desc());
        ((ReviewHolder) holder).review_username.setText(model.getReview_username());
        ((ReviewHolder) holder).review_date.setText(model.getReview_date());
        ((ReviewHolder) holder).review_rating.setRating(Float.valueOf( model.getReview_rating()));

          }

    //number of items in list

    @Override
    public int getItemCount() {
        return mReviewModelList.size();
    }
}
