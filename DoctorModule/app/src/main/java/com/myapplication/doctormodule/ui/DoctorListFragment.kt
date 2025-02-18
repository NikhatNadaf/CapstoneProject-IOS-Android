package com.myapplication.doctormodule.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapplication.doctormodule.R
import com.myapplication.doctormodule.adapter.DoctorAdapter
import com.myapplication.doctormodule.database.DoctorDatabase

class DoctorListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var doctorAdapter: DoctorAdapter
    private lateinit var database: DoctorDatabase
    private lateinit var backBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bind the views
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        doctorAdapter = DoctorAdapter(requireContext())
        recyclerView.adapter = doctorAdapter

        database = DoctorDatabase.getDatabase(requireContext())

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DashboardFragment()) // Replace with your desired fragment
                .addToBackStack(null)
                .commit()
        }

        loadDoctorSlots()
    }

    private fun loadDoctorSlots() {
        // Loading doctor slots in a background thread
        Thread {
            val doctorSlots = database.doctorSlotDao().getAllSlots()
            activity?.runOnUiThread {
                doctorAdapter.setDoctors(doctorSlots)
            }
        }.start()
    }
}
