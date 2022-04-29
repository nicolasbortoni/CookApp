package com.utn.cookapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utn.cookapp.R
import com.utn.cookapp.adapters.RecipeAdapter
import com.utn.cookapp.database.recipeDao
import com.utn.cookapp.database.recipeDatabase
import com.utn.cookapp.entities.Recipe
import com.utn.cookapp.viewmodels.RecyclerViewViewModel

class RecyclerViewFragment : Fragment() {

    private lateinit var viewModel: RecyclerViewViewModel
    private lateinit var v : View
    private lateinit var addButton : Button
    private lateinit var recyclerView : RecyclerView
    private var db: recipeDatabase? = null
    private var recipeDao: recipeDao? = null
    private lateinit var recipeList : MutableList<Recipe>

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.recycler_view_fragment, container, false)
        addButton = v.findViewById(R.id.addBtn)
        recyclerView = v.findViewById(R.id.recyclerViewRecipes)
        return v
    }

    override fun onStart() {
        super.onStart()

        db = recipeDatabase.getAppDataBase(v.context)
        recipeDao = db?.recipeDao()

        recipeList = recipeDao?.loadAllPersons() as MutableList<Recipe>

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = RecipeAdapter(recipeList,requireContext()){ index ->
            onItemClick(index)
        }

        addButton.setOnClickListener {
            val action = RecyclerViewFragmentDirections.actionRecyclerViewFragmentToAddFragment()
            v.findNavController().navigate(action)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecyclerViewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun onItemClick(pos : Int){

    }

}