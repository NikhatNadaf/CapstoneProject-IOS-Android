package com.myapplication.doctormodule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myapplication.doctormodule.dao.DoctorSlotDao
import com.myapplication.doctormodule.model.DoctorSlot

@Database(entities = [DoctorSlot::class], version = 1, exportSchema = false)
abstract class DoctorDatabase : RoomDatabase() {
    abstract fun doctorSlotDao(): DoctorSlotDao

    companion object {
        @Volatile
        private var INSTANCE: DoctorDatabase? = null

        fun getDatabase(context: Context): DoctorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DoctorDatabase::class.java,
                    "doctor_slots_database" // Ensure a unique name
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
