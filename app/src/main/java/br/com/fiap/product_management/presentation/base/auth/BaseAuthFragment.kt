package br.com.fiap.product_management.presentation.base.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.product_management.data.remote.datasource.store.impl.StoreRemoteDataSourceImpl
import br.com.fiap.product_management.data.repository.StoreRepositoryImpl
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.usecases.store.StoreLoggedUseCase
import br.com.fiap.product_management.presentation.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import br.com.fiap.product_management.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val NAVIGATION_KEY = "NAV_KEY"
@ExperimentalCoroutinesApi
abstract class BaseAuthFragment : BaseFragment() {
    private val baseAuthViewModel: BaseAuthViewModel by lazy {
        ViewModelProvider(
            this,

            BaseViewModelFactory(
                StoreLoggedUseCase(
                    StoreRepositoryImpl(
                StoreRemoteDataSourceImpl(
                    Firebase.auth,
                    Firebase.firestore
                )
            ))
            )
        ).get(BaseAuthViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerObserver()
        baseAuthViewModel.getUserLogged()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun registerObserver() {
        baseAuthViewModel.userLoggedState.observe(viewLifecycleOwner, Observer {
                result ->
            when (result) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {
                    hideLoading()
                }
                is RequestState.Error -> {
                    findNavController(this).navigate(
                        R.id.login_nav_graph, bundleOf(
                            NAVIGATION_KEY to
                                    findNavController(this).currentDestination?.id
                        )
                    )
                    hideLoading()
                }
            }
        })
    }
}