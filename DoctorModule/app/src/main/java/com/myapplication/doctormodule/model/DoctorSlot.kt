package com.myapplication.doctormodule.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctor_slots")
data class DoctorSlot(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var doctorName: String,
    var speciality: String,
    var slot: String,
    var timeAvailable: String
)

