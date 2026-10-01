package com.yatraverse.presentation.screens.explore

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yatraverse.R
import com.yatraverse.data.api.RetrofitClient
import kotlinx.coroutines.launch

class ExploreFragment : Fragment(R.layout.fragment_explore) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyState: TextView
    private lateinit var adapter: DestinationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvDestinations)
        progressBar = view.findViewById(R.id.progressBar)
        emptyState = view.findViewById(R.id.tvEmptyState)

        adapter = DestinationAdapter(emptyList()) { destination ->
            findNavController().navigate(
                R.id.action_explore_to_detail,
                bundleOf("destinationId" to destination.id)
            )
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        fetchDestinations()
    }

    private fun fetchDestinations() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        emptyState.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getDestinations()
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val destinations = response.body().orEmpty()
                    if (destinations.isEmpty()) {
                        emptyState.visibility = View.VISIBLE
                    } else {
                        recyclerView.visibility = View.VISIBLE
                        adapter.updateData(destinations)
                    }
                } else {
                    emptyState.text = "Failed to load destinations (${response.code()})"
                    emptyState.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                emptyState.text = "Network error: ${e.message}"
                emptyState.visibility = View.VISIBLE
            }
        }
    }
}
