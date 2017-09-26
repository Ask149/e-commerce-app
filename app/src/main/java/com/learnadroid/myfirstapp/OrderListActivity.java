package com.learnadroid.myfirstapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderListActivity extends Fragment {

    String orderItemName[];
    String orderPersonName[];
    String orderCost[];
    String orderPersonContact[];
    String orderQuantity[];
    int[] itemImage = {R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,  R.drawable.ic_menu_camera };
    ConnectionClass connectionClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_order_list,container,false);
        getActivity().setTitle("Orders List");
        HomeActivity.fragment_no=3;
        connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();

        try {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("select c.name,c.quantity,c.contact_no,p.prod_name,c.quantity from cart c,product p where c.user_id='"+MainActivity.user_id+"' and p.prod_id = c.prod_id;");

                int count=0;
                if(rs.first())
                    count=1;

                while(rs.next())
                    count++;

                orderItemName = new String[count];
                orderPersonName = new String[count];
                orderPersonContact = new String[count];
                orderQuantity = new String[count];
                orderCost = new String[count];

                int index=0;
                if(rs.first())
                {
                    orderPersonName[index]=rs.getString(1);
                    orderQuantity[index]=rs.getString(2);
                    orderPersonContact[index]=rs.getString(3);
                    orderItemName[index]=rs.getString(4);
                    orderCost[index++]=rs.getString(5);
                }

                while(rs.next())
                {
                    orderPersonName[index]=rs.getString(1);
                    orderQuantity[index]=rs.getString(2);
                    orderPersonContact[index]=rs.getString(3);
                    orderItemName[index]=rs.getString(4);
                    orderCost[index++]=rs.getString(5);
                }

                ListView  temp=(ListView)view.findViewById(R.id.content_orders_list_view2);
                CustomeOrderAdapter customeOrderAdapter2 = new CustomeOrderAdapter(getContext());
                temp.setAdapter(customeOrderAdapter2);
            }
            else {
                Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
        catch (SQLException e) {
            Toast.makeText(getActivity(), "Exception1 : "+e, Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    class CustomeOrderAdapter extends ArrayAdapter<String> {

        Context context;
        public CustomeOrderAdapter(Context context) {
            super(context,R.layout.orderlistrow);
            this.context=context;
        }

        @Override
        public int getCount() {
            return orderItemName.length;
        }

        @Override
        public String getItem(int position) {
            return orderItemName[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getActivity().getLayoutInflater().inflate(R.layout.orderlistrow,null);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.order_item_image);
            TextView textViewProdName = (TextView) convertView.findViewById(R.id.order_item_title);
            TextView textViewPersonName = (TextView) convertView.findViewById(R.id.order_item_person_name);
            TextView textViewContact = (TextView) convertView.findViewById(R.id.order_item_contact_no);
            TextView textViewCost = (TextView) convertView.findViewById(R.id.order_item_price);


            imageView.setImageResource(itemImage[position]);
            textViewProdName.setText(orderItemName[position]);
            textViewCost.setText(orderCost[position]);
            textViewContact.setText(orderPersonContact[position]);
            textViewPersonName.setText(orderPersonName[position]);

            return convertView;
        }
    }
}