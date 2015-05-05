package br.com.cqt.chat;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.cqt.R;


public class MainActivityCliente extends Activity {

    private ListView mList;
    private ArrayList<String> arrayList;
    private ClienteController mAdapter;
    private Cliente mClient;
    public static String names = "";
    public static String id;
    private String message;
    private int cont = 0;
    private Handler handler = new Handler();
    int aux;


    private boolean on;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        on = true;

        // pega o id da festa que o user clicou
        id = this.getIntent().getStringExtra("id_festa");
        arrayList = new ArrayList<String>();

        final EditText editText = (EditText) findViewById(R.id.editText);
        ImageButton send = (ImageButton) findViewById(R.id.send_button);
        ImageButton refresh = (ImageButton) findViewById(R.id.refresh);

        //relate the listView from java to the one created in xml
        mList = (ListView) findViewById(R.id.list);
        mAdapter = new ClienteController(this, arrayList);
        mList.setAdapter(mAdapter);
        // conecta ao servidor
        new connectTask().execute("");

        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mClient.recebe();

            }

        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = editText.getText().toString();

                if (cont == 0) {
                    names = message;
                    handler.postDelayed(runnable,3000);
                }
                //envia as msg para o servidor com o id da festa
                if (mClient != null) {
                    mClient.sendMessage(id + message);
                }

                //refresh na lista
                mAdapter.notifyDataSetChanged();
                editText.setText("");
                cont = 1;
                //chama a thread que recupera as msgs automaticamente
               // handler.postDelayed(runnable,2000);
               // aux =0;

             }
        });



    }

    // recupera as msgs a cada 2 segundos
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
         if(on) {
             mClient.recebe();
             handler.postDelayed(this, 3000);
         }
        }
    };


    public class connectTask extends AsyncTask<String, String, Cliente> {

        @Override
        protected Cliente doInBackground(String... message) {

            //cria um objeto cliente
            mClient = new Cliente(new Cliente.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //chama o metodo onProgressUpdate
                    publishProgress(message);
                }
            });
            mClient.run();
            //chama a thread que recupera as msgs automaticamente
           // handler.postDelayed(runnable,2000);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            arrayList.add(values[0]);
            mAdapter.notifyDataSetChanged();
        }
    }


    protected void onDestroy(){
        super.onDestroy();
        // quando sai da activity encerra a thread de msgs
        on = false;
        mClient.sendMessageFinal();
        Log.i("destroy", getLocalClassName() + "DESTRUIU E PARAOU A THREAD");

    }




}
