package org.d3if3124.assessment1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import org.d3if3124.assessment1.model.Order

@Database(entities = [Order::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class OrderDb : RoomDatabase() {

    abstract val dao: OrderDao

    companion object {

        @Volatile
        private var INSTANCE: OrderDb? = null

        fun getInstance(context: Context): OrderDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OrderDb::class.java,
                        "order.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}