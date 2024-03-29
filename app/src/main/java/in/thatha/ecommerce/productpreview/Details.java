package in.thatha.ecommerce.productpreview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.thatha.ecommerce.R;

public class Details extends Fragment {
    private Context mContext;
    private TextView prod_details;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product_details,container,false);
        mContext = view.getContext();

        prod_details = (TextView) view.findViewById(R.id.text_details);
         //receive details from product details activity and display in details tab
        prod_details.setText( ((ProductDetailsActivity) getActivity()).prod_fulldetails);


        return view;
    }
}
