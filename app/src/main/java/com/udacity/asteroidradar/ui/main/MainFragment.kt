package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.ViewModelFactory
import com.udacity.asteroidradar.data.local.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment(), AsteroidAdapter.Interaction {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val factory = ViewModelFactory(AsteroidDatabase.getInstance(requireContext()))


        val viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        val asteroidAdapter = AsteroidAdapter(this)

        lifecycleScope.launchWhenCreated {
            binding.statusLoadingWheel.visibility = View.VISIBLE
            viewModel.refresh()
        }

        binding.asteroidRecycler.apply {
            adapter = asteroidAdapter
        }

        binding.viewModel = viewModel

        viewModel.asteroidsLiveData.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    binding.statusLoadingWheel.visibility = View.INVISIBLE
                    asteroidAdapter.submitList(it)
                } else {
                    Toast.makeText(requireContext(), "Couldn't fetch data", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onItemSelected(position: Int, item: Asteroid) {
        findNavController().navigate(
            MainFragmentDirections.actionShowDetail(item)
        )
    }
}
