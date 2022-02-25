package com.aldidwikip.thecocktail.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.aldidwikip.thecocktail.R
import com.aldidwikip.thecocktail.data.model.Cocktail
import com.aldidwikip.thecocktail.databinding.FragmentHomeBinding
import com.aldidwikip.thecocktail.ui.BaseFragment
import com.aldidwikip.thecocktail.ui.adapter.CocktailListAdapter
import com.aldidwikip.thecocktail.util.DataState
import com.aldidwikip.thecocktail.util.gone
import com.aldidwikip.thecocktail.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), CocktailListAdapter.OnItemClickListener {
    private val homeViewModel: HomeViewModel by viewModels()
    private var keywords: String? = null
    private lateinit var rvAdapter: CocktailListAdapter
    private lateinit var ingredientQuery: String
    private lateinit var ingredientsList: List<String>
    private lateinit var appCompatActivity: AppCompatActivity
    private var choiceIndex by Delegates.notNull<Int>()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        ingredientQuery = sharedPreferences.getString("ingredients", "Rum").toString()

        keywords?.let {
            homeViewModel.setSearchQuery(it)
        } ?: run {
            homeViewModel.setIngredientQuery(ingredientQuery)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setup() {
        appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(binding.toolbar)

        subscribeData()
        searchCocktails()
        initRecycler()
    }

    override fun onPause() {
        super.onPause()
        with(sharedPreferences.edit()) {
            putString("ingredients", ingredientQuery)
            apply()
        }
    }

    private fun initRecycler() {
        rvAdapter = CocktailListAdapter()
        rvAdapter.setOnItemClickListener(this)
        binding.rvCocktail.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = rvAdapter
        }
    }

    private fun subscribeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.getFilteredCocktails().collect { dataState ->
                        when (dataState) {
                            is DataState.Success -> {
                                binding.progressBar.gone()
                                binding.searchBox.text?.clear()
                                appCompatActivity.supportActionBar?.subtitle = ingredientQuery
                                rvAdapter.submitList(dataState.data)
                            }
                            is DataState.Error -> {
                                binding.progressBar.gone()
                                Toast.makeText(context, dataState.exception.message, Toast.LENGTH_SHORT).show()
                            }
                            is DataState.Loading -> binding.progressBar.visible()
                        }
                    }
                }

                launch {
                    homeViewModel.getIngredientList().collect { ingredients ->
                        ingredientsList = ingredients.map { it.ingredient }
                        choiceIndex = ingredientsList.indexOf(ingredientQuery)
                    }
                }

                launch {
                    homeViewModel.getSearchCocktailResult().collect { dataState ->
                        keywords?.let {
                            when (dataState) {
                                is DataState.Loading -> binding.progressBar.visible()
                                is DataState.Success -> {
                                    binding.progressBar.gone()
                                    appCompatActivity.supportActionBar?.subtitle = keywords
                                    rvAdapter.submitList(dataState.data)
                                    choiceIndex = -1
                                }
                                is DataState.Error -> {
                                    binding.progressBar.gone()
                                    Toast.makeText(context, "Data not found", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun searchCocktails() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.searchBox.setOnEditorActionListener { v, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    keywords = v.text.toString().trim()
                    keywords?.let {
                        v.clearFocus()
                        imm.hideSoftInputFromWindow(v.windowToken, 0)
                        homeViewModel.setSearchQuery(it)
                    }
                    true
                }
                else -> false
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_filter) {
            MaterialDialog(requireContext()).show {
                title(text = "Ingredients")
                listItemsSingleChoice(items = ingredientsList, initialSelection = choiceIndex) { _, index, text ->
                    ingredientQuery = text.toString()
                    choiceIndex = index
                    homeViewModel.setIngredientQuery(ingredientQuery)
                    keywords = null
                }
                positiveButton(R.string.select)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemClicked(dataCocktailModel: Cocktail) {
        val bundle = bundleOf(resources.getString(R.string.ARG_ID) to dataCocktailModel.cocktailId)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }
}