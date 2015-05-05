package br.com.cqt.chat;

import android.os.Handler;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import br.com.cqt.MainActivity;
import br.com.cqt.app.AppController;

public class Cliente implements Runnable {

    // Ws festa json url
    private String url = "http://10.0.2.2:8080/wsCQT_painel/ws/msg/";
    private String url2 = "http://10.0.2.2:8080/wsCQT_painel/ws/msg/";
    public static String id_festa;
    Boolean on = false;
    private int idAtual = 0;
    private int idMsg;
    public static String serverMessage;

    private String SERVERIP = "10.0.2.2"; // endereço localhost
    public static final int SERVERPORT = 2222;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;
    int tam = 0;

    String name;
    String msg;

    PrintWriter out;
    BufferedReader in;



    /**
     * Metodo recebe mensagens do ws
     */
    public Cliente(OnMessageReceived listener) {
        mMessageListener = listener;

    }

    public Cliente() {

    }


    /**
     * Envia postagem para o servidor
     */
    public void sendMessage(String message) {
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }

    /**
     * Envia postagem para o servidor
     */
    public void sendMessageFinal() {
        if (out != null && !out.checkError()) {
            out.println("/quit");
            out.flush();

        }
    }

    public void recebe() {
      url += id_festa + "/" + idAtual;
        Log.d("url",url);
        JsonArrayRequest partiesReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                idMsg = obj.getInt("id");
                                name = obj.getString("nome");
                                name += " diz: ";
                                msg = obj.getString("msg");
                                serverMessage = name + msg;
                                if (serverMessage != null && mMessageListener != null) {
                                    //class chama o metodo messageReceived
                                    mMessageListener.messageReceived(serverMessage);
                                }
                                serverMessage = null;
                                idAtual = idMsg;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }
        );
        AppController.getInstance().addToRequestQueue(partiesReq);
        url = url2;

    }


    public void run() {
        try {
            // ip do computador
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            Log.e("serverAddr", serverAddr.toString());
            Log.e("TCP Client", "C: Connecting...");

            // criar o socket de conexao
            Socket socket = new Socket(serverAddr, SERVERPORT);
            Log.e("TCP Server IP", SERVERIP);
            try {

                // envia postagem para o servidor
                out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);

                // recebe a postagem do servidor
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));

                 // recebe pergunta do  nome e respotas de entrou
                 for ( int i =0; i < 3; i++) {
                     serverMessage = in.readLine();
                     mMessageListener.messageReceived(serverMessage);
                  }

                recebe();
                 Log.e("RESPONSE FROM SERVER", "S: Received Message: '"
                        + serverMessage + "'");

            } catch (Exception e) {
                Log.e("TCP", "S: Error", e);
            }

        } catch (Exception e) {
            Log.e("TCP", "C: Error", e);
        }

    }




    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}