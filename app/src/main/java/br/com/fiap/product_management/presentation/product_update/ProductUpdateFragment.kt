package br.com.fiap.product_management.presentation.product_update

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.fiap.product_management.R
import br.com.fiap.product_management.data.remote.datasource.product.impl.ProductRemoteDataSourceImpl
import br.com.fiap.product_management.data.repository.ProductRepositoryImpl
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.usecases.product.ProductGetUseCase
import br.com.fiap.product_management.domain.usecases.product.ProductUpdateUserCase
import br.com.fiap.product_management.presentation.base.auth.BaseAuthFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.NumberFormat

class ProductUpdateFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_product_update
    private lateinit var selectedProduct: Product

    private val productUpdateViewModel: ProductUpdateViewModel by lazy {
        ViewModelProvider(
            this,
            ProductUpdateViewModelFactory(
                ProductGetUseCase(
                    ProductRepositoryImpl(
                        ProductRemoteDataSourceImpl(
                            Firebase.firestore
                        )
                    )
                ),
                ProductUpdateUserCase(
                    ProductRepositoryImpl(
                        ProductRemoteDataSourceImpl(
                            Firebase.firestore
                        )
                    )
                ),
            )
        ).get(ProductUpdateViewModel::class.java)
    }

    private lateinit var ivProductUpdateBackButton: ImageView
    private lateinit var etProductUpdateName: TextInputEditText
    private lateinit var etProductUpdatePrice: TextInputEditText
    private lateinit var etProductUpdateAmount: TextInputEditText
    private lateinit var btProductUpdate: MaterialButton

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewListeners(view)
        registerObserver()
        productUpdateViewModel.getProduct(arguments?.getString("productId") ?: "")
    }

    private fun setUpViewListeners(view: View) {
        ivProductUpdateBackButton = view.findViewById(R.id.ivProductUpdateBackButton)
        etProductUpdateName = view.findViewById(R.id.etProductUpdateName)
        etProductUpdatePrice = view.findViewById(R.id.etProductUpdatePrice)
        etProductUpdateAmount = view.findViewById(R.id.etProductUpdateAmount)
        btProductUpdate = view.findViewById(R.id.btProductUpdate)

        /*etProductUpdatePrice.addTextChangedListener(object : TextWatcher {
            private var current = ""

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != current) {
                    etProductUpdatePrice.removeTextChangedListener(this)
                    val cleanString = s.toString().replace("[R$.]".toRegex(), "")
                    val parsed = cleanString.toDouble()
                    val formatted = NumberFormat.getCurrencyInstance().format(parsed / 100)
                    current = formatted.replace("[R$]".toRegex(), "")
                    etProductUpdatePrice.setText(current)
                    etProductUpdatePrice.setSelection(current.length)
                    etProductUpdatePrice.addTextChangedListener(this)
                }
            }
        })*/

        ivProductUpdateBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        btProductUpdate.setOnClickListener {
            productUpdateViewModel.updateProduct(
                Product(
                    selectedProduct.id,
                    etProductUpdateName.text.toString(),
                    etProductUpdatePrice.text.toString().toDouble(),
                    etProductUpdateAmount.text.toString().toInt()
                )
            )
        }
    }

    private fun registerObserver() {
        productUpdateViewModel.productSelected.observe(
            viewLifecycleOwner, Observer {
                when (it) {
                    is RequestState.Success -> {
                        val product = it.data
                        selectedProduct = product
                        etProductUpdateName.text = Editable.Factory.getInstance().newEditable(product.name)
                        etProductUpdatePrice.text = Editable.Factory.getInstance().newEditable(product.price.toString())
                        etProductUpdateAmount.text = Editable.Factory.getInstance().newEditable(product.amount.toString())
                    }
                    is RequestState.Error -> {
                        hideLoading()
                        showMessage(it.throwable.message)
                    }
                }
            }
        )

        productUpdateViewModel.productUpdated.observe(
            viewLifecycleOwner, Observer {
                when (it) {
                    is RequestState.Success -> {
                        showMessage(getString(R.string.toaster_product_updated_succcess))
                        findNavController().popBackStack()
                    }
                    is RequestState.Error -> {
                        hideLoading()
                        showMessage(getString(R.string.toaster_product_updated_error))
                    }
                }
            }
        )

    }
}