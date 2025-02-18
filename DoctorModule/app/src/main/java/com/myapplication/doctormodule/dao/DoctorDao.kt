package com.myapplication.doctormodule.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myapplication.doctormodule.model.Doctor

@Dao
interface DoctorDao {
    @Insert
    suspend fun registerDoctor(doctor: Doctor)

    @Query("SELECT * FROM doctor_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): Doctor?
}