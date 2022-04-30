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

        fun getCardView() : CardView {
            return view.findViewById(R.id.recipeCard)
        }
        //fun getImageView() : ImageView {
        //    return view.findViewById(R.id.imageView)
        //}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter.RecipeHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.recipe_item,parent,false)
        return (RecipeHolder(view))
    }

    override fun onBindViewHolder(holder: RecipeAdapter.RecipeHolder, position: Int) {
        holder.setName(recipeList[position].name)

        /*Glide
            .with(context)
            .load(recipeList[position].uid)
            .centerInside()
            .into(holder.getImageView());*/
        if (selectedPosition == position && selectedPosition != -2)
        {
            holder.getCardView().setCardBackgroundColor(Color.parseColor("#C0C0C0"))
        }

        holder.getCardView().setOnLongClickListener() {
            onLongClick(position)
        }
        holder.getCardView().setOnClickListener() {
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

}