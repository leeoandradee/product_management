package br.com.fiap.product_management.presentation.signin

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.fiap.product_management.R
import br.com.fiap.product_management.data.remote.datasource.store.impl.StoreRemoteDataSourceImpl
import br.com.fiap.product_management.data.repository.StoreRepositoryImpl
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.usecases.store.StoreSignInUseCase
import br.com.fiap.product_management.presentation.base.BaseFragment
import br.com.fiap.product_management.presentation.base.auth.NAVIGATION_KEY
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SigninFragment : BaseFragment() {

    override val layout = R.layout.fragment_signin

    private val signinViewModel: SigninViewModel by lazy {
        ViewModelProvider(
            this,
            SigninViewModelFactory(
                StoreSignInUseCase(
                    StoreRepositoryImpl(
                        StoreRemoteDataSourceImpl(
                            Firebase.auth,
                            Firebase.firestore
                        )
                    )
                )
            )
        ).get(SigninViewModel::class.java)
    }

    private lateinit var etSignInEmail: TextInputEditText
    private lateinit var etSignInPassword: TextInputEditText
    private lateinit var btSignIn: MaterialButton
    private lateinit var tvLoginCreateAccount: TextView

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObserver()
        registerBackPressedAction()
        setUpViewListeners(view)
    }

    private fun setUpViewListeners(view: View) {
        etSignInEmail = view.findViewById(R.id.etSignInEmail)
        etSignInPassword = view.findViewById(R.id.etSignInPassword)
        btSignIn= view.findViewById(R.id.btSignIn)
        tvLoginCreateAccount= view.findViewById(R.id.tvLoginCreateAccount)

        btSignIn.setOnClickListener {
            signinViewModel.signup(etSignInEmail.text.toString(), etSignInPassword.text.toString())
        }

        tvLoginCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signinFragment_to_signupFragment)
        }
    }

    private fun registerObserver() {
        this.signinViewModel.signinStoreState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    val navIdForArguments = arguments?.getInt(NAVIGATION_KEY)
                    if (navIdForArguments == null) {
                        findNavController().navigate(R.id.main_nav_graph)
                    } else {
                        findNavController().popBackStack(navIdForArguments, false)
                    }
                }
                is RequestState.Error -> {
                    hideLoading()
                    etSignInEmail.error = null
                    etSignInPassword.error = null
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> showLoading("Authenticating")
            }
        })
    }


    private fun registerBackPressedAction () {
        val callback = object : OnBackPressedCallback( true) {
            override fun handleOnBackPressed () {
                activity?.finish()
            }
        }

        requireActivity(). onBackPressedDispatcher.addCallback( viewLifecycleOwner, callback)
    }
}