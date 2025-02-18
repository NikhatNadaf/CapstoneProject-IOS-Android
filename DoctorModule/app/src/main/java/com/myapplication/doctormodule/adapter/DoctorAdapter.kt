package com.myapplication.doctormodule.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.myapplication.doctormodule.R
import com.myapplication.doctormodule.database.DoctorDatabase
import com.myapplication.doctormodule.model.DoctorSlot
import com.myapplication.doctormodule.ui.EditDoctorFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DoctorAdapter(private val context: Context) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {
    private var doctorList: List<DoctorSlot> = listOf()

    fun setDoctors(doctors: List<DoctorSlot>) {
        this.doctorList = doctors
        notifyDataSetChanged()
    }

    inner class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doctorName: TextView = itemView.findViewById(R.id.tvDoctorName)
        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctorList[position]
        holder.doctorName.text = doctor.doctorName  // Assuming DoctorSlot has a name field

        // Handle Edit Click
        holder.btnEdit.setOnClickListener {
            if (context is FragmentActivity) {
                val fragment = EditDoctorFragment()
                val bundle = Bundle()
                bundle.putInt("doctor_id", doctor.id)  // Pass doctor ID
                fragment.arguments = bundle

                // Replace current fragment with EditDoctorFragment
                context.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null) // To allow back navigation
                    .commit()
            }
        }

        // Handle Delete Click
        holder.btnDelete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                // Deleting the doctor slot from the database
                val db = DoctorDatabase.getDatabase(context)
                db.doctorSlotDao().deleteSlot(doctor)

                // Refresh the list after deletion on the Main thread
                launch(Dispatchers.Main) {
                    // Refreshing the list after deletion
                    setDoctors(db.doctorSlotDao().getAllSlots())
                }
            }
        }
    }

    override fun getItemCount(): Int = doctorList.size
}
