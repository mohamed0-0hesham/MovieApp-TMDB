package com.hesham0_0.movietrainingapplication.roomDataBaase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hesham0_0.movietrainingapplication.models.MovieItem


@Database( entities = [MovieItem::class],
            version = 1 )
@TypeConverters(Converter::class)
abstract class ProductRoomDB:RoomDatabase() {
    abstract fun getProductsDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductRoomDB? = null
        fun getDatabase(context: Context?): ProductRoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context!!.applicationContext,
                    ProductRoomDB::class.java,
                    "Movie_db.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}