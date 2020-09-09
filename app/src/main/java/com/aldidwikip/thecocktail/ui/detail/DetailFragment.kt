package com.aldidwikip.thecocktail.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aldidwikip.thecocktail.R
import com.aldidwikip.thecocktail.databinding.FragmentDetailBinding
import com.aldidwikip.thecocktail.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentDetailBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this
        binding.data = detailViewModel

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar_detail)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navController = Navigation.findNavController(view)

        val cocktailId = arguments?.getString(resources.getString(R.string.ARG_ID))
        detailViewModel.cocktail(cocktailId!!).observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    detailViewModel.getCocktail(dataState.data[0])
                }

                is DataState.Error ->
                    Toast.makeText(view.context, dataState.exception.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}