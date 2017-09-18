package com.learnadroid.myfirstapp;

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
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * Created by Ashish on 09-09-2017.
 */

public class ListActivity extends Fragment {

    String[] itemName1 = {"ONE PLUS 5","ONE PLUS 3T","IPHONE 7+","SAMSUNG GALAXY S8+","GOOGLE PIXEL XL","LG G6","XPERIA XZ","REDMI NOTE 4","HTC 10","NOKIA 6"};
    String[] itemName2 = {"DELL XPS 13","HP SPECTER 360","MACBOOK AIR 13","ASUS UX305LA-FB055T","HP ENVY 14-J008TX","LENOVO Z51-70","HP PAVILION 15AB032TX","ASUS X555LJXX132H","MACBOOK PRO","LENOVO YOGA 500"};
    int[] itemImage = {R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,  R.drawable.ic_menu_camera };
    ConnectionClass connectionClass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_list_item,container,false);
        ListView listView = (ListView) view.findViewById(R.id.content_list_item_list_view);
        ArrayAdapter<String> listViewAdapter;
        connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();
        try {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from product");
                ResultSetMetaData rsmd = rs.getMetaData();
                String itemName[] = new String[10];
                String itemPrice[] = new String[10];
                int index=0;
                while(rs.next())
                {
                    itemName[index] = rs.getString(2);
                    itemPrice[index++] = rs.getString(4);
                }
                listViewAdapter = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_list_item_1, itemName);
                listView.setAdapter(listViewAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                Fragment fragment = null;

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HomeActivity.prod_id=position;
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
            Toast.makeText(getActivity(), "Exception Generated !!", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}