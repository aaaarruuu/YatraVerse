package com.yatraverse.presentation.screens.bookings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.yatraverse.R
import com.yatraverse.databinding.FragmentBookingsBinding

class BookingsFragment : Fragment(R.layout.fragment_bookings) {

    private var _binding: FragmentBookingsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBookingsBinding.bind(view)

        binding.tabLayoutBookings.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        binding.tvBookingsMessage.text = "No upcoming bookings yet"
                        binding.tvBookingsSubtitle.text =
                            "Guides, homestays, and experiences you book will show up here."
                    }
                    1 -> {
                        binding.tvBookingsMessage.text = "No past bookings"
                        binding.tvBookingsSubtitle.text =
                            "Your completed trips and experiences will appear here."
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}