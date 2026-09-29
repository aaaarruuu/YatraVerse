package com.yatraverse.presentation.screens.community

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yatraverse.R
import com.yatraverse.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment(R.layout.fragment_community) {

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCommunityBinding.bind(view)

        binding.fabNewPost.setOnClickListener {
            Toast.makeText(requireContext(), "New Post — coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}