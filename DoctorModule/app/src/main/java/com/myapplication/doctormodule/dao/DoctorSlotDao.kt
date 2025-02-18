package com.myapplication.doctormodule.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.myapplication.doctormodule.model.DoctorSlot
@Dao
interface DoctorSlotDao {
    @Query("SELECT * FROM doctor_slots WHERE id = :doctorId")
    fun getDoctorById(doctorId: Int): DoctorSlot?
    @Query("SELECT * FROM doctor_slots")
    fun getAllSlots(): List<DoctorSlot>
    @Update
    fun updateSlot(doctor: DoctorSlot)
    @Insert
    fun insertSlot(doctor: DoctorSlot)
    @Delete
    fun deleteSlot(doctor: DoctorSlot)
    @Query("DELETE FROM doctor_slots WHERE id = :slotId")
    suspend fun deleteSlotById(slotId: Int)
}

