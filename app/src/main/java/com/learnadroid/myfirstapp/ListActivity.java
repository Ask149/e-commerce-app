package com.learnadroid.myfirstapp;

import android.content.Context;
import android.os.Bundle;
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
    int[] itemImage = {R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,  R.drawable.ic_menu_camera };
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

//              ResultSetMetaData rsmd = rs.getMetaData();

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
//                listViewAdapter = new ArrayAdapter<String>(
//                getActivity(),android.R.layout.simple_list_item_1);
//                listView.setAdapter(listViewAdapter);

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
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getActivity().getLayoutInflater().inflate(R.layout.listrow,null);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_image);
            TextView textViewTitle = (TextView) convertView.findViewById(R.id.list_item_title);
            TextView textViewPrice = (TextView) convertView.findViewById(R.id.list_item_price);

            imageView.setImageResource(itemImage[position]);
            textViewTitle.setText(itemName[position]);
            textViewPrice.setText(itemPrice[position]);

            return convertView;
        }
    }
}