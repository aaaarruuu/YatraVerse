package com.yatraverse.presentation.screens.profile

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yatraverse.R
import com.yatraverse.data.local.SessionManager
import com.yatraverse.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val session = SessionManager(requireContext())
        val fullName = session.getFullName() ?: "Guest"
        val email = session.getEmail() ?: "Not logged in"

        val circle = GradientDrawable()
        circle.shape = GradientDrawable.OVAL
        circle.setColor(0xFF2E7D32.toInt())
        binding.tvInitials.background = circle
        binding.tvInitials.text = initialsFrom(fullName)
        binding.tvFullName.text = fullName
        binding.tvEmail.text = email

        binding.btnLogout.setOnClickListener {
            session.clearSession()
            Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_profile_to_login)
        }
    }

    private fun initialsFrom(name: String): String {
        val parts = name.trim().split(" ").filter { it.isNotBlank() }
        if (parts.isEmpty()) return "YV"
        return parts.take(2).map { it.first().uppercaseChar() }.joinToString("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}