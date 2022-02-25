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
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldidwikip.thecocktail.R
import com.aldidwikip.thecocktail.data.model.CocktailDetail
import com.aldidwikip.thecocktail.databinding.FragmentDetailBinding
import com.aldidwikip.thecocktail.ui.adapter.CocktailIngredientsAdapter
import com.aldidwikip.thecocktail.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this
        binding.data = detailViewModel

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(binding.toolbarDetail)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        arguments?.getString(resources.getString(R.string.ARG_ID))?.let {
            detailViewModel.setCocktailId(it)
        }
        subscribeData()
    }

    private fun subscribeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            detailViewModel.cocktail().flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .collect { dataState ->
                        when (dataState) {
                            is DataState.Success -> {
                                try {
                                    binding.tvLoading.gone()
                                    detailViewModel.setCocktail(dataState.data[0])
                                    showIngredientRecycler(dataState.data[0])
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    binding.tvLoading.visible()
                                    Toast.makeText(context, "Data not available", Toast.LENGTH_SHORT).show()
                                }
                            }
                            is DataState.Error -> {
                                binding.tvLoading.visible()
                                Toast.makeText(context, dataState.exception.message, Toast.LENGTH_SHORT).show()
                            }
                            is DataState.Loading -> binding.tvLoading.visible()
                        }
                    }
        }
    }

    private fun showIngredientRecycler(data: CocktailDetail) {
        val rvIngredientsAdapter = CocktailIngredientsAdapter()
        rvIngredientsAdapter.setData(mapIngredientsToList(data), mapMeasuresToList(data))
        binding.rvIngredients.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvIngredientsAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }
}