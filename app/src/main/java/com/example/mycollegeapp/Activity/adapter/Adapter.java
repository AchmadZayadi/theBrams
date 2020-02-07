package com.example.mycollegeapp.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycollegeapp.R;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    private Context context;
    private ArrayList<DataModel> dataModelArrayList;

    public Adapter(Context context, ArrayList<DataModel> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row, null, true);

            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.tvidtrans = (TextView) convertView.findViewById(R.id.id_trans);
            holder.tvidcus = (TextView) convertView.findViewById(R.id.id_cus);
            holder.tvharga = (TextView) convertView.findViewById(R.id.harga);
            holder.tvstatus = (TextView) convertView.findViewById(R.id.status);
            holder.tvket = (TextView) convertView.findViewById(R.id.ket);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the viewaaaaaaa
            holder = (ViewHolder)convertView.getTag();
        }


        holder.tvidtrans.setText("ID Transaksi "+dataModelArrayList.get(position).getId_trans());
        holder.tvidcus.setText("Nomor Transaksi: "+dataModelArrayList.get(position).getId_cus());
        holder.tvharga.setText("Harga: "+dataModelArrayList.get(position).getHarga());
        holder.tvstatus.setText("status: "+dataModelArrayList.get(position).getStatus());
        holder.tvket.setText("keterangan: "+dataModelArrayList.get(position).getKeterangan());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvidtrans, tvidcus, tvharga,tvstatus, tvket ;
        protected ImageView iv;
    }

}
