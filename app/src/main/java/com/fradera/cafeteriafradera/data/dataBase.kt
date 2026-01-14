package com.fradera.cafeteriafradera.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.fradera.cafeteriafradera.R
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    entities = [
        Begudes::class,
        Menjars::class,
        Postres::class
    ],
    version = 2,
    exportSchema = false
)

abstract class dataBase : RoomDatabase(){
    abstract fun begudaDao(): BegudesDAO
    abstract fun menjarDao(): MenjarsDAO
    abstract fun postraDao(): PostresDAO

    companion object {
        @Volatile
        private var INSTANCE: dataBase? = null

        fun getDatabase(context: Context): dataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    dataBase::class.java,
                    "cafeteria_db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
private class DatabaseCallback(
    private val context: Context
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            val database = dataBase.getDatabase(context)

            database.begudaDao().insertAll(
                listOf(
                    Begudes(1,"Americà", 1.20, "calent", R.drawable.america),
                    Begudes(2,"Cafè amb llet", 1.30, "calent", R.drawable.cafe_amb_llet),
                    Begudes(3,"Capucchino", 1.40,"calent",R.drawable.capucchino)
                )
            )

            database.menjarDao().insertAll(
                listOf(
                    Menjars("Entrepà de pernil dolç amb formatge", 3.50, "fred",R.drawable.pernil_dols_formatge),
                    Menjars("Croissant", 1.80, "fred",R.drawable.croasaint)
                )
            )

            database.postraDao().insertAll(
                listOf(
                    Postres("Flam", 2.00, "fred",R.drawable.flan),
                    Postres("Pastís de xocolata", 2.50, "fred",R.drawable.pastis_xoco)
                )
            )

        }
    }
}