package com.example.localrecommendations.ui.business

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.localrecommendations.MainActivity
import com.example.localrecommendations.R
import com.example.localrecommendations.databinding.FragmentBusinessBinding
import com.example.localrecommendations.ui.gallery.GalleryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BusinessFragment : Fragment(R.layout.fragment_business) {
    private val args: BusinessFragmentArgs by navArgs()
    private var _binding: FragmentBusinessBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentBusinessBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = args.business.name
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Make the FAB visible
        // Assuming 'args' contains the business details
        val name = args.business.name
        val categories = args.business.categories.joinToString { it.title } // Example, adjust as needed
        val address = args.business.location.addr.joinToString(", ")
        val website = args.business.url

        // Configure the FAB in MainActivity to share these details
        (activity as? MainActivity)?.setupFabForBusinessShare(name, categories, address, website)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide the FAB
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.hide()
        _binding = null
    }
}