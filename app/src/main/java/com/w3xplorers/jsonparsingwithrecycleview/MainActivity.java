package com.w3xplorers.jsonparsingwithrecycleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //ArrayList for person Names,email id's and mobile numbers
    ArrayList<String> personNames = new ArrayList<>();
    ArrayList<String> emailIds = new ArrayList<>();
    ArrayList<String> mobileNumbers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the reference of recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try{
            //get JSONobject from JSON file
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //fetcch JSONArray named users
            JSONArray userArray =obj.getJSONArray("users");
            //implement for loop for getting users list data
            for(int i = 0;i<userArray.length();i++){
                //create a JSONObject for fetching single user
                JSONObject userDetail = userArray.getJSONObject(i);
                //fetch email and name and store it in arraylist
                personNames.add(userDetail.getString("name"));
                emailIds.add(userDetail.getString("email"));
                //create a object for getting contact data from jsonobject
                JSONObject contact = userDetail.getJSONObject("contact");
                //fetch mobile number and store it in arraylist
                mobileNumbers.add(contact.getString("mobile"));
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        //call the contructor of CustomAdapter to send the reference data to adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this,personNames,emailIds,mobileNumbers);
        recyclerView.setAdapter(customAdapter); //set the adapter to recycler
    }

    public String loadJSONFromAsset(){
        String json = null;
        try{
            InputStream is = getAssets().open("users_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
