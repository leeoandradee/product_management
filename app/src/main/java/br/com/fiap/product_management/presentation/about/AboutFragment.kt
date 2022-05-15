package br.com.fiap.product_management.presentation.about

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import br.com.fiap.product_management.R
import br.com.fiap.product_management.presentation.base.auth.BaseAuthFragment

class AboutFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_about

    private lateinit var ivAboutBackButton: ImageView

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewListeners(view)
    }

    private fun setUpViewListeners(view: View) {

        ivAboutBackButton = view.findViewById(R.id.ivAboutBackButton)

        ivAboutBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}