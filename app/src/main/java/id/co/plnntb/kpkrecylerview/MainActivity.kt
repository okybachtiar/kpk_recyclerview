package id.co.plnntb.kpkrecylerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.plnntb.kpkrecylerview.adapter.PelangganAdapter
import id.co.plnntb.kpkrecylerview.model.Pelanggan
import id.co.plnntb.kpkrecylerview.viewholder.database
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select


class MainActivity : AppCompatActivity() {
    //buat global variable untuk menampung data pelanggan
    var listPlg = mutableListOf<Pelanggan>()
    val adapterPlg = PelangganAdapter(listPlg)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTampilan()


        //masukan data pelanggan ke rycler View
        rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterPlg
        }
        cameraBtn.setOnClickListener{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(cameraIntent)
        }
    }

    fun initTampilan() {

        btnsimpan.setOnClickListener {
            database.use {
                insert(
                    "pelanggan",
                    "idpel" to idpel.text.toString(),
                    "nama" to nama.text.toString(),
                    "alamat" to alamat.text.toString(),
                    "tarif" to tarif.text.toString(),
                    "daya" to daya.text.toString()
                )
                listPlg.add(
                    Pelanggan(
                        idpel.text.toString(),
                        nama.text.toString(),
                        alamat.text.toString(),
                        tarif.text.toString(),
                        daya.text.toString()
                    )
                )
                //refresh recyler view, agar datayang baru di inster
                //langsung muncul
                adapterPlg.notifyDataSetChanged()
                //onResume()
            }

        }


    }

    override fun onResume() {
        bacaDatabase()
        super.onResume()
    }
    fun bacaDatabase() {
        database.use {
            select("pelanggan").exec {
                listPlg.clear()
                while (this.moveToNext()) {
                    listPlg.add(
                        Pelanggan(
                            getString(getColumnIndex("idpel")),
                            getString(getColumnIndex("nama")),
                            getString(getColumnIndex("alamat")),
                            getString(getColumnIndex("tarif")),
                            getString(getColumnIndex("daya"))
                            ))

                }

            }
        }
        adapterPlg.notifyDataSetChanged()
    }


}
