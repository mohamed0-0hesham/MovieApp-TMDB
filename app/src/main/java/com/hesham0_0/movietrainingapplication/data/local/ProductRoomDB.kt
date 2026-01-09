package com.hesham0_0.movietrainingapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hesham0_0.movietrainingapplication.domain.models.MovieItem


@Database(
    entities = [MovieItem::class],
    version = 1, // when change schema you have to change the version
)
@TypeConverters(Converter::class)
abstract class ProductRoomDB:RoomDatabase() {
    abstract fun getProductsDao(): ProductDao

    companion object {
        // writes to this field are immediately visible to other threads
        @Volatile
        private var INSTANCE: ProductRoomDB? = null
        fun getDatabase(context: Context?): ProductRoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            // synchronized makes sure that only one thread can have an instance of this class to avoid create more than one instance of database
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