package com.yatraverse.presentation.screens.explore

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.yatraverse.R
import com.yatraverse.data.api.RetrofitClient
import kotlinx.coroutines.launch

class DestinationDetailFragment : Fragment(R.layout.fragment_destination_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val content = view.findViewById<ScrollView>(R.id.scrollContent)
        val progress = view.findViewById<ProgressBar>(R.id.detailProgress)
        val error = view.findViewById<TextView>(R.id.tvDetailError)
        val image = view.findViewById<ImageView>(R.id.ivDetailImage)
        val name = view.findViewById<TextView>(R.id.tvDetailName)
        val location = view.findViewById<TextView>(R.id.tvDetailLocation)
        val description = view.findViewById<TextView>(R.id.tvDetailDescription)
        val askAi = view.findViewById<Button>(R.id.btnAskAi)

        view.findViewById<Button>(R.id.btnDetailBack).setOnClickListener {
            findNavController().popBackStack()
        }

        val id = arguments?.getLong("destinationId", -1L) ?: -1L
        if (id == -1L) {
            progress.visibility = View.GONE
            error.text = "Destination not found."
            error.visibility = View.VISIBLE
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getDestination(id)
                progress.visibility = View.GONE
                val d = response.body()
                if (response.isSuccessful && d != null) {
                    name.text = d.name
                    location.text = d.location.orEmpty()
                    description.text = d.description.orEmpty()
                    Glide.with(image.context)
                        .load(d.imageUrl)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .into(image)

                    // The destination name is the chatbot's place filter
                    val place = d.name
                    askAi.setOnClickListener {
                        findNavController().navigate(
                            R.id.action_detail_to_chat,
                            bundleOf("city" to place)
                        )
                    }

                    content.visibility = View.VISIBLE
                } else {
                    error.text = "Failed to load (${response.code()})"
                    error.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                progress.visibility = View.GONE
                error.text = "Network error: ${e.message}"
                error.visibility = View.VISIBLE
            }
        }
    }
}