package com.myapplication.doctormodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.myapplication.doctormodule.R
import com.myapplication.doctormodule.database.AppDatabase
import com.myapplication.doctormodule.model.Doctor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bind the views
        etName = view.findViewById(R.id.etName)
        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        btnRegister = view.findViewById(R.id.btnRegister)

        // Initialize the database instance
        db = AppDatabase.getInstance(requireContext())

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val doctor = Doctor(name = name, email = email, password = password)

                // Use a CoroutineScope to launch a coroutine
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        // Perform the database operation on the IO thread
                        withContext(Dispatchers.IO) {
                            db.doctorDao().registerDoctor(doctor)
                        }

                        // On success, show a toast and navigate back to the LoginFragment
                        Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()

                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, LoginFragment())
                            .addToBackStack(null)
                            .commit()

                    } catch (e: Exception) {
                        // Handle error on the main thread
                        Toast.makeText(requireContext(), "Registration Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
