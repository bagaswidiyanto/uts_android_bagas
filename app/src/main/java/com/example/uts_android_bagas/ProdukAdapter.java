package com.example.uts_android_bagas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.GridViewHolder> {

    private List<Produk> barangs;
    private Context context;

    public ProdukAdapter(Context context, List<Produk> barangs) {
        this.barangs = barangs;
        this.context = context;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produk_item_layout, parent, false);
        GridViewHolder viewHolder = new GridViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        final int pos = position;
        final String id = barangs.get(position).getId();
        final String jenis_barang = barangs.get(position).getJenisBarang();
        final String type_barang = barangs.get(position).getTypeBarang();
        final String qty = barangs.get(position).getQTY();
        final String merek = barangs.get(position).getMerek();
        final String keterangan = barangs.get(position).getKeterangan();

        holder.tvId.setText(id);
        holder.tvJenisBarang.setText(jenis_barang);
        holder.tvTypeBarang.setText(type_barang);
        holder.tvQty.setText(qty);
        holder.tvMerek.setText(merek);
        holder.tvKeterangan.setText(keterangan);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("View Data");
                alertDialog.setMessage(id + " - " + jenis_barang);
                alertDialog.setPositiveButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.setNegativeButton("LIHAT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Bundle b = new Bundle();
                        b.putString("b_id", id);
                        b.putString("b_jenis_barang", jenis_barang);
                        b.putString("b_type_barang", type_barang);
                        b.putString("b_qty", qty);
                        b.putString("b_merek", merek);
                        b.putString("b_keterangan", keterangan);

                        Intent intent = new Intent(context, DataDetail.class);
                        intent.putExtras(b);

                        //context.startActivity(intent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            ((Activity) context).startActivityForResult(intent, 1, b);
                        }
                    }
                });

                alertDialog.setNeutralButton("HAPUS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url = "https://webwidiyantobagas.000webhostapp.com/api/produk.php?action=hapus&id=" + id;

                        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                url,
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {


                                        if (response.optString("result").equals("true")) {
                                            Toast.makeText(context, "Data" + " " + id + " " + "berhasil dihapus!", Toast.LENGTH_SHORT).show();

                                            barangs.remove(pos); //hapus baris customers
                                            notifyItemRemoved(pos); //refresh customer list ( ada animasinya )
                                            notifyDataSetChanged();

                                        } else {
                                            Toast.makeText(context, "Gagal" + " " + id + " " + "hapus data", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.d("Events: ", error.toString());

                                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show();
                            }
                        });

                        queue.add(jsObjRequest);
                    }
                });


                AlertDialog dialog = alertDialog.create();
                dialog.show();

                //untuk ubah warna tombol dialog
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.teal_200));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.yellow));
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.red));
            }
        });
    }

    @Override
    public int getItemCount() {
       return barangs.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvJenisBarang, tvTypeBarang, tvQty, tvMerek, tvKeterangan;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);
            tvJenisBarang = (TextView) itemView.findViewById(R.id.tv_jenis_barang);
            tvTypeBarang = (TextView) itemView.findViewById(R.id.tv_type_barang);
            tvQty = (TextView) itemView.findViewById(R.id.tv_qty);
            tvMerek = (TextView) itemView.findViewById(R.id.tv_merek);
            tvKeterangan = (TextView) itemView.findViewById(R.id.tv_keterangan);
        }
    }
}
