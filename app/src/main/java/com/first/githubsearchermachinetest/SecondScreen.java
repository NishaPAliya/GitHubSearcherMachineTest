package com.first.githubsearchermachinetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SecondScreen extends AppCompatActivity {
    ImageView avatar_image;
    TextView username,email,location,joindate,followers,following;
    //String user_url = "https://api.github.com/users/"+username;
    ArrayList<RepoModel> repoArraylist = new ArrayList<>();

    RecyclerView reporecyclerview;
    SearchView searchView;
    RepoAdapter repoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second_screen);
        String name = getIntent().getStringExtra("name");
        String avatar = getIntent().getStringExtra("avatarimage");
        String user_url = "https://api.github.com/users/"+name;
        String repourl = "https://api.github.com/users/"+name+"/repos";
        //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();

        username = (TextView)findViewById(R.id.txtusername) ;
        email = (TextView)findViewById(R.id.txtemail);
        location = (TextView)findViewById(R.id.txtlocation);
        joindate = (TextView)findViewById(R.id.txtjoindate);
        followers = (TextView)findViewById(R.id.txtfollowers);
        following= (TextView)findViewById(R.id.txtfollowing);
        reporecyclerview = (RecyclerView)findViewById(R.id.recyclerViewrepo);
        searchView = (SearchView)findViewById(R.id.searchview);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }
        });

        username.setText(name);
        avatar_image = (ImageView) findViewById(R.id.avatar_image);
        Glide.with(this)
                .load(avatar)
                .into(avatar_image);

        RequestQueue requestQueue = Volley.newRequestQueue(SecondScreen.this);
        StringRequest srequest = new StringRequest(user_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobject = new JSONObject(response);
                    email.setText("NAME : "+jobject.getString("email"));
                    location.setText("LOCATION : "+jobject.getString("location").toString());
                    joindate.setText("JOIN DATE : "+jobject.getString("created_at"));
                    followers.setText("FOLLOWERS : "+jobject.getString("followers"));
                    following.setText("FOLLOWING : "+jobject.getString("following"));

                    //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(srequest);

        StringRequest repoRequest = new StringRequest(repourl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i =0;i< jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String reponame = jsonObject.getString("name");
                        //Toast.makeText(getApplicationContext(),reponame,Toast.LENGTH_LONG).show();
                        String fork = jsonObject.getString("forks_count");
                        String star = jsonObject.getString("stargazers_count");

                        RepoModel repoModel = new RepoModel(id,reponame,fork,star);
                        repoArraylist.add(repoModel);
                    }
                    repoAdapter = new RepoAdapter(repoArraylist,SecondScreen.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SecondScreen.this);
                    reporecyclerview.setLayoutManager(linearLayoutManager);
                    reporecyclerview.setAdapter(repoAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(repoRequest);
    }
    private void filterList(String text) {
        List<RepoModel> filteredlist = new ArrayList<>();
        for (RepoModel item:repoArraylist)
        {
            if (item.getReponame().toLowerCase(Locale.ROOT).contains(text.toLowerCase()))
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
            repoAdapter.setfilteredlist((ArrayList<RepoModel>) filteredlist);
        }
    }
}