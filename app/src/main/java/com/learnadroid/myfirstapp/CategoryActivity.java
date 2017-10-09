package com.learnadroid.myfirstapp;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

public class CategoryActivity extends Fragment {

    ImageView imageView1,imageView2,imageView3,imageView4,imageView5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_category,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CircleMenu circleMenu = (CircleMenu) view.findViewById(R.id.circle_menu);

        circleMenu.setMinimumWidth(200);
        circleMenu.setMinimumHeight(200);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),  R.drawable.ic_shopping_cart_white_32dp , R.drawable.cat);
        circleMenu.addSubMenu(Color.parseColor("#258CFF"), R.drawable.mobile)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.laptop)
                .addSubMenu(Color.parseColor("#FF4B32"), R.drawable.wmachine)
                .addSubMenu(Color.parseColor("#8A39FF"), R.drawable.fridge)
                .addSubMenu(Color.parseColor("#FF6A00"), R.drawable.tv);

        circleMenu.setOnMenuSelectedListener(new OnMenuSelectedListener() {

                 @Override
                 public void onMenuSelected(int index) {
                     switch (index) {
                         case 0:
                             HomeActivity.cat_id=1;
                             Fragment fragment = new ListActivity();
                             FragmentTransaction ft = getFragmentManager().beginTransaction();
                             ft.replace(R.id.content_home,fragment);
                             ft.commit();
                             break;
                         case 1:
                             HomeActivity.cat_id=2;
                             fragment = new ListActivity();
                             ft = getFragmentManager().beginTransaction();
                             ft.replace(R.id.content_home,fragment);
                             ft.commit();
                             break;
                         case 2:
                             HomeActivity.cat_id=3;
                             fragment = new ListActivity();
                             ft = getFragmentManager().beginTransaction();
                             ft.replace(R.id.content_home,fragment);
                             ft.commit();
                             break;
                         case 3:
                             HomeActivity.cat_id=4;
                             fragment = new ListActivity();
                             ft = getFragmentManager().beginTransaction();
                             ft.replace(R.id.content_home,fragment);
                             ft.commit();
                             break;
                         case 4:
                             HomeActivity.cat_id=5;
                             fragment = new ListActivity();
                             ft = getFragmentManager().beginTransaction();
                             ft.replace(R.id.content_home,fragment);
                             ft.commit();
                             break;
                     }
                 }
             }

        );

        circleMenu.setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

                 @Override
                 public void onMenuOpened() {
                 }

                 @Override
                 public void onMenuClosed() {
                 }
             }
        );

        HomeActivity.fragment_no=2;
        getActivity().setTitle("Category");
/*        imageView1 = (ImageView) view.findViewById(R.id.cat_mobile_button);
        imageView2 = (ImageView) view.findViewById(R.id.cat_laptop_button);
        imageView3 = (ImageView) view.findViewById(R.id.cat_washing_button);
        imageView4 = (ImageView) view.findViewById(R.id.cat_fridge_button);
        imageView5 = (ImageView) view.findViewById(R.id.cat_tv_button);
        imageView1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
              HomeActivity.cat_id=1;
              Fragment fragment = new ListActivity();
              FragmentTransaction ft = getFragmentManager().beginTransaction();
              ft.replace(R.id.content_home,fragment);
              ft.commit();
              }
          }
        );
        imageView2.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
              HomeActivity.cat_id=2;
              Fragment fragment = new ListActivity();
              FragmentTransaction ft = getFragmentManager().beginTransaction();
              ft.replace(R.id.content_home,fragment);
              ft.commit();
          }
                                      }
        );
        imageView3.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
              HomeActivity.cat_id=3;
              Fragment fragment = new ListActivity();
              FragmentTransaction ft = getFragmentManager().beginTransaction();
              ft.replace(R.id.content_home,fragment);
              ft.commit();
              }
          }
        );
        imageView4.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                  HomeActivity.cat_id=4;
                  Fragment fragment = new ListActivity();
                  FragmentTransaction ft = getFragmentManager().beginTransaction();
                  ft.replace(R.id.content_home,fragment);
                  ft.commit();
              }
          }
        );
        imageView5.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                  HomeActivity.cat_id=5;
                  Fragment fragment = new ListActivity();
                  FragmentTransaction ft = getFragmentManager().beginTransaction();
                  ft.replace(R.id.content_home,fragment);
                  ft.commit();
              }
          }
        );*/
    }
}