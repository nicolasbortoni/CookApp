package com.utn.cookapp.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.utn.cookapp.R
import com.utn.cookapp.database.recipeDao
import com.utn.cookapp.database.recipeDatabase
import com.utn.cookapp.entities.Recipe
import com.utn.cookapp.viewmodels.DetailInfoViewModel

class DetailInfoFragment : Fragment() {

    private lateinit var viewModel: DetailInfoViewModel
    private lateinit var v : View

    private lateinit var recipeImage : ImageView
    private lateinit var recipeTitle : TextView
    private lateinit var recipeAuthor : TextView

    private var db: recipeDatabase? = null
    private var recipeDao: recipeDao? = null

    private lateinit var recipeSelected : Recipe

    companion object {
        fun newInstance() = DetailInfoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.detail_info_fragment, container, false)
        recipeImage = v.findViewById(R.id.recipeImage2)
        recipeTitle = v.findViewById(R.id.recipeTitle)
        recipeAuthor = v.findViewById(R.id.recipeAuthor2)
        return v
    }

    override fun onStart() {
        super.onStart()

        val sharedPref : SharedPreferences = requireContext().getSharedPreferences("myPref", Context.MODE_PRIVATE)
        db = recipeDatabase.getAppDataBase(v.context)
        recipeDao = db?.recipeDao()

        recipeSelected = recipeDao?.loadPersonById(sharedPref.getInt("recipeSelected",-1)) as Recipe

        Glide
            .with(v.context)
            .load(recipeSelected.image)
            .centerInside()
            .into(recipeImage);

        recipeTitle.text = recipeSelected.name
        recipeAuthor.text = "By " + recipeSelected.author

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}