package com.learnadroid.myfirstapp;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderListActivity extends Fragment {

    ProgressBar progressBar;
    MyCountDownTimer myCountDownTimer;
    String orderItemName[];
    String orderPersonName[];
    CheckedTextView textCounter;
    String orderCost[];
    int orderProductId[];
    int count;
    String orderQuantity[];
    int[] itemImage = {R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10,R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17,R.drawable.a18,R.drawable.a19,R.drawable.a20,R.drawable.a21,R.drawable.a22,R.drawable.a23,R.drawable.a24,R.drawable.a25,R.drawable.a26,R.drawable.a27,R.drawable.a28,R.drawable.a29,R.drawable.a30,R.drawable.a31,R.drawable.a32,R.drawable.a33,R.drawable.a34,R.drawable.a35,R.drawable.a36,R.drawable.a37,R.drawable.a38,R.drawable.a39,R.drawable.a40,R.drawable.a41,R.drawable.a42,R.drawable.a43,R.drawable.a44,R.drawable.a45,R.drawable.a46,R.drawable.a47,R.drawable.a48,R.drawable.a49,R.drawable.a50};
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
                rs = st.executeQuery("select c.name,c.quantity,c.contact_no,p.prod_name,c.total_cost,p.prod_id from cart c,product p where c.user_id='"+MainActivity.user_id+"' and p.prod_id = c.prod_id;");

                count=0;
                if(rs.first())
                    count=1;
                while(rs.next())
                    count++;

                orderItemName = new String[count];
                orderPersonName = new String[count];
                orderQuantity = new String[count];
                orderCost = new String[count];
                orderProductId = new int[count];

                int index=0;
                if(rs.first())
                {
                    orderPersonName[index]=rs.getString(1);
                    orderQuantity[index]=rs.getString(2);
                    orderItemName[index]=rs.getString(4);
                    orderCost[index]=rs.getString(5);
                    orderProductId[index++]=rs.getInt(6);
                }

                while(rs.next())
                {
                    orderPersonName[index]=rs.getString(1);
                    orderQuantity[index]=rs.getString(2);
                    orderItemName[index]=rs.getString(4);
                    orderProductId[index]=rs.getInt(6);
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
            progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);
            textCounter = (CheckedTextView)convertView.findViewById(R.id.order_row_check_text);
            progressBar.setProgress(0);
            myCountDownTimer = new MyCountDownTimer(10000, 500);
            myCountDownTimer.start();

            ImageView imageView = (ImageView) convertView.findViewById(R.id.order_item_image);
            TextView textViewProdName = (TextView) convertView.findViewById(R.id.order_item_title);
            TextView textViewPersonName = (TextView) convertView.findViewById(R.id.order_item_person_name);
            TextView textViewCost = (TextView) convertView.findViewById(R.id.order_item_price);

            imageView.setImageResource(itemImage[orderProductId[position]-1]);
            textViewProdName.setText(orderItemName[position]);
            textViewCost.setText(orderCost[position]);
            textViewPersonName.setText(orderPersonName[position]);

            return convertView;
        }
    }
    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //textCounter.setText(String.valueOf(millisUntilFinished));
            textCounter.setText(String.valueOf(millisUntilFinished));
            int progress = (int) (millisUntilFinished/100);
            if(progress < 100 && progress >= 75)
                textCounter.setText("Order is out for delivery");
            else if(progress < 75 && progress >= 50 )
                textCounter.setText("Order is being shipped");
            else if(progress < 50 && progress >= 25)
                textCounter.setText("Order is dispatched");
            else
            {
                textCounter.setText("Order is out for delivery");
            }
            progressBar.setProgress(100-progress);
        }

        @Override
        public void onFinish() {
            textCounter.setText("Delivered");
            textCounter.setCheckMarkDrawable(R.drawable.ok1);
            progressBar.setProgress(100);
        }

    }
}