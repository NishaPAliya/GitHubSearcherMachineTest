package com.first.githubsearchermachinetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
String url = "https://api.github.com/users";
ArrayList<UserModel> userArraylist = new ArrayList<>();
RecyclerView recyclerView;
SearchView searchView;
    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        searchView = (SearchView) findViewById(R.id.searchview);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i =0;i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("login");
                        String repo = jsonObject.getString("repos_url");
                        String image = jsonObject.getString("avatar_url");
                        //                        String urlsub = "https://api.github.com/users/"+name;
//                        StringRequest stringRequest1 = new StringRequest(urlsub, new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//
//                                try {
//                                    JSONObject jobject1 = new JSONObject(response);
//                                    repo = jobject1.getInt("public_repos");
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        });
//                        requestQueue.add(stringRequest1);


                        UserModel userModel = new UserModel(id,name,repo,image);
                        userArraylist.add(userModel);

                        //Toast.makeText(getApplicationContext(),repo,Toast.LENGTH_LONG).show();
                    }
                    userAdapter = new UserAdapter(userArraylist,MainActivity.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(userAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);


    }

    private void filterList(String text) {
        List<UserModel> filteredlist = new ArrayList<>();
        for (UserModel item:userArraylist)
        {
            if (item.getLogin().toLowerCase(Locale.ROOT).contains(text.toLowerCase()))
            {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"No data found.....",Toast.LENGTH_LONG).show();
        }
        else
        {
            userAdapter.setfilteredlist((ArrayList<UserModel>) filteredlist);
        }
    }
}