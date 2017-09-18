package com.learnadroid.myfirstapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * Created by Ashish on 09-09-2017.
 */

public class DetailActivity extends Fragment {
    ConnectionClass connectionClass;
    String[] itemName ;
    String[] itemPrice;
    String[] itemSpec ;
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
        connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();
        itemSpec = new String[10];
        itemName = new String[10];
        itemPrice = new String[10];
        myName = (TextView) view.findViewById(R.id.detail_product_title);
        myPrice = (TextView) view.findViewById(R.id.detail_product_price);
        mySpec = (TextView) view.findViewById(R.id.detail_product_specs);
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from product");
            ResultSetMetaData rsmd = rs.getMetaData();
            int index=0;
            while(rs.next())
            {
                itemName[index] += rs.getString(2);
                itemPrice[index] += rs.getString(4);
                itemSpec[index++] += rs.getString(3);
            }
            Toast.makeText(getActivity(),"PRODUCT DETAILS",Toast.LENGTH_SHORT);
            myName.setText(itemName[HomeActivity.prod_id]);
            myPrice.setText(itemPrice[HomeActivity.prod_id]);
            mySpec.setText(itemSpec[HomeActivity.prod_id]);
        }

        catch (Exception e){
            Toast.makeText(getActivity(),"EXCEPTION GENERATED",Toast.LENGTH_SHORT);
        }
    }
}
