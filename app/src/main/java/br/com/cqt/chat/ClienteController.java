package br.com.cqt.chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.cqt.R;


public class ClienteController extends BaseAdapter {

        private ArrayList<String> mListItems;
        private LayoutInflater mLayoutInflater;

        public ClienteController(Context context, ArrayList<String> arrayList){

            mListItems = arrayList;
            //get the layout inflater
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            //getCount() represents how many items are in the list
            return mListItems.size();
        }

        @Override
        //get the data of an item from a specific position
        //i represents the position of the item in the list
        public Object getItem(int i) {
            return null;
        }

        @Override
        //get the position id of the item from the list
        public long getItemId(int i) {
            return 0;
        }

        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //Date date = new Date();

        @Override

        public View getView(int position, View view, ViewGroup viewGroup) {


            if (view == null) {
                view = mLayoutInflater.inflate(R.layout.list_row_chat, null);
            }


            String stringItem = mListItems.get(position);

            int i = stringItem.indexOf(MainActivityCliente.names);

            if (stringItem != null)  {
                TextView itemName = (TextView) view.findViewById(R.id.list_item_text_view);
                  // itemName.setBackgroundResource(R.drawable.bubble_yellow);
                if (stringItem.indexOf(MainActivityCliente.names) >= 0) {
                    itemName.setBackgroundResource(R.drawable.bubble_green);

                    itemName.setText(stringItem);
                } else {
                    itemName.setBackgroundResource(R.drawable.bubble_yellow);
                    itemName.setText(stringItem);
                }

            }
            return view;

        }
    }