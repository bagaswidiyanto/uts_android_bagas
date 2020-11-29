package com.example.uts_android_bagas;

public class Produk {
    String id, jenis_barang , type_barang, qty, merek, keterangan;

    public Produk(String id, String jenis_barang, String type_barang, String qty, String merek, String keterangan) {
        this.id = id;
        this.jenis_barang = jenis_barang;
        this.type_barang = type_barang;
        this.qty = qty;
        this.merek = merek;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public String getJenisBarang() {
        return jenis_barang;
    }

    public String getTypeBarang() {
        return type_barang;
    }

    public String getQTY() { return qty;}

    public String getMerek() { return merek;}

    public String getKeterangan() { return keterangan;}

}
