package com.yatraverse.presentation.screens.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yatraverse.R
import com.yatraverse.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.cardTripPlanner.setOnClickListener {
            comingSoon("AI Trip Planner")
        }
        binding.cardItinerary.setOnClickListener {
            comingSoon("Smart Itinerary")
        }
        binding.cardHeritageScanner.setOnClickListener {
            comingSoon("Heritage Scanner")
        }
        binding.cardEcoScore.setOnClickListener {
            comingSoon("Eco Score")
        }
        binding.cardCrowdPrediction.setOnClickListener {
            comingSoon("Crowd Prediction")
        }
        binding.cardEmergency.setOnClickListener {
            comingSoon("Emergency SOS")
        }
    }

    private fun comingSoon(feature: String) {
        Toast.makeText(requireContext(), "$feature — coming soon", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}