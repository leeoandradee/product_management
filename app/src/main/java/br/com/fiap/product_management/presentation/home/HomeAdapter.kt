package br.com.fiap.product_management.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

            label.text = item.name

            itemView.setOnClickListener { clickListener(item) }
        }
    }


}