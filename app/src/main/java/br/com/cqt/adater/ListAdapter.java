package br.com.cqt.adater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import br.com.cqt.R;
import br.com.cqt.app.AppController;
import br.com.cqt.model.Party;

public class ListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Party> partyItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ListAdapter(Activity activity, List<Party> partyItems) {
        this.activity = activity;
        this.partyItems = partyItems;
    }

    @Override
    public int getCount() {
        return partyItems.size();
    }

    @Override
    public Object getItem(int location) {
        return partyItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView local = (TextView) convertView.findViewById(R.id.local);
        TextView descricao = (TextView) convertView.findViewById(R.id.descricao);
        TextView data = (TextView) convertView.findViewById(R.id.data);
        TextView msg = (TextView) convertView.findViewById(R.id.msg);

        // pega os dados da festa e coloca na array
        Party m = partyItems.get(position);

        // imagem da festa
        thumbNail.setImageUrl(m.getImg(), imageLoader);

        // Titulo - Nome da festa
        title.setText(m.getName());

        // local da festa
        local.setText("Local: " + (m.getLocal()));

        // Descrição da festa
        descricao.setText("Descrição: " + (m.getDesc()));

        // postagens
        msg.setText(m.getMsg());


        // pega a data de inicio da festa
        data.setText(m.getDate());

        return convertView;
    }

}