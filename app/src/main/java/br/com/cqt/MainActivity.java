package br.com.cqt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.cqt.adater.ListAdapter;
import br.com.cqt.app.AppController;
import br.com.cqt.chat.Cliente;
import br.com.cqt.chat.MainActivityCliente;
import br.com.cqt.model.Party;


    public class MainActivity extends Activity {
        // Log da tag
        private static final String TAG = MainActivity.class.getSimpleName();
        // Ws festa json url
        private static final String url = "https://api.import.io/store/data/0432f234-5c97-4028-beed-c318eb551d90/_query?input/webpage/url=http%3A%2F%2Fwww.lidl.ie%2Fen%2Ffreshoffers.htm&_user=731b23fc-30fc-43fa-81d3-345239c6de27&_apikey=731b23fc-30fc-43fa-81d3-345239c6de27%3AQgGSHk18XryNacv2%2F2nTYTUUSoL61oBQPgYz6wrbTEVJkWsynAl31UWNatUwqE%2FdIQ9h1vz1XFC9gC6FAS8CdQ%3D%3D";
        private ProgressDialog pDialog;
        private List<Party> partyList = new ArrayList<Party>();
        private ListView listView;
        private ListAdapter adapter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            listView = (ListView) findViewById(R.id.list);
            adapter = new ListAdapter(this, partyList);
            listView.setAdapter(adapter);
            pDialog = new ProgressDialog(this);
            // Fazendo a busca e mostrando carregando
            pDialog.setMessage("Carregando Festas...");
            pDialog.show();
            //Log.d("test","log");
            // Cria volley request obj
            JsonArrayRequest partyReq = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                            hidePDialog();

                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    Log.d("test",String.valueOf(obj));
                                    Party party = new Party();
                                    party.setId(obj.getInt("id"));
                                    party.setName(obj.getString("name"));
                                    party.setLocal(obj.getString("local"));
                                    party.setImg(obj.getString("logo"));
                                    party.setDesc(obj.getString("descricao"));
                                    party.setDate(obj.getString("date_start"));
                                    // add festas a array
                                    partyList.add(party);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            // retorna a lista com os dados atualizados
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    hidePDialog();
                }
            }
            );
            // Add request para a fila
            AppController.getInstance().addToRequestQueue(partyReq);
            // Metodo onclick
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent i = new Intent(getApplicationContext(), MainActivityCliente.class);
                    i.putExtra("id_festa", String.valueOf(partyList.get(position).getId()));
                    Cliente.id_festa = String.valueOf(partyList.get(position).getId());
                    startActivity(i);

                }
            });


        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            hidePDialog();
        }

        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }


    }
