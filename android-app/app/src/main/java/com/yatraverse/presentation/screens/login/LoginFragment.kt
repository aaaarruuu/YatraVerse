package com.yatraverse.presentation.screens.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yatraverse.R
import com.yatraverse.data.api.RetrofitClient
import com.yatraverse.data.models.LoginRequest
import com.yatraverse.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {

            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Enter email and password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            performLogin(email, password)
        }
    }

    private fun performLogin(email: String, password: String) {

        viewLifecycleOwner.lifecycleScope.launch {
            try {

                val response = RetrofitClient.apiService.login(
                    LoginRequest(
                        email = email,
                        password = password
                    )
                )

                if (response.isSuccessful && response.body() != null) {

                    val loginResponse = response.body()!!

                    com.yatraverse.data.local.SessionManager(
                        requireContext()
                    ).saveSession(
                        token = loginResponse.token,
                        email = loginResponse.email,
                        fullName = loginResponse.fullName
                    )

                    Toast.makeText(
                        requireContext(),
                        "Welcome ${loginResponse.fullName}",
                        Toast.LENGTH_SHORT
                    ).show()

                    findNavController().navigate(
                        R.id.action_login_to_home
                    )

                } else {

                    Toast.makeText(
                        requireContext(),
                        "Login failed: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Network error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}