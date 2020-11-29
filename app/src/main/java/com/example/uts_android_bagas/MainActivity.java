package com.example.uts_android_bagas;

import android.content.DialogInterface;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Barang Masuk"); // for set actionbar title

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                // Tarik Layout
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.form_input_barang_layout, null);

                dialog.setView(view);
                dialog.setCancelable(true);

                // Definisi Objek
                final EditText etID = (EditText) view.findViewById(R.id.et_id);
                final EditText etJenisBarang = (EditText) view.findViewById(R.id.et_jenis_barang);
                final EditText etTypeBarang = (EditText) view.findViewById(R.id.et_type_barang);
                final EditText etQTY = (EditText) view.findViewById(R.id.et_qty);
                final EditText etMerek = (EditText) view.findViewById(R.id.et_merek);
                final EditText etKeterangan = (EditText) view.findViewById(R.id.et_keterangan);

                dialog.setPositiveButton("SIMPAN",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String id, jenis_barang, type_barang, qty, merek, keterangan;

                                id = etID.getText().toString();
                                jenis_barang = etJenisBarang.getText().toString();
                                type_barang = etTypeBarang.getText().toString();
                                qty = etQTY.getText().toString();
                                merek = etMerek.getText().toString();
                                keterangan = etKeterangan.getText().toString();


                                // Simpan Data
                                simpanData(id, jenis_barang, type_barang, qty, merek, keterangan);
                            }
                        });

                dialog.setNegativeButton("BATAL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog.show();
            }
        });
    }

    private void simpanData(String id, String jenis_barang, String type_barang, String qty, String merek, String keterangan) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://webwidiyantobagas.000webhostapp.com/api/produk.php?action=simpan&id=" + id + "&jenis_barang=" + jenis_barang + "&type_barang=" + type_barang + "&qty=" + qty + "&merek=" + merek + "&keterangan=" + keterangan;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response.optString("result").equals("true")) {
                            Toast.makeText(getApplicationContext(), "Data" + " " + id + " " +"Berhasil Ditambah!", Toast.LENGTH_SHORT).show();

                            // panggil fungsi load pada fragment
                            loadFragment(new FirstFragment());
                        } else {
                            Toast.makeText(getApplicationContext(), "Sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("Events: ", error.toString());

                Toast.makeText(getApplicationContext(), "Masalah internet atau data yang kamu masukan", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}