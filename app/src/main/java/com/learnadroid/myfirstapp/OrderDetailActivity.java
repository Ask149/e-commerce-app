package com.learnadroid.myfirstapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderDetailActivity extends Fragment {
    EditText quantity;
    TextView order_price;
    ConnectionClass connectionClass;
    TextView order_total_price;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_order_details,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        order_price = (TextView) view.findViewById(R.id.order_price_no);
        order_total_price = (TextView) view.findViewById(R.id.order_total_price);
        quantity = (EditText) view.findViewById(R.id.order_quantity_count);
        int total_price_int = 0;
        connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();
        try
        {

            ResultSet rs;
            String sql = "select * from product where prod_id = "+HomeActivity.prod_id+";";
            PreparedStatement st = con.prepareStatement(sql);
            rs = st.executeQuery();
            String s=null;
            if(rs.next())
            {
                total_price_int=rs.getInt(4);
                s=rs.getString(4);
            }

            String quan = quantity.getText().toString();
            int qtity = Integer.parseInt(quan);
            order_price.setText(s);

            total_price_int = total_price_int*qtity;

            String ts=Integer.toString(total_price_int);
            order_total_price.setText(ts);
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(),"Exception : "+e,Toast.LENGTH_SHORT).show();
        }
    }
}