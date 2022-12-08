package com.capstone.travguide.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.capstone.travguide.R
import com.capstone.travguide.TravisActivity
import com.capstone.travguide.databinding.FragmentTravisSplashScreenBinding
import kotlinx.coroutines.delay


class TravisSplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentTravisSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTravisSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            delay(2000)

            (activity as TravisActivity).checkAndGetGoogleUser()?.let {
                findNavController().navigate(R.id.action_travisSplashScreenFragment_to_travisHomePageFragment)
            } ?: run {
                findNavController().navigate(R.id.action_travisSplashScreenFragment_to_travisLoginFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as TravisActivity).toolBarVisible(false)
    }
}