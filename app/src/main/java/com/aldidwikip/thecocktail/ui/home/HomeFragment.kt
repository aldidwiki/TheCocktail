package com.aldidwikip.thecocktail.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aldidwikip.thecocktail.R
import com.aldidwikip.thecocktail.data.model.Cocktail
import com.aldidwikip.thecocktail.ui.adapter.CocktailListAdapter
import com.aldidwikip.thecocktail.util.DataState
import com.aldidwikip.thecocktail.util.gone
import com.aldidwikip.thecocktail.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(), CocktailListAdapter.OnItemClickListener {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)

        navController = Navigation.findNavController(view)

        val rvAdapter = CocktailListAdapter()
        rvAdapter.setOnItemClickListener(this)
        rvCocktail.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = rvAdapter
        }

        homeViewModel.cocktails.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    progress_bar.gone()
                    rvAdapter.submitList(dataState.data)
                }
                is DataState.Error -> {
                    progress_bar.gone()
                    Toast.makeText(view.context, dataState.exception.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    progress_bar.visible()
                }
            }
        }
    }

    override fun onItemClicked(dataCocktailModel: Cocktail) {
        val bundle = bundleOf(resources.getString(R.string.ARG_ID) to dataCocktailModel.cocktailId)
        navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

}