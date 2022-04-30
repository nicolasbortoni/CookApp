package com.utn.cookapp.fragments

import android.graphics.Color
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.utn.cookapp.R
import com.utn.cookapp.adapters.RecipeAdapter
import com.utn.cookapp.database.recipeDao
import com.utn.cookapp.database.recipeDatabase
import com.utn.cookapp.entities.Recipe
import com.utn.cookapp.viewmodels.RecyclerViewViewModel

class RecyclerViewFragment : Fragment() {

    private lateinit var viewModel: RecyclerViewViewModel

    private lateinit var v : View

    private lateinit var addButton : FloatingActionButton
    private lateinit var editButton : FloatingActionButton
    private lateinit var trashButton : FloatingActionButton
    private var selectedPosition : Int = 0

    private lateinit var recyclerView : RecyclerView

    private var db: recipeDatabase? = null
    private var recipeDao: recipeDao? = null

    private lateinit var recipeList : MutableList<Recipe>

    val funct1 = { index : Int -> onLongItemClick(index) }
    val funct2 = { index : Int -> onItemClick(index) }

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
        editButton = v.findViewById(R.id.editBtn)
        trashButton = v.findViewById(R.id.trashBtn)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = recipeDatabase.getAppDataBase(v.context)
        recipeDao = db?.recipeDao()

        recipeList = recipeDao?.loadAllPersons() as MutableList<Recipe>

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = RecipeAdapter(recipeList,requireContext(),funct1,funct2,-1)


        addButton.setOnClickListener {
            val action = RecyclerViewFragmentDirections.actionRecyclerViewFragmentToAddFragment()
            v.findNavController().navigate(action)
        }

        trashButton.setOnClickListener{

            if(selectedPosition < recipeList.size && selectedPosition>=0){

                recipeDao?.delete(recipeList[selectedPosition])
                recipeList = recipeDao?.loadAllPersons() as MutableList<Recipe>
                recyclerView.adapter = RecipeAdapter(recipeList,requireContext(),funct1,funct2,-1)
                selectedPosition = -2
            }

        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecyclerViewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun onLongItemClick(pos : Int) : Boolean{
        if(selectedPosition==pos){
            recyclerView.adapter = RecipeAdapter(recipeList, requireContext(), funct1, funct2, -2)
            selectedPosition = -2
        }
        else {
            recyclerView.adapter = RecipeAdapter(recipeList, requireContext(), funct1, funct2, pos)
            selectedPosition = pos
        }
        return true
    }
    fun onItemClick(pos : Int){

    }

}