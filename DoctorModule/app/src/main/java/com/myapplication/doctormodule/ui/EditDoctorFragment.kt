package com.myapplication.doctormodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.myapplication.doctormodule.R
import com.myapplication.doctormodule.database.DoctorDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditDoctorFragment : Fragment() {
    private lateinit var edtDoctorName: EditText
    private lateinit var edtSpeciality: EditText
    private lateinit var spinnerSlot: Spinner
    private lateinit var spinnerTimeAvailable: Spinner
    private lateinit var btnUpdateDoctor: Button
    private lateinit var btnCancelEdit: Button

    private var doctorId: Int = -1  // Store Doctor ID
    private lateinit var slotOptions: Array<String>
    private lateinit var timeOptions: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_doctor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtDoctorName = view.findViewById(R.id.etDoctorName)
        edtSpeciality = view.findViewById(R.id.etSpeciality)
        spinnerSlot = view.findViewById(R.id.spinnerSlot)
        spinnerTimeAvailable = view.findViewById(R.id.spinnerTimeAvailable)
        btnUpdateDoctor = view.findViewById(R.id.btnUpdateDoctor)
        btnCancelEdit = view.findViewById(R.id.btnCancelEdit)

        doctorId = arguments?.getInt("doctor_id", -1) ?: -1

        // Load predefined spinner options
        slotOptions = arrayOf("Morning", "Afternoon", "Evening")
        timeOptions = arrayOf("9 AM - 12 PM", "1 PM - 4 PM", "5 PM - 8 PM")

        // Set up Spinners
        setupSpinner(spinnerSlot, slotOptions)
        setupSpinner(spinnerTimeAvailable, timeOptions)

        // Load doctor details
        loadDoctorDetails()

        // Update button click
        btnUpdateDoctor.setOnClickListener {
            updateDoctorDetails()
        }

        // Cancel button click
        btnCancelEdit.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()  // Navigate back to the previous fragment
        }
    }

    private fun setupSpinner(spinner: Spinner, options: Array<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, options)
        spinner.adapter = adapter
    }

    private fun loadDoctorDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = DoctorDatabase.getDatabase(requireContext())
            val doctor = db.doctorSlotDao().getDoctorById(doctorId)

            requireActivity().runOnUiThread {
                doctor?.let {
                    edtDoctorName.setText(it.doctorName)
                    edtSpeciality.setText(it.speciality)

                    // Select correct spinner value
                    spinnerSlot.setSelection(slotOptions.indexOf(it.slot))
                    spinnerTimeAvailable.setSelection(timeOptions.indexOf(it.timeAvailable))
                }
            }
        }
    }

    private fun updateDoctorDetails() {
        val newName = edtDoctorName.text.toString().trim()
        val newSpeciality = edtSpeciality.text.toString().trim()
        val newSlot = spinnerSlot.selectedItem.toString()
        val newTimeAvailable = spinnerTimeAvailable.selectedItem.toString()

        if (newName.isNotEmpty() && newSpeciality.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                val db = DoctorDatabase.getDatabase(requireContext())
                val doctor = db.doctorSlotDao().getDoctorById(doctorId)

                if (doctor != null) {
                    doctor.doctorName = newName
                    doctor.speciality = newSpeciality
                    doctor.slot = newSlot
                    doctor.timeAvailable = newTimeAvailable

                    db.doctorSlotDao().updateSlot(doctor)

                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Doctor updated successfully!", Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.popBackStack() // Navigate back after update
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "All fields must be filled!", Toast.LENGTH_SHORT).show()
        }
    }
}
