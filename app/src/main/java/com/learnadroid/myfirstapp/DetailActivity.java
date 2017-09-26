package com.learnadroid.myfirstapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class DetailActivity extends Fragment {
    ConnectionClass connectionClass;
    Button order_button;
    TextView myName,myPrice,mySpec;
    int[] sliderImage = {R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10,R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17,R.drawable.a18,R.drawable.a19,R.drawable.a20,R.drawable.a21,R.drawable.a22,R.drawable.a23,R.drawable.a24,R.drawable.a25,R.drawable.a26,R.drawable.a27,R.drawable.a28,R.drawable.a29,R.drawable.a30,R.drawable.a31,R.drawable.a32,R.drawable.a33,R.drawable.a34,R.drawable.a35,R.drawable.a36,R.drawable.a37,R.drawable.a38,R.drawable.a39,R.drawable.a40,R.drawable.a41,R.drawable.a42,R.drawable.a43,R.drawable.a44,R.drawable.a45,R.drawable.a46,R.drawable.a47,R.drawable.a48,R.drawable.a49,R.drawable.a50};
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.content_detail,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Product Details");
        connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();
        String itemName ="" ;
        String itemPrice ="";
        String itemSpec ="";
        HomeActivity.fragment_no=4;

        ImageView imageView1 = (ImageView) view.findViewById(R.id.detail_product_image_view1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.detail_product_image_view2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.detail_product_image_view3);
        order_button = (Button) view.findViewById(R.id.detail_buy_button);
        myName = (TextView) view.findViewById(R.id.detail_product_title);
        myPrice = (TextView) view.findViewById(R.id.detail_product_price);
        mySpec = (TextView) view.findViewById(R.id.detail_product_specs);
        try {
            Statement st = con.createStatement();
            ResultSet rs ;
            rs = st.executeQuery("select * from product where prod_id="+HomeActivity.prod_id);
            int pos = HomeActivity.prod_id;
            imageView1.setImageResource(sliderImage[pos-1]);
            imageView2.setImageResource(sliderImage[pos-1]);
            imageView3.setImageResource(sliderImage[pos-1]);
            if(rs.first())
            {
                itemName= rs.getString(2);
                itemPrice = rs.getString(4);
                itemSpec = rs.getString(3);
            }

            myName.setText(itemName);
            myPrice.setText(itemPrice);
            mySpec.setText(itemSpec);
            order_button.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                        Fragment fragment = new OrderDetailActivity();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_home, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                }
            );

        }
        catch (Exception e){
            Toast.makeText(getActivity(),"EXCEPTION GENERATED "+e,Toast.LENGTH_SHORT).show();
        }
    }
    /*public void showAlertDialog(String message, final boolean finish) {
        final FragmentActivity activity=getActivity();
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (finish)
                            activity.finish();
                    }
                });
        alertDialog.show();
    }*/
}