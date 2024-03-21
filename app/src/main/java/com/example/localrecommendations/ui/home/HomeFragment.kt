package com.example.localrecommendations.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localrecommendations.R
import com.example.localrecommendations.data.Businesses
import com.example.localrecommendations.databinding.FragmentHomeBinding
import com.example.localrecommendations.ui.YelpAdapter
import com.google.android.material.progressindicator.CircularProgressIndicator

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels ()
    private val yelpAdapter = YelpAdapter(::onYelpClick)

    private lateinit var cityTV: TextView
    private lateinit var yelpListRV: RecyclerView
    private lateinit var loadingErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cityTV = view.findViewById(R.id.tv_city)
        loadingErrorTV = view.findViewById(R.id.tv_loading_error)
        loadingIndicator = view.findViewById(R.id.loading_indicator)

        yelpListRV = view.findViewById(R.id.rv_yelp_list)
        yelpListRV.layoutManager = LinearLayoutManager(requireContext())
        yelpListRV.visibility = View.VISIBLE
        yelpListRV.setHasFixedSize(true)
        yelpListRV.adapter = yelpAdapter

        cityTV.text = "Restaurants"

        viewModel.yelp.observe(viewLifecycleOwner) { yelp ->
            if(yelp != null) {
                yelpAdapter.updateList(yelp)
                Log.d("Home", "Yelp Result: ${yelp.result?.get(1)?.imageURL}")
                Log.d("Home", "Businesses: ${yelp.result}")
                yelpListRV.visibility = View.VISIBLE
                yelpListRV.scrollToPosition(0)
            }
        }
        Log.d("Home","viewModel results: ${viewModel.yelp.value}")
        Log.d("Home","error results: ${viewModel.error.value.toString()}")
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadYelp("Corvallis", "restaurant")
        Log.d("Home OnResume","viewModel results: ${viewModel.yelp.value?.result?.get(1)?.imageURL}")
        Log.d("Home OnResume","error results: ${viewModel.error.value.toString()}")

    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val HomeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onYelpClick (business: Businesses) {
        Log.v("OnSavedClick", "Weather entity name: ${business.name}")
        findNavController().navigate(HomeFragmentDirections.navigateToBusiness())
    }
}