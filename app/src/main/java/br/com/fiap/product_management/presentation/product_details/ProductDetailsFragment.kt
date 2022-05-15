package br.com.fiap.product_management.presentation.product_details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.fiap.product_management.R
import br.com.fiap.product_management.data.remote.datasource.product.impl.ProductRemoteDataSourceImpl
import br.com.fiap.product_management.data.repository.ProductRepositoryImpl
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.usecases.product.*
import br.com.fiap.product_management.presentation.base.auth.BaseAuthFragment
import br.com.fiap.product_management.presentation.base.auth.NAVIGATION_KEY
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductDetailsFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_product_details
    private lateinit var selectedProduct: Product

    private val productDetailsViewModel: ProductDetailsViewModel by lazy {
        ViewModelProvider(
            this,
            ProductDetailsViewModelFactory(
                ProductGetUseCase(
                    ProductRepositoryImpl(
                        ProductRemoteDataSourceImpl(
                            Firebase.firestore
                        )
                    )
                ),
                ProductDeleteUseCase(
                    ProductRepositoryImpl(
                        ProductRemoteDataSourceImpl(
                            Firebase.firestore
                        )
                    )
                ),
            )
        ).get(ProductDetailsViewModel::class.java)
    }

    private lateinit var ivProductDetailsBackButton: ImageView
    private lateinit var tvProductDetailsName: TextView
    private lateinit var tvProductDetailsPrice: TextView
    private lateinit var tvProductDetailsAmount: TextView
    private lateinit var btProductDetailsDelete: MaterialButton

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewListeners(view)
        registerObserver()
        productDetailsViewModel.getProduct(arguments?.getString("productId") ?: "")
    }

    private fun setUpViewListeners(view: View) {
        ivProductDetailsBackButton = view.findViewById(R.id.ivProductDetailsBackButton)
        tvProductDetailsName = view.findViewById(R.id.tvProductDetailsName)
        tvProductDetailsPrice = view.findViewById(R.id.tvProductDetailsPrice)
        tvProductDetailsAmount = view.findViewById(R.id.tvProductDetailsAmount)
        btProductDetailsDelete = view.findViewById(R.id.btProductDetailsDelete)

        ivProductDetailsBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        btProductDetailsDelete.setOnClickListener {
            productDetailsViewModel.deleteProduct(selectedProduct)
        }
    }

    private fun registerObserver() {
        productDetailsViewModel.productSelected.observe(
            viewLifecycleOwner, Observer {
                when (it) {
                    is RequestState.Success -> {
                        val product = it.data
                        selectedProduct = product
                        tvProductDetailsName.text = product.name
                        tvProductDetailsPrice.text = product.price.toString()
                        tvProductDetailsAmount.text = product.amount.toString()
                    }
                    is RequestState.Error -> {
                        hideLoading()
                        showMessage(it.throwable.message)
                    }
                    is RequestState.Loading -> showLoading("Loading product")
                }
            })

        productDetailsViewModel.productDeleted.observe(
            viewLifecycleOwner, Observer {
                when (it) {
                    is RequestState.Success -> {
                        val deleted = it.data
                        if (deleted) {
                            showMessage("Product deleted")
                            findNavController().popBackStack()
                        } else {
                            showMessage("Error to delete product")
                        }
                    }
                    is RequestState.Error -> {
                        hideLoading()
                        showMessage(it.throwable.message)
                    }
                    is RequestState.Loading -> showLoading("Deleting product")
                }
            })

    }
}