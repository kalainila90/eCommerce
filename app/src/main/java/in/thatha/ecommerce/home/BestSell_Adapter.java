package in.thatha.ecommerce.home;

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
import in.thatha.ecommerce.productpreview.ProductDetailsActivity;

public class BestSell_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<BestSell_Model> mBestSellModelList;
    private String TAG = "BestSell_adapter";
    private int mscreenWidth;

public BestSell_Adapter(Context context, List<BestSell_Model> list,int screenWidth){

    mContext = context;
    mBestSellModelList = list;
    mscreenWidth = screenWidth;

}
  //separate class for each item
   private class BestSellHolder extends RecyclerView.ViewHolder {
    ImageView prod_img;
    TextView prod_name,prod_oldprice,prod_price;
    RatingBar prod_rating;
    CardView cardView;

       public BestSellHolder(View itemView) {
                   super(itemView);

           prod_img = (ImageView) itemView.findViewById(R.id.prod_img);
           prod_name = (TextView)itemView.findViewById(R.id.product_name);
           prod_oldprice = (TextView) itemView.findViewById(R.id.old_price);
           prod_price = (TextView) itemView.findViewById(R.id.prod_price);
           prod_rating = (RatingBar) itemView.findViewById(R.id.prod_rating);
           cardView = (CardView) itemView.findViewById(R.id.card_view);


           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mscreenWidth - mscreenWidth/100*60,LinearLayout.LayoutParams.MATCH_PARENT);
           params.setMargins(10,10,10,10);

           cardView.setLayoutParams(params);
           cardView.setPadding(5,5,5,5);

       }
   }

   //inflate the original view
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_bestselling,parent,false);
        return new BestSellHolder(view);
    }

    //set items using list position

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BestSell_Model model = mBestSellModelList.get(position);
        //Log.e(TAG, " assign value ");
        ((BestSellHolder) holder).prod_name.setText(model.getProd_name());
        ((BestSellHolder) holder).prod_oldprice.setText(model.getOld_price());
        ((BestSellHolder) holder).prod_price.setText(model.getPrice());
        ((BestSellHolder) holder).prod_rating.setRating(Float.valueOf( model.getRating()));

        // imageview glider lib to get image from url
        Glide.with(mContext)
                .load(model.getImg_url())
                .into(((BestSellHolder) holder).prod_img);

        //on item click
        ((BestSellHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, in.thatha.ecommerce.productpreview.ProductDetailsActivity.class);
                 i.putExtra("prod_id",model.getProd_id());
                 //inside adapter use context.startActivity
                Log.e(TAG,"response :" +model.getProd_id());
                mContext.startActivity(i);
            }
        });




           }

    //number of items in list

    @Override
    public int getItemCount() {
        return mBestSellModelList.size();
    }
}
