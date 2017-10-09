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
    static String product_name;
    TextView myName,myPrice,mySpec;

    int[] sliderImage1 = {R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10,R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17,R.drawable.a18,R.drawable.a19,R.drawable.a20,R.drawable.a21,R.drawable.a22,R.drawable.a23,R.drawable.a24,R.drawable.a25,R.drawable.a26,R.drawable.a27,R.drawable.a28,R.drawable.a29,R.drawable.a30,R.drawable.a31,R.drawable.a32,R.drawable.a33,R.drawable.a34,R.drawable.a35,R.drawable.a36,R.drawable.a37,R.drawable.a38,R.drawable.a39,R.drawable.a40,R.drawable.a41,R.drawable.a42,R.drawable.a43,R.drawable.a44,R.drawable.a45,R.drawable.a46,R.drawable.a47,R.drawable.a48,R.drawable.a49,R.drawable.a50};
    int[] sliderImage2 = {R.drawable.a1_2,R.drawable.a2_2,R.drawable.a3_2,R.drawable.a4_2,R.drawable.a5_2,R.drawable.a6_2,R.drawable.a7_2,R.drawable.a8_2,R.drawable.a9_2,R.drawable.a10_2,R.drawable.a11_2,R.drawable.a12_2,R.drawable.a13_2,R.drawable.a14_2,R.drawable.a15_2,R.drawable.a16_2,R.drawable.a17_2,R.drawable.a18_2,R.drawable.a19_2,R.drawable.a20_2,R.drawable.a21_2,R.drawable.a22_2,R.drawable.a23_2,R.drawable.a24_2,R.drawable.a25_2,R.drawable.a26_2,R.drawable.a27_2,R.drawable.a28_2,R.drawable.a29_2,R.drawable.a30_2,R.drawable.a31_2,R.drawable.a32_2,R.drawable.a33_2,R.drawable.a34_2,R.drawable.a35_2,R.drawable.a36_2,R.drawable.a37_2,R.drawable.a38_2,R.drawable.a39_2,R.drawable.a40_2,R.drawable.a41_2,R.drawable.a42_2,R.drawable.a43_2,R.drawable.a44_2,R.drawable.a45_2,R.drawable.a46_2,R.drawable.a47_2,R.drawable.a48_2,R.drawable.a49_2,R.drawable.a50_2};
    int[] sliderImage3 = {R.drawable.a1_3,R.drawable.a2_3,R.drawable.a3_3,R.drawable.a4_3,R.drawable.a5_3,R.drawable.a6_3,R.drawable.a7_3,R.drawable.a8_3,R.drawable.a9_3,R.drawable.a10_3,R.drawable.a11_3,R.drawable.a12_3,R.drawable.a13_3,R.drawable.a14_3,R.drawable.a15_3,R.drawable.a16_3,R.drawable.a17_3,R.drawable.a18_3,R.drawable.a19_3,R.drawable.a20_3,R.drawable.a21_3,R.drawable.a22_3,R.drawable.a23_3,R.drawable.a24_3,R.drawable.a25_3,R.drawable.a26_3,R.drawable.a27_3,R.drawable.a28_3,R.drawable.a29_3,R.drawable.a30_3,R.drawable.a31_3,R.drawable.a32_3,R.drawable.a33_3,R.drawable.a34_3,R.drawable.a35_3,R.drawable.a36_3,R.drawable.a37_3,R.drawable.a38_3,R.drawable.a39_3,R.drawable.a40_3,R.drawable.a41_3,R.drawable.a42_3,R.drawable.a43_3,R.drawable.a44_3,R.drawable.a45_3,R.drawable.a46_3,R.drawable.a47_3,R.drawable.a48_3,R.drawable.a49_3,R.drawable.a50_3};
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
            imageView1.setImageResource(sliderImage1[pos-1]);
            imageView2.setImageResource(sliderImage2[pos-1]);
            imageView3.setImageResource(sliderImage3[pos-1]);
            if(rs.first())
            {
                itemName= rs.getString(2);
                itemPrice = rs.getString(4);
                itemSpec = rs.getString(3);
            }

            product_name = itemName;

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
}