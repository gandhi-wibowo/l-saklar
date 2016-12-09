package com.skripsi.saklar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skripsi.saklar.adapter.CustomListAdapter;
import com.skripsi.saklar.app.AppController;
import com.skripsi.saklar.model.Saklar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    private List<Saklar> saklarList = new ArrayList<Saklar>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, saklarList);
        listView.setAdapter(adapter);
        Cekjaringan();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Saklar listSaklar = saklarList.get(position);
                Intent intent = new Intent(getApplicationContext(),RenameSaklar.class);
                Bundle b = new Bundle();
                b.putString("id_saklar",listSaklar.getIdSaklar());
                b.putString("nama_saklar",listSaklar.getNamaSaklar());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
    private void Cekjaringan(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.url_base + "/Welcome/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GetSaklar();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("! Alert");
                        builder.setMessage("Terjadi msalah saat menghubungi Server !");
                        builder.setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cek", "coba");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);;
    }
    private void GetSaklar(){
        JsonArrayRequest saklarReq = new JsonArrayRequest(Constant.url_saklar,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Saklar saklar = new Saklar();
                                saklar.setIdSaklar(obj.getString("id_saklar"));
                                saklar.setNamaSaklar(obj.getString("nama_saklar"));
                                saklar.setStatusSaklar(obj.getString("status_saklar"));
                                saklarList.add(saklar);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Errornya tuh disini :" + error);

            }
        });
        AppController.getInstance().addToRequestQueue(saklarReq);

    }


}
