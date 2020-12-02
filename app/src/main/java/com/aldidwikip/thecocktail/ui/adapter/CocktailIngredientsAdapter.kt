package com.aldidwikip.thecocktail.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aldidwikip.thecocktail.R
import com.google.android.material.textview.MaterialTextView

class CocktailIngredientsAdapter : RecyclerView.Adapter<CocktailIngredientsAdapter.IngredientViewHolder>() {
    private lateinit var ingredients: List<String>
    private lateinit var measures: List<String?>

    fun setData(ingredients: List<String>, measures: List<String?>) {
        this.ingredients = ingredients
        this.measures = measures
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_ingredients, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        with(holder) {
            tvIngredients.text = ingredients[position]
            tvMeasures.text = measures[position]
        }
    }

    override fun getItemCount(): Int = ingredients.size

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIngredients: MaterialTextView = itemView.findViewById(R.id.tv_ingredients)
        val tvMeasures: MaterialTextView = itemView.findViewById(R.id.tv_measures)
    }
}