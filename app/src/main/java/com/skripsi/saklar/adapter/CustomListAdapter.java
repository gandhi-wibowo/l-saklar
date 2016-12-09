package com.skripsi.saklar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skripsi.saklar.Constant;
import com.skripsi.saklar.MainActivity;
import com.skripsi.saklar.R;
import com.skripsi.saklar.model.Saklar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomListAdapter extends  BaseAdapter {
	// untuk nampilin mobil di main menu
	private Activity activity;
	private LayoutInflater inflater;
	private List<Saklar> saklarItems;

	public CustomListAdapter(Activity activity, List<Saklar> saklarItems) {
		this.activity = activity;
		this.saklarItems = saklarItems;
	}

	@Override
	public int getCount() {
		return saklarItems.size();
	}

	@Override
	public Object getItem(int location) {
		return saklarItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (inflater == null) inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) convertView = inflater.inflate(R.layout.list_row, null);
        TextView nmSaklar = (TextView) convertView.findViewById(R.id.textView);
		TextView stsSaklar = (TextView) convertView.findViewById(R.id.textView2);
        Button btnOn = (Button) convertView.findViewById(R.id.on);
        Button btnOff = (Button) convertView.findViewById(R.id.off);
		final Saklar m = saklarItems.get(position);
        nmSaklar.setText(m.getNamaSaklar());
		stsSaklar.setText(m.getStatusSaklar());
        String status = m.getStatusSaklar();
        if(status.equals("mati")){
            btnOff.setVisibility(View.GONE);
            btnOn.setVisibility(View.VISIBLE);
        }
        else if(status.equals("hidup")){
            btnOn.setVisibility(View.GONE);
            btnOff.setVisibility(View.VISIBLE);
        }

		btnOn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.url_saklar,
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
                                Intent intent = new Intent(activity.getApplicationContext(),MainActivity.class);
                                activity.startActivity(intent);							}
						},
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
                                Intent intent = new Intent(activity.getApplicationContext(),MainActivity.class);
                                activity.startActivity(intent);							}
						}
				) {
					@Override
					public String getBodyContentType() {
						return "application/x-www-form-urlencoded";
					}

					protected Map<String, String> getParams() {
						Map<String, String> params = new HashMap<String, String>();
						params.put("id_saklar", m.getIdSaklar());
						params.put("status_saklar", "hidup");
						return params;
					}
				};
				RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
				requestQueue.add(stringRequest);
			}
		});

		btnOff.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.url_saklar,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Intent intent = new Intent(activity.getApplicationContext(),MainActivity.class);
                                activity.startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Intent intent = new Intent(activity.getApplicationContext(),MainActivity.class);
                                activity.startActivity(intent);                            }
                        }
                ) {
                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded";
                    }

                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id_saklar", m.getIdSaklar());
                        params.put("status_saklar", "mati");
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
                requestQueue.add(stringRequest);

			}
		});



		return convertView;
	}


}