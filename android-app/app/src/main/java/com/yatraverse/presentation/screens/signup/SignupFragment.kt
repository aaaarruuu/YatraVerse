package com.yatraverse.presentation.screens.signup

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yatraverse.R
import com.yatraverse.data.api.RetrofitClient
import com.yatraverse.data.local.SessionManager
import com.yatraverse.data.models.SignupRequest
import com.yatraverse.databinding.FragmentSignupBinding
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignupBinding.bind(view)

        binding.btnSignup.setOnClickListener {
            val fullName = binding.etFullName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            binding.tilFullName.error = null
            binding.tilEmail.error = null
            binding.tilPassword.error = null

            when {
                fullName.isEmpty() -> binding.tilFullName.error = "Enter your name"
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    binding.tilEmail.error = "Enter a valid email"
                password.length < 6 ->
                    binding.tilPassword.error = "At least 6 characters"
                else -> performSignup(fullName, email, password)
            }
        }

        binding.tvGoLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.btnSignup.isEnabled = !loading
        binding.btnSignup.text = if (loading) "Creating account..." else "Sign up"
    }

    private fun performSignup(fullName: String, email: String, password: String) {
        setLoading(true)
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.signup(
                    SignupRequest(fullName = fullName, email = email, password = password)
                )
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    SessionManager(requireContext()).saveSession(
                        token = body.token,
                        email = body.email,
                        fullName = body.fullName
                    )
                    Toast.makeText(requireContext(), "Welcome ${body.fullName}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signup_to_home)
                } else {
                    setLoading(false)
                    val serverMessage = response.errorBody()?.string().orEmpty()
                    val message = if (serverMessage.isNotBlank() && !serverMessage.trimStart().startsWith("{"))
                        serverMessage else "Signup failed (${response.code()}). Check your details."
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                setLoading(false)
                Toast.makeText(requireContext(), "Network error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}