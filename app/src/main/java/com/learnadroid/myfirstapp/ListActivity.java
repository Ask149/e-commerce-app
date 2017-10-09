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
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static android.content.ContentValues.TAG;

public class ListActivity extends Fragment {

    SearchView sc;
    boolean flag;
    String searchtext;
    String itemName[];
    ListItem listItem;
    ArrayList<ListItem> itemArrayList;
    int productidlist[];
    String itemPrice[];
    int[] itemImage1 = {R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10};
    int count;
    int[] itemImage2 = {R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17,R.drawable.a18,R.drawable.a19,R.drawable.a20};
    int[] itemImage3 = {R.drawable.a21,R.drawable.a22,R.drawable.a23,R.drawable.a24,R.drawable.a25,R.drawable.a26,R.drawable.a27,R.drawable.a28,R.drawable.a29,R.drawable.a30};
    int[] itemImage4 = {R.drawable.a31,R.drawable.a32,R.drawable.a33,R.drawable.a34,R.drawable.a35,R.drawable.a36,R.drawable.a37,R.drawable.a38,R.drawable.a39,R.drawable.a40};
    int[] itemImage5 = {R.drawable.a41,R.drawable.a42,R.drawable.a43,R.drawable.a44,R.drawable.a45,R.drawable.a46,R.drawable.a47,R.drawable.a48,R.drawable.a49,R.drawable.a50};
    ConnectionClass connectionClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_list_item,container,false);
        final ListView listView = (ListView) view.findViewById(R.id.content_list_item_list_view);
        sc = (SearchView)view.findViewById(R.id.sc);
        HomeActivity.fragment_no=3;
        final CustomeAdapter customeAdapter ;
        connectionClass = new ConnectionClass();
        final Connection con = connectionClass.CONN();
        try {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("select * from product where category_id ="+HomeActivity.cat_id);

                flag=false;
                count=0;
                if(rs.first())
                    count=1;
                while(rs.next())
                    count++;

                ResultSet r;
                Statement st2 = con.createStatement();
                r=st2.executeQuery("select * from category where category_id="+HomeActivity.cat_id);
                if(r.first())
                    getActivity().setTitle(r.getString(2));

                sc.setQueryHint("Search Here");
                itemPrice = new String[count];
                itemName = new String[count];
                int index=0;
                itemArrayList = new ArrayList<ListItem>();
                itemArrayList.clear();
                if(rs.first())
                {
                    itemName[index] = rs.getString(2);
                    itemPrice[index] = rs.getString(4);
                    if(HomeActivity.cat_id==1)
                        itemArrayList.add(new ListItem(itemName[index],itemPrice[index],itemImage1[index++]));
                    else if(HomeActivity.cat_id==2)
                        itemArrayList.add(new ListItem(itemName[index],itemPrice[index],itemImage2[index++]));
                    else if(HomeActivity.cat_id==3)
                        itemArrayList.add(new ListItem(itemName[index],itemPrice[index],itemImage3[index++]));
                    else if(HomeActivity.cat_id==4)
                        itemArrayList.add(new ListItem(itemName[index],itemPrice[index],itemImage4[index++]));
                    else
                        itemArrayList.add(new ListItem(itemName[index],itemPrice[index],itemImage5[index++]));
                }
                while(rs.next())
                {
                    itemName[index] = rs.getString(2);
                    itemPrice[index] = rs.getString(4);
                    if(HomeActivity.cat_id==1)
                        itemArrayList.add(new ListItem(itemName[index],itemPrice[index],itemImage1[index++]));
                    else if(HomeActivity.cat_id==2)
                        itemArrayList.add(new ListItem(itemName[index],itemPrice[index],itemImage2[index++]));
                    else if(HomeActivity.cat_id==3)
                        itemArrayList.add(new ListItem(itemName[index],itemPrice[index],itemImage3[index++]));
                    else if(HomeActivity.cat_id==4)
                        itemArrayList.add(new ListItem(itemName[index],itemPrice[index],itemImage4[index++]));
                    else
                        itemArrayList.add(new ListItem(itemName[index],itemPrice[index],itemImage5[index++]));
                }

                sc.setIconified(false);
                sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        //customeAdapter.getFilter().filter(newText);
                        searchtext = newText.toString();
                        String sql = "select prod_id from product where prod_name LIKE '%"+searchtext+"%' and category_id = '"+HomeActivity.cat_id+"'";
                        try {
                            Statement stmt = con.createStatement();
                            ResultSet r = stmt.executeQuery(sql);
                           flag=true;
                            int count=1;
                            if(r.first())
                                count=1;
                            while(r.next())
                                count++;
                            productidlist = new int[count+1];
                            Toast.makeText(getActivity(),"Length is "+count,Toast.LENGTH_SHORT).show();
                            count=0;
                            if(r.first())
                                productidlist[count++]=r.getInt(1);
                            while(r.next())
                                productidlist[count++]=r.getInt(1);
                            String loop = "List : ";
                            for (int i = 0 ; i < count; i++)
                            {
                                loop += Integer.toString(productidlist[i]);
                                loop += " ";
                            }
                            Toast.makeText(getActivity(),"List is "+loop,Toast.LENGTH_LONG).show();
                        }
                        catch (SQLException e)
                        {
                            Toast.makeText(getActivity(),"Exception "+e,Toast.LENGTH_LONG).show();
                        }
                        if (TextUtils.isEmpty(newText.toString())) {
                            listView.clearTextFilter();
                        } else {
                            listView.setFilterText(newText.toString());
                        }
                        return true;
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    Fragment fragment = null;
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if(flag)
                        HomeActivity.prod_id = productidlist[position];
                        else
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
                customeAdapter = new CustomeAdapter(getActivity().getApplicationContext(),itemArrayList);
                listView.setAdapter(customeAdapter);

                listView.setTextFilterEnabled(true);

                } else {
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
        }
        catch (Exception e) {
            Toast.makeText(getActivity(), "Exception : "+e, Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public class CustomeAdapter extends ArrayAdapter<String> {

        Context context;
        public ArrayList<ListItem> itemArrayList;
        public ArrayList<ListItem> orig;

        public CustomeAdapter(Context context,ArrayList<ListItem> itemArrayList) {
            super(context, R.layout.listrow);
            this.context = context;
            this.itemArrayList=itemArrayList;
        }

        @Override
        public int getCount() {
            return itemArrayList.size();
        }

        @Override
        public String getItem(int position) {
            return itemArrayList.get(position).toString();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            ListHolder holder;
            convertView = getActivity().getLayoutInflater().inflate(R.layout.listrow, null);
            holder = new ListHolder();
            ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_image);
            holder.name = (TextView) convertView.findViewById(R.id.list_item_title);
            holder.price = (TextView) convertView.findViewById(R.id.list_item_price);

            imageView.setImageResource(itemArrayList.get(position).getImage());

            holder.name.setText(itemArrayList.get(position).getName());
            String pricetext = "";
            pricetext = "Rs. " + itemArrayList.get(position).getPrice();
            holder.price.setText(pricetext);
            convertView.setTag(holder);
            return convertView;
        }

        public class ListHolder
        {
            TextView name;
            TextView price;
        }

        public Filter getFilter() {
            return new Filter() {

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    final FilterResults oReturn = new FilterResults();
                    final ArrayList<ListItem> results = new ArrayList<ListItem>();
                    if (orig == null)
                        orig = itemArrayList;
                    if (constraint != null) {
                        if (orig != null && orig.size() > 0) {
                            for (final ListItem g : orig) {
                                if (g.getName().toLowerCase()
                                        .contains(constraint.toString().toLowerCase()))
                                    results.add(g);
                            }
                        }
                        oReturn.values = results;
                    }
                    return oReturn;
                }

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,
                                              FilterResults results) {
                    itemArrayList = (ArrayList<ListItem>) results.values;
                    notifyDataSetChanged();
                }
            };
        }
    }

    public class ListItem {

        private String name;
        private String price;
        private int image;

        public ListItem(String name,String price,int image){
            this.name = name;
            this.price = price;
            this.image= image;
        }

        public String getName(){
            return name;
        }

        public String getPrice(){
            return price;
        }

        public int getImage(){
            return image;
        }
    }
}