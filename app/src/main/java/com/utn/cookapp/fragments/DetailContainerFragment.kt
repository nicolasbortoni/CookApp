package com.utn.cookapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.utn.cookapp.R
import com.utn.cookapp.viewmodels.DetailContainerViewModel

class DetailContainerFragment : Fragment() {

    //Views
    private lateinit var v : View
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager : ViewPager2


    companion object {
        fun newInstance() = DetailContainerFragment()
    }

    private lateinit var viewModel: DetailContainerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Views configuration
        v = inflater.inflate(R.layout.detail_container_fragment, container, false)
        tabLayout = v.findViewById(R.id.tabLayout)
        viewPager = v.findViewById(R.id.viewPager)
        return v
    }

    override fun onStart() {
        super.onStart()
        //Set tab layout
        viewPager.setAdapter(ViewPagerAdapter(requireActivity()))
        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                0 -> tab.text = "Info"
                1 -> tab.text = "Recipe"
                else -> tab.text = "undefined"
            }
        }).attach()
    }

    class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {

            return when(position){
                0 -> DetailInfoFragment()
                1 -> DetailRecipeFragment()

                else -> DetailInfoFragment()
            }
        }

        override fun getItemCount(): Int {
            return TAB_COUNT
        }

        companion object {
            private const val TAB_COUNT = 2
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailContainerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}