package com.learnadroid.myfirstapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;

public class ListActivity extends Fragment {

    String itemName[];
    String itemPrice[];
    int[] itemImage1 = {R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10};
    int[] itemImage2 = {R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17,R.drawable.a18,R.drawable.a19,R.drawable.a20};
    int[] itemImage3 = {R.drawable.a21,R.drawable.a22,R.drawable.a23,R.drawable.a24,R.drawable.a25,R.drawable.a26,R.drawable.a27,R.drawable.a28,R.drawable.a29,R.drawable.a30};
    int[] itemImage4 = {R.drawable.a31,R.drawable.a32,R.drawable.a33,R.drawable.a34,R.drawable.a35,R.drawable.a36,R.drawable.a37,R.drawable.a38,R.drawable.a39,R.drawable.a40};
    int[] itemImage5 = {R.drawable.a41,R.drawable.a42,R.drawable.a43,R.drawable.a44,R.drawable.a45,R.drawable.a46,R.drawable.a47,R.drawable.a48,R.drawable.a49,R.drawable.a50};
    ConnectionClass connectionClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_list_item,container,false);
        ListView listView = (ListView) view.findViewById(R.id.content_list_item_list_view);
        HomeActivity.fragment_no=3;
        CustomeAdapter customeAdapter ;
        connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();

        try {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("select * from product where category_id ="+HomeActivity.cat_id);

                ResultSet r;
                Statement st2 = con.createStatement();
                r=st2.executeQuery("select * from category where category_id="+HomeActivity.cat_id);
                if(r.first())
                    getActivity().setTitle(r.getString(2));

                itemPrice = new String[10];
                itemName = new String[10];
                int index=0;
                if(rs.first())
                {
                    itemName[index] = rs.getString(2);
                    itemPrice[index++] = rs.getString(4);
                }
                while(rs.next() && index<10)
                {
                    itemName[index] = rs.getString(2);
                    itemPrice[index++] = rs.getString(4);
                }
                customeAdapter = new CustomeAdapter(getActivity().getApplicationContext());

                listView.setAdapter(customeAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    Fragment fragment = null;
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HomeActivity.prod_id = (((HomeActivity.cat_id-1)*10)+position+1);
                        HomeActivity.position = position;
                        fragment = new DetailActivity();
                        if (fragment != null) {
                            int thisid = getId();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(thisid, fragment);
                                ft.commit();
                            } else {
                                Toast.makeText(getActivity(), "Null Fragment", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
        }
        catch (Exception e) {
            Toast.makeText(getActivity(), "Exception : "+e, Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public class CustomeAdapter extends ArrayAdapter<String>{

        Context context;
        public CustomeAdapter(Context context) {
            super(context,R.layout.listrow);
            this.context=context;
        }

        @Override
        public int getCount() {
            return itemName.length;
        }

        @Override
        public String getItem(int position) {
            return itemName[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            convertView = getActivity().getLayoutInflater().inflate(R.layout.listrow,null);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_image);
            TextView textViewTitle = (TextView) convertView.findViewById(R.id.list_item_title);
            TextView textViewPrice = (TextView) convertView.findViewById(R.id.list_item_price);

            if(HomeActivity.cat_id==1)
                imageView.setImageResource(itemImage1[position]);
            else if(HomeActivity.cat_id==2)
                imageView.setImageResource(itemImage2[position]);
            else if(HomeActivity.cat_id==3)
                imageView.setImageResource(itemImage3[position]);
            else if(HomeActivity.cat_id==4)
                imageView.setImageResource(itemImage4[position]);
            else if(HomeActivity.cat_id==5)
                imageView.setImageResource(itemImage5[position]);
            else
                imageView.setImageResource(itemImage1[position]);
            textViewTitle.setText(itemName[position]);
            textViewPrice.setText("Rs. "+itemPrice[position]);

            return convertView;
        }
    }
}