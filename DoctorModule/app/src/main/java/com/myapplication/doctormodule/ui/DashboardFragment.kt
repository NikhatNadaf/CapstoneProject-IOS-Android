package com.myapplication.doctormodule.ui
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.myapplication.doctormodule.R
import com.myapplication.doctormodule.database.DoctorDatabase
import com.myapplication.doctormodule.model.DoctorSlot

class DashboardFragment : Fragment() {

    private lateinit var etDoctorName: EditText
    private lateinit var etSpeciality: EditText
    private lateinit var spinnerSlot: Spinner
    private lateinit var spinnerTimeAvailable: Spinner
    private lateinit var btnCreateSlot: Button
    private lateinit var btnBack: Button
    private lateinit var btnLogout: Button

    private lateinit var database: DoctorDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etDoctorName = view.findViewById(R.id.etDoctorName)
        etSpeciality = view.findViewById(R.id.etSpeciality)
        spinnerSlot = view.findViewById(R.id.spinnerSlot)
        spinnerTimeAvailable = view.findViewById(R.id.spinnerTimeAvailable)
        btnCreateSlot = view.findViewById(R.id.btnCreateSlot)
        btnBack = view.findViewById(R.id.btnBack)
        btnLogout = view.findViewById(R.id.btnLogout)

        database = DoctorDatabase.getDatabase(requireContext())
        setupSlotSpinner()

        btnCreateSlot.setOnClickListener { saveSlotToDatabase() }

        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DashboardFragment())
                .addToBackStack(null)
                .commit()
        }
        btnLogout.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupSlotSpinner() {
        val slots = arrayOf("Morning", "Afternoon", "Evening", "Night")
        val slotAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, slots)
        spinnerSlot.adapter = slotAdapter

        spinnerSlot.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateTimeSpinner(slots[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateTimeSpinner(slot: String) {
        val times = when (slot) {
            "Morning" -> arrayOf("08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM")
            "Afternoon" -> arrayOf("12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM")
            "Evening" -> arrayOf("04:00 PM", "05:00 PM", "06:00 PM", "07:00 PM")
            "Night" -> arrayOf("08:00 PM", "09:00 PM", "10:00 PM", "11:00 PM")
            else -> emptyArray()
        }
        val timeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, times)
        spinnerTimeAvailable.adapter = timeAdapter
    }

    private fun saveSlotToDatabase() {
        val doctorName = etDoctorName.text.toString()
        val speciality = etSpeciality.text.toString()
        val slot = spinnerSlot.selectedItem.toString()
        val timeAvailable = spinnerTimeAvailable.selectedItem?.toString() ?: ""

        if (doctorName.isEmpty() || speciality.isEmpty() || timeAvailable.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val slotData = DoctorSlot(
            doctorName = doctorName,
            speciality = speciality,
            slot = slot,
            timeAvailable = timeAvailable
        )

        // Save slot to the database in a background thread
        Thread {
            database.doctorSlotDao().insertSlot(slotData)

            activity?.runOnUiThread {
                Toast.makeText(requireContext(), "Slot Created Successfully!", Toast.LENGTH_LONG).show()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DoctorListFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }.start()
    }

    // Update for delete slot functionality


    private fun deleteSlot(slotId: Int) {
        // Launch a coroutine tied to the view lifecycle
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Call the suspend function to delete the slot in the background
                database.doctorSlotDao().deleteSlotById(slotId)

                // After deleting, update the UI on the main thread
                withContext(Dispatchers.Main) {
                    // Show a Toast message on the main thread
                    Toast.makeText(requireContext(), "Slot Deleted Successfully!", Toast.LENGTH_LONG).show()

                    // Navigate to the DoctorListFragment after the deletion
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DoctorListFragment()) // Or reload the current fragment
                        .addToBackStack(null)
                        .commit()
                }

            } catch (e: Exception) {
                // Handle errors if needed
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error deleting slot", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun refreshSlotList() {
        // Logic to refresh the UI after deleting the slot (e.g., reload the list of slots)
    }
}
