package id.co.plnntb.kpkrecylerview.viewholder

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import org.w3c.dom.Text
import java.util.concurrent.ForkJoinPool

class Database(ctx: Context):
    ManagedSQLiteOpenHelper(ctx, "kpk",null,1) {

    companion object{
        private var instance:Database? = null
        // syncronize object database untuk memastikan semua activity
        //menggunakan object database yang sama
        @Synchronized
        fun getInstance(ctx: Context): Database{
            if(instance==null){
                instance = Database(ctx.applicationContext)
            }
            return  instance!!
        }

    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable("pelanggan",true,

            "idpel" to TEXT + PRIMARY_KEY,
            "nama" to TEXT,
            "alamat" to TEXT,
            "tarif" to TEXT,
            "daya" to INTEGER
            )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


}
val Context.database: Database
get() = Database.getInstance(applicationContext)
