package br.com.fiap.product_management.presentation.product

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
import br.com.fiap.product_management.data.remote.datasource.store.impl.StoreRemoteDataSourceImpl
import br.com.fiap.product_management.data.repository.ProductRepositoryImpl
import br.com.fiap.product_management.data.repository.StoreRepositoryImpl
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.usecases.product.ProductCreateUserCase
import br.com.fiap.product_management.domain.usecases.product.ProductDeleteUseCase
import br.com.fiap.product_management.domain.usecases.product.ProductListUseCase
import br.com.fiap.product_management.domain.usecases.product.ProductUpdateUserCase
import br.com.fiap.product_management.domain.usecases.store.StoreLoggedUseCase
import br.com.fiap.product_management.presentation.base.auth.BaseAuthFragment
import br.com.fiap.product_management.presentation.base.auth.NAVIGATION_KEY
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.NumberFormat

class ProductFragment  : BaseAuthFragment() {
    override val layout = R.layout.fragment_product

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProvider(
            this,
            ProductViewModelFactory(
                ProductCreateUserCase(
                    StoreLoggedUseCase(
                        StoreRepositoryImpl(
                            StoreRemoteDataSourceImpl(
                                Firebase.auth,
                                Firebase.firestore
                            )
                        )
                    ),
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
                ProductListUseCase(
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
        ).get(ProductViewModel::class.java)
    }

    private lateinit var ivProductBackButton: ImageView
    private lateinit var etProductName: TextInputEditText
    private lateinit var etProductPrice: TextInputEditText
    private lateinit var etProductAmount: TextInputEditText
    private lateinit var btProductAdd: MaterialButton

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewListeners(view)
        registerObserver()
    }

    private fun setUpViewListeners(view: View) {
        ivProductBackButton = view.findViewById(R.id.ivProductBackButton)
        etProductName = view.findViewById(R.id.etProductName)
        etProductPrice = view.findViewById(R.id.etProductPrice)
        etProductAmount = view.findViewById(R.id.etProductAmount)
        btProductAdd = view.findViewById(R.id.btProductAdd)

        /*etProductPrice.addTextChangedListener(object : TextWatcher {
            private var current = ""

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != current) {
                    etProductPrice.removeTextChangedListener(this)
                    val cleanString = s.toString().replace("[R$,.]".toRegex(), "")
                    val parsed = cleanString.toDouble()
                    val formatted = NumberFormat.getCurrencyInstance().format(parsed / 100)
                    current = formatted.replace("[R$]".toRegex(), "")
                    etProductPrice.setText(current)
                    etProductPrice.setSelection(current.length)
                    etProductPrice.addTextChangedListener(this)
                }
            }
        })*/

        ivProductBackButton.setOnClickListener {
            val navIdForArguments = arguments?.getInt(NAVIGATION_KEY)
            if (navIdForArguments == null) {
                findNavController().navigate(R.id.main_nav_graph)
            } else {
                findNavController().popBackStack(navIdForArguments, false)
            }
        }

        btProductAdd.setOnClickListener {
            productViewModel.createProduct(
                etProductName.text.toString(),
                etProductPrice.text.toString().toDouble(),
                etProductAmount.text.toString().toInt()
            )
        }
    }

    private fun registerObserver() {
        this.productViewModel.productCreateStoreState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    val navIdForArguments = arguments?.getInt(NAVIGATION_KEY)
                    showMessage(getString(R.string.toaster_product_created_succcess))
                    if (navIdForArguments == null) {
                        findNavController().navigate(R.id.main_nav_graph)
                    } else {
                        findNavController().popBackStack(navIdForArguments, false)
                    }
                }
                is RequestState.Error -> {
                    hideLoading()
                    etProductName.error = null
                    etProductPrice.error = null
                    etProductAmount.error = null
                    showMessage(getString(R.string.toaster_product_created_error))
                }
            }
        })
    }
}