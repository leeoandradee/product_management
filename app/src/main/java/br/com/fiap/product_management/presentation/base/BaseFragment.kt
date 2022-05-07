package br.com.fiap.product_management.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import br.com.fiap.product_management.R


abstract class BaseFragment : Fragment() {

    abstract val layout: Int
    private lateinit var loadingView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val screenRootView = FrameLayout(requireContext())
        val screenView = inflater.inflate(layout, container, false)

        loadingView = inflater.inflate(R.layout.include_loading, container, false)
        screenRootView.addView(screenView)
        screenRootView.addView(loadingView)

        return screenRootView
    }

    fun showLoading(message: String = "Loading") {
        loadingView.visibility = View.VISIBLE
        if (message.isNotEmpty())
            loadingView.findViewById<TextView>(R.id.tvLoading).text = message
    }
    fun hideLoading() {
        loadingView.visibility = View.GONE
    }
    fun showMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}