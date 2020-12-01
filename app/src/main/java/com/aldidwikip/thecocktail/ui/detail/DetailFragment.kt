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
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldidwikip.thecocktail.R
import com.aldidwikip.thecocktail.data.model.CocktailDetail
import com.aldidwikip.thecocktail.databinding.FragmentDetailBinding
import com.aldidwikip.thecocktail.ui.adapter.CocktailIngredientsAdapter
import com.aldidwikip.thecocktail.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
        subscribeData(cocktailId!!)
    }

    private fun subscribeData(cocktailId: String) {
        detailViewModel.cocktail(cocktailId).observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    try {
                        tv_loading.gone()
                        detailViewModel.setCocktail(dataState.data[0])
                        showIngredientRecycler(dataState.data[0])
                    } catch (e: Exception) {
                        e.printStackTrace()
                        tv_loading.visible()
                        Toast.makeText(context, "Data not available", Toast.LENGTH_SHORT).show()
                    }
                }
                is DataState.Error -> {
                    tv_loading.visible()
                    Toast.makeText(context, dataState.exception.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> tv_loading.visible()
            }
        }
    }

    private fun showIngredientRecycler(data: CocktailDetail) {
        val rvIngredientsAdapter = CocktailIngredientsAdapter(
                mapIngredientsToList(data),
                mapMeasuresToList(data)
        )
        rv_ingredients.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvIngredientsAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}