package me.javicabanas.randomuser.usercatalog.userdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.activity_user_list.*
import kotlinx.android.synthetic.main.content_user_detail.*
import me.javicabanas.randomuser.androidcommons.lifecycle.observe
import me.javicabanas.randomuser.androidcommons.view.gone
import me.javicabanas.randomuser.androidcommons.view.setImageUrl
import me.javicabanas.randomuser.androidcommons.view.visible
import me.javicabanas.randomuser.core.utills.exhaustive
import me.javicabanas.randomuser.usercatalog.R

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {
    companion object {
        private const val USER_ID = "userId"
        fun open(fromActivity: Activity, userId: String) {
            val intent = Intent(fromActivity, UserDetailActivity::class.java)
            intent.putExtra(USER_ID, userId)
            fromActivity.startActivity(intent)
        }
    }

    private val viewModel by viewModels<UserDetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        observe(viewModel.viewState, ::onStateUpdated)
        val userId = intent.extras?.getString(USER_ID)
        viewModel.loadUser(userId)
    }

    private fun onStateUpdated(state: UserDetailViewState) {
        when (state) {
            UserDetailViewState.Error -> renderError()
            UserDetailViewState.Loading -> renderLoading()
            is UserDetailViewState.WithData -> renderWithData(state.user)
        }.exhaustive
    }

    private fun renderError() {
        progressIndicator.hide()
        notFoundImage.visible()
    }

    private fun renderLoading() {
        notFoundImage.gone()
        progressIndicator.show()
    }

    private fun renderWithData(user: UserUiDetail) {
        collapsingToolbar.title = user.name
        avatar.setImageUrl(user.avatar)
        email.text = user.email
        gender.text = user.gender
        city.text = user.city
        description.text = user.description
    }
}
