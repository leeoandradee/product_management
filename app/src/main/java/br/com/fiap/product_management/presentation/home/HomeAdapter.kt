package br.com.fiap.product_management.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.product_management.R
import br.com.fiap.product_management.domain.entity.product.Product

class HomeAdapter(
    private var menuItems: List<Product>,
    private var clickListener: (Product) -> Unit
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(menuItems[position], clickListener)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Product, clickListener: (Product) -> Unit) {
            val label = itemView.findViewById<TextView>(R.id.tvProductName)
            val updateButton = itemView.findViewById<ImageView>(R.id.ivUpdateProduct)
            val deleteButton = itemView.findViewById<ImageView>(R.id.ivDeleteProduct)

            label.text = item.name

            updateButton.setOnClickListener {
                val bundle = bundleOf("productId" to item.id)
                val navController = Navigation.findNavController(itemView)
                navController!!.navigate(R.id.productUpdateFragment, bundle, null)
            }

            deleteButton.setOnClickListener {
                val bundle = bundleOf("productId" to item.id)
                val navController = Navigation.findNavController(itemView)
                navController!!.navigate(R.id.productDetailsFragment, bundle, null)
            }

            itemView.setOnClickListener { clickListener(item) }
        }
    }


}