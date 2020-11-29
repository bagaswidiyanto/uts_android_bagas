package com.example.uts_android_bagas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class DataDetail extends AppCompatActivity {
    ProgressBar pb;
    EditText etID, etJenisBarang, etTypeBarang, etQTY, etMerek, etKeterangan;
    Button btHapus, btUbah;
    String id, jenis_barang, type_barang, qty, merek, keterangan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_detail);

        pb = (ProgressBar) findViewById(R.id.pb);
        etID = (EditText) findViewById(R.id.et_id);
        etJenisBarang = (EditText) findViewById(R.id.et_jenis_barang);
        etTypeBarang = (EditText) findViewById(R.id.et_type_barang);
        etQTY = (EditText) findViewById(R.id.et_qty);
        etMerek = (EditText) findViewById(R.id.et_merek);
        etKeterangan = (EditText) findViewById(R.id.et_keterangan);
        btUbah = (Button) findViewById(R.id.btUbah);
        btHapus = (Button) findViewById(R.id.btHapus);

        //tombol back
        getSupportActionBar().setTitle("Detail"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action back

        //tangkap bundle
        Bundle bundle = null;
        bundle = this.getIntent().getExtras();

        //letakkan isi bundle
        id = bundle.getString("b_id");
        jenis_barang = bundle.getString("b_jenis_barang");
        type_barang = bundle.getString("b_type_barang");
        qty = bundle.getString("b_qty");
        merek = bundle.getString("b_merek");
        keterangan = bundle.getString("b_keterangan");

        //letakkan pada textview
        etID.setText(id);
        etJenisBarang.setText(jenis_barang);
        etTypeBarang.setText(type_barang);
        etQTY.setText(qty);
        etMerek.setText(merek);
        etKeterangan.setText(keterangan);


        //operasi ubah data

        btUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenis_barang =  etJenisBarang.getText().toString();
                type_barang =  etTypeBarang.getText().toString();
                qty =  etQTY.getText().toString();
                merek =  etMerek.getText().toString();
                keterangan =  etKeterangan.getText().toString();


                pb.setVisibility(ProgressBar.VISIBLE); //munculkan progressbar

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://webwidiyantobagas.000webhostapp.com/api/produk.php?action=ubah&id="+id+"&jenis_barang="+jenis_barang+"&type_barang="+type_barang+"&qty="+qty+"&merek="+merek+"&keterangan="+keterangan;

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                if (response.optString("result").equals("true")){
                                    Toast.makeText(getApplicationContext(), "Data terubah!", Toast.LENGTH_SHORT).show();

                                    finish(); //tutup activity
                                }else{
                                    Toast.makeText(getApplicationContext(), "O ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Events: ", error.toString());

                        Toast.makeText(getApplicationContext(), "Hmm, masalah internet atau data yang kamu masukkan", Toast.LENGTH_SHORT).show();

                        pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar
                    }
                });

                queue.add(jsObjRequest);
            }
        });

        btHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(ProgressBar.VISIBLE); //tampilkan progress bar

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://webwidiyantobagas.000webhostapp.com/api/produk.php?action=hapus&id="+ id;

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                if (response.optString("result").equals("true")){
                                    Toast.makeText(getApplicationContext(), "Data terhapus!", Toast.LENGTH_SHORT).show();

                                    finish(); //tutup activity
                                }else{
                                    Toast.makeText(getApplicationContext(), "O ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Events: ", error.toString());

                        pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar

                        Toast.makeText(getApplicationContext(), "Hmm, masalah internet atau data yang kamu masukkan", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsObjRequest);
            }
        });
    }

}