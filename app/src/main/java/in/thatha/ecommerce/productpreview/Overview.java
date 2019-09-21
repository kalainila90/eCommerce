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

public class Overview extends Fragment {

    private Context mContext;
    private TextView prod_overview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product_overview,container,false);
        mContext = view.getContext();
        prod_overview = (TextView) view.findViewById(R.id.text_overview);

        prod_overview.setText( ((ProductDetailsActivity) getActivity()).prod_overview);

        return view;
        }
}
