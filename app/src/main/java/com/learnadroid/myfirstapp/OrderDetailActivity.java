package com.learnadroid.myfirstapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDetailActivity extends Fragment {

    String s;
    String quan;
    EditText etaddress;
    EditText etname;
    EditText econtact;
    int qtity ;
    String ts;
    private static final int MY_NOTIFICATION_ID=1;
    NotificationManager notificationManager;
    Notification myNotification;
    Button bbtn;
    int total_price_int;
    int total_price_int2;
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
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HomeActivity.fragment_no=5;
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
            Statement st1 = con.createStatement();
            rs = st1.executeQuery("select c.name,c.quantity,c.contact_no,p.prod_name,c.quantity from cart c,product p where c.user_id='"+MainActivity.user_id+"' and p.prod_id = c.prod_id;");
            String sql = "select * from product where prod_id = "+HomeActivity.prod_id+";";
            PreparedStatement st = con.prepareStatement(sql);
            rs = st.executeQuery();
            s=null;

            if(rs.next())
            {
                total_price_int=rs.getInt(4);
                s=rs.getString(4);
            }
            quan = quantity.getText().toString();
            qtity = Integer.parseInt(quan);
            total_price_int2 = total_price_int*qtity;
            ts=Integer.toString(total_price_int2);
            order_price.setText(s);
            order_total_price.setText(ts);



            final RadioButton radiobutton = (RadioButton) view.findViewById(R.id.order_detail_confirm);
            radiobutton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quan = quantity.getText().toString();
                            qtity = Integer.parseInt(quan);
                            total_price_int2 = total_price_int*qtity;
                            ts=Integer.toString(total_price_int2);
                            order_price.setText(s);
                            radiobutton.setButtonDrawable(R.drawable.ok1);
                            order_total_price.setText(ts);
                        }
                    }
            );

        bbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                    quan = quantity.getText().toString();
                    HomeActivity.flag=true;
                    qtity = Integer.parseInt(quan);
                    total_price_int2 = total_price_int*qtity;
                    ts=Integer.toString(total_price_int2);
                    order_price.setText(s);
                    order_total_price.setText(ts);

                    String data1, data2, data3, data4;
                    data1 = etaddress.getText().toString();
                    data2 = econtact.getText().toString();
                    data3 = etname.getText().toString();
                    data4 = quantity.getText().toString();

                    if (data1.trim().equals("") || data2.length() != 10 || data3.trim().equals("") || data4.trim().equals("0") || data4.trim().equals("")) {
                        Toast.makeText(getActivity(), "Please enter valid details", Toast.LENGTH_LONG).show();
                    } else {
                        String name, address;
                        String contact;
                        address = etaddress.getText().toString();
                        name = etname.getText().toString();
                        contact = econtact.getText().toString();
                        Connection con1 = connectionClass.CONN();

                        Statement st1 = null;
                        try {
                            st1 = con1.createStatement();
                            String sql2 = "insert into cart(user_id,name,address,contact_no,prod_id,quantity,total_cost) values('" + MainActivity.user_id + "','" + name + "','" + address + "'," + contact + "," + HomeActivity.prod_id + "," + qtity + "," + total_price_int + ");";
                            st1.execute(sql2);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Fragment fragment = new OrderListActivity();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_home, fragment);
                        ft.addToBackStack(null);
                        ft.commit();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            myNotification = new Notification.Builder(getActivity())
                                    .setContentTitle(DetailActivity.product_name)
                                    .setContentText("Order has been successfully placed !")
                                    .setTicker("Notification!")
                                    .setWhen(System.currentTimeMillis())
                                    .setDefaults(Notification.DEFAULT_SOUND)
                                    .setAutoCancel(true)
                                    .setSmallIcon(R.drawable.mobile)
                                    .build();
                        }

                        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);

                    }
                }
            });
        }
        catch (SQLException e)
        {
            Toast.makeText(getActivity(),"Exception : "+e,Toast.LENGTH_SHORT).show();
        }
    }
}