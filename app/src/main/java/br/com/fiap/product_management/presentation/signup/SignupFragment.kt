package br.com.fiap.product_management.presentation.signup

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment

import br.com.fiap.product_management.R
import br.com.fiap.product_management.data.remote.datasource.store.impl.StoreRemoteDataSourceImpl
import br.com.fiap.product_management.data.repository.StoreRepositoryImpl
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.usecases.store.StoreSignUpUseCase
import br.com.fiap.product_management.presentation.base.BaseFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupFragment : BaseFragment() {
    override val layout = R.layout.fragment_signup

    private val signUpViewModel: SignupViewModel by lazy {
        ViewModelProvider(
            this,
            SignupViewModelFactory(
                StoreSignUpUseCase(
                    StoreRepositoryImpl(
                        StoreRemoteDataSourceImpl(
                            Firebase.auth,
                            Firebase.firestore
                        )
                    )
                )
            )
        ).get(SignupViewModel::class.java)
    }

    private lateinit var etSignupStoreName: TextInputEditText
    private lateinit var  etSignupCnpj: TextInputEditText
    private lateinit var etSignupEmail: TextInputEditText
    private lateinit var etSignupPassword: TextInputEditText
    private lateinit var etSignupConfirmPassword: TextInputEditText
    private lateinit var btSignup: MaterialButton

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObserver()
        setUpViewListeners(view)
    }

    private fun setUpViewListeners(view: View) {
        etSignupStoreName = view.findViewById(R.id.etSignupStoreName)
        etSignupCnpj = view.findViewById(R.id.etSignupCnpj)
        etSignupEmail = view.findViewById(R.id.etSignupEmail)
        etSignupPassword= view.findViewById(R.id.etSignupPassword)
        etSignupConfirmPassword= view.findViewById(R.id.etSignupConfirmPassword)
        btSignup= view.findViewById(R.id.btSignup)

        btSignup.setOnClickListener {
            signUpViewModel.signup(
                etSignupStoreName.text.toString(),
                etSignupCnpj.text.toString(),
                etSignupEmail.text.toString(),
                etSignupPassword.text.toString(),
                etSignupConfirmPassword.text.toString()
            )
        }
    }

    private fun registerObserver() {
        this.signUpViewModel.newStoreState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.main_nav_graph)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
            }
        })
    }
}