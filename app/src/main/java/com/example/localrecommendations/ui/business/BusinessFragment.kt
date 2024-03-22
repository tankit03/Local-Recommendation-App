package com.example.localrecommendations.ui.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.localrecommendations.MainActivity
import com.example.localrecommendations.R
import com.example.localrecommendations.databinding.FragmentBusinessBinding
import com.squareup.picasso.Picasso

class BusinessFragment : Fragment(R.layout.fragment_business) {
    private val args: BusinessFragmentArgs by navArgs()
    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val business = args.business

        // Bind the business details to the views
        binding.textGallery.text = business.name
        binding.textAddressValue.text = business.location.addr.joinToString(", ")
        binding.textPhoneValue.text = business.phone
        binding.textRatingValue.text = business.rating.toString()
        binding.textWebsiteValue.text = business.url

        // Display categories
        val categories = business.categories.joinToString { it.title }
        binding.textCategoriesValue.text = categories

        // Configure the FAB in MainActivity to share these details
        val name = business.name
        val categoriesText = categories
        val address = business.location.addr.joinToString(", ")
        val website = business.url
        (activity as? MainActivity)?.setupFabForBusinessShare(name, categoriesText, address, website)
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
