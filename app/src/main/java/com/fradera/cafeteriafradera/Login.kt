package com.fradera.cafeteriafradera

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etUser = view.findViewById<EditText>(R.id.etUser)
        val etPass = view.findViewById<EditText>(R.id.etPass)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)

        val prefs = requireActivity().getSharedPreferences("dades", 0)

        btnLogin.setOnClickListener {

            val user = etUser.text.toString()
            val pass = etPass.text.toString()

            val userGuardat = prefs.getString("user", "")
            val passGuardada = prefs.getString("pass", "")

            if (user == userGuardat && pass == passGuardada) {

                findNavController()
                    .navigate(R.id.action_loginFragment_to_begudesFragment)

            } else {
                Toast.makeText(
                    requireContext(),
                    "Login incorrecte",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnRegister.setOnClickListener {

            findNavController()
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}
