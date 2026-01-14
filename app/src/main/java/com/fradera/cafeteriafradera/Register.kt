package com.fradera.cafeteriafradera

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class RegisterFragment : Fragment(R.layout.fragment_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val etUser = view.findViewById<EditText>(R.id.etUser)
        val etPass = view.findViewById<EditText>(R.id.etPass)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardar)

        val prefs = requireActivity().getSharedPreferences("dades", 0)

        btnGuardar.setOnClickListener {

            val user = etUser.text.toString()
            val pass = etPass.text.toString()

            if (user != "" && pass != "") {

                val editor = prefs.edit()
                editor.putString("user", user)
                editor.putString("pass", pass)
                editor.commit()

                Toast.makeText(activity, "Usuari guardat", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()

            } else {
                Toast.makeText(activity, "Falten dades", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
