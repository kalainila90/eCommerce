package in.thatha.ecommerce.home;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.thatha.ecommerce.R;

public class NewProd_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<NewProd_Model> mNewProdModelList;
    private String TAG = "NewProd_adapter";
    private int mscreenWidth;

    public NewProd_Adapter(Context context, List<NewProd_Model> list,int screenWidth){

        mContext = context;
        mNewProdModelList = list;
        mscreenWidth = screenWidth;

    }

    public class NewProdHolder extends RecyclerView.ViewHolder{
        ImageView prod_img;
        TextView prod_name;
        CardView cardView;
        public NewProdHolder(View itemView) {
            super(itemView);
            prod_img = (ImageView) itemView.findViewById(R.id.prod_img);
            prod_name = (TextView)itemView.findViewById(R.id.product_name);
            cardView = (CardView) itemView.findViewById(R.id.card);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mscreenWidth - mscreenWidth/100*65,LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(10,10,10,10);

            cardView.setLayoutParams(params);
            cardView.setPadding(5,5,5,5);


        }
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_home_newarrival,parent,false);
         return new NewProdHolder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        NewProd_Model model = mNewProdModelList.get(position);
        ((NewProdHolder) holder).prod_name.setText(model.getProd_name());
        // imageview glider lib to get image from url
        Glide.with(mContext)
                .load(model.getImg_url())
                .into(((NewProdHolder) holder).prod_img);

    }

    @Override
    public int getItemCount() {
        return mNewProdModelList.size();
    }
}
