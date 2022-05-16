package com.utn.cookapp.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utn.cookapp.R
import com.utn.cookapp.entities.Recipe

class RecipeAdapter(
    var recipeList : MutableList<Recipe>,
    val context : Context,
    var onLongClick : (Int) -> Boolean,
    var onClick : (Int) -> Unit,
    val selectedPosition : Int
    ) : RecyclerView.Adapter<RecipeAdapter.RecipeHolder>()

{
    class RecipeHolder(v : View) : RecyclerView.ViewHolder(v){
        private var view : View = v

        fun setName(name : String) {
            var recipeName : TextView = view.findViewById(R.id.recipeName)
            recipeName.text = name
        }

        fun setAuthor(name : String) {
            var recipeAuthor : TextView = view.findViewById(R.id.recipeAuthor)
            recipeAuthor.text = name
        }

        fun getCardView() : CardView {
            return view.findViewById(R.id.recipeCard)
        }
        fun getImageView() : ImageView {
            return view.findViewById(R.id.imageCard)
        }
    }

    override fun onBindViewHolder(holder: RecipeAdapter.RecipeHolder, position: Int) {
        holder.setName(recipeList[position].name)
        holder.setAuthor(recipeList[position].author)
        Glide
            .with(context)
            .load(recipeList[position].image)
            .centerInside()
            .into(holder.getImageView());

        //Logica de selección de items
        if (selectedPosition == position && selectedPosition != -2)
        {
            holder.getCardView().setCardBackgroundColor(Color.parseColor("#C0C0C0"))
        }

        holder.getCardView().setOnLongClickListener() {
            onLongClick(position)
        }
        holder.getCardView().setOnClickListener() {
            onClick(recipeList[position].id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter.RecipeHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.recipe_item,parent,false)
        return (RecipeHolder(view))
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

}