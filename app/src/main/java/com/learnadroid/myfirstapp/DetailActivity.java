package com.learnadroid.myfirstapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        order_button = (Button) view.findViewById(R.id.detail_buy_button);
        myName = (TextView) view.findViewById(R.id.detail_product_title);
        myPrice = (TextView) view.findViewById(R.id.detail_product_price);
        mySpec = (TextView) view.findViewById(R.id.detail_product_specs);
        try {
            Statement st = con.createStatement();
            ResultSet rs ;
            rs = st.executeQuery("select * from product where prod_id="+HomeActivity.prod_id);
            ResultSetMetaData rsmd = rs.getMetaData();
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
}