package br.com.fiap.product_management.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.product_management.R
import br.com.fiap.product_management.data.remote.datasource.product.impl.ProductRemoteDataSourceImpl
import br.com.fiap.product_management.data.remote.datasource.store.impl.StoreRemoteDataSourceImpl
import br.com.fiap.product_management.data.repository.ProductRepositoryImpl
import br.com.fiap.product_management.data.repository.StoreRepositoryImpl
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.usecases.product.ProductListUseCase
import br.com.fiap.product_management.domain.usecases.store.StoreLoggedUseCase
import br.com.fiap.product_management.domain.usecases.store.StoreLogoutUseCase
import br.com.fiap.product_management.presentation.base.auth.BaseAuthFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_home


    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModelFactory(
                StoreLoggedUseCase(
                    StoreRepositoryImpl(
                        StoreRemoteDataSourceImpl(
                            Firebase.auth,
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
                StoreLogoutUseCase(
                    StoreRepositoryImpl(
                        StoreRemoteDataSourceImpl(
                            Firebase.auth,
                            Firebase.firestore
                        )
                    )
                ),
            )
        ).get(HomeViewModel::class.java)
    }

    private lateinit var tvHomeStoreName: TextView
    private lateinit var btHomeProductAdd: FloatingActionButton
    private lateinit var rvHomeProductList: RecyclerView
    private lateinit var ivAbout: ImageView
    private lateinit var ivLogoutButton: ImageView


    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()
        registerObserver()
        setUpViewListeners(view)
        homeViewModel.getStoreLoggedIn()
        homeViewModel.getProductList()
    }

    private fun setUpViewListeners(view: View) {
        tvHomeStoreName = view.findViewById(R.id.tvHomeStoreName)
        btHomeProductAdd = view.findViewById(R.id.btHomeProductAdd)
        rvHomeProductList = view.findViewById(R.id.rvHomeProductList)
        ivAbout = view.findViewById(R.id.ivAbout)
        ivLogoutButton = view.findViewById(R.id.ivLogoutButton)

        ivLogoutButton.setOnClickListener {
            homeViewModel.logout()

        }

        btHomeProductAdd.setOnClickListener {
            findNavController().navigate(R.id.productFragment, null, null)
        }

        ivAbout.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment, null, null)
        }
    }

    private fun registerObserver() {
        this.homeViewModel.storeLogged.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    tvHomeStoreName.text = "Ol??, ${it.data.name}"
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> showLoading("carregando dados do usu??rio")
            }
        })

        this.homeViewModel.productList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    setProductList(it.data)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> showLoading()
            }
        })

        this.homeViewModel.storeLogout.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.login_nav_graph)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
            }
        })
    }


    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setProductList(items: List<Product>) {
        rvHomeProductList.adapter = HomeAdapter(items, this::clickItem)
    }

    private fun clickItem(item: Product) {

    }

}