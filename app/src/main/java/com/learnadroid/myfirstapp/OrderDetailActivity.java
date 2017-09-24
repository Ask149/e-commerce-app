package com.learnadroid.myfirstapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDetailActivity extends Fragment {
    String quan;
    EditText etaddress;
    EditText etname;
    EditText econtact;
    int qtity ;
    String ts;
    Button bbtn;
    int total_price_int;
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

        getActivity().setTitle("Order");
        bbtn = (Button) view.findViewById(R.id.order_detail_buy_button);
        etaddress = (EditText) view.findViewById(R.id.order_address);
        etname = (EditText) view.findViewById(R.id.order_address_name);
        econtact = (EditText) view.findViewById(R.id.order_contact_no);
        order_price = (TextView) view.findViewById(R.id.order_price_no);
        order_total_price = (TextView) view.findViewById(R.id.order_total_price);
        quantity = (EditText) view.findViewById(R.id.order_quantity_count);
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

            quan = quantity.getText().toString();
            qtity = Integer.parseInt(quan);
            order_price.setText(s);

            total_price_int = total_price_int*qtity;
            Toast.makeText(getActivity(),"Lets Insert",Toast.LENGTH_SHORT).show();
            ts=Integer.toString(total_price_int);

            bbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order_total_price.setText(ts);
                        Toast.makeText(getActivity(),"Clicked",Toast.LENGTH_SHORT).show();
                        String name , address ;
                        String contact;
                        address=etaddress.getText().toString();
                        name=etname.getText().toString();
                        contact=econtact.getText().toString();
                        address=etaddress.getText().toString();
                        name=etname.getText().toString();
                        contact=econtact.getText().toString();
                        Connection con1 = connectionClass.CONN();

                        Statement st1 = null;
                        try {
                            st1 = con1.createStatement();
                            String sql2 = "insert into cart(user_id,name,address,contact_no,prod_id,quantity,total_cost) values('"+ MainActivity.user_id + "','" + name + "','" + address + "'," + contact + "," + HomeActivity.prod_id + "," + qtity + "," + total_price_int +");";
                            Boolean rs1 = st1.execute(sql2);
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getActivity(),"Exception"+e,Toast.LENGTH_SHORT).show();
                        }
                        Fragment fragment = new OrderListActivity();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_order_details,fragment);
                        ft.commit();
                        Toast.makeText(getActivity(),"Exit",Toast.LENGTH_SHORT).show();
                    }
                }
            );
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(),"Exception : "+e,Toast.LENGTH_SHORT).show();
        }
    }
}