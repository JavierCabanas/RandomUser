package me.javicabanas.randomuser.usercatalog.userlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_user_list.*
import me.javicabanas.randomuser.androidcommons.lifecycle.observe
import me.javicabanas.randomuser.androidcommons.view.gone
import me.javicabanas.randomuser.androidcommons.view.visible
import me.javicabanas.randomuser.core.utills.exhaustive
import me.javicabanas.randomuser.usercatalog.R
import me.javicabanas.randomuser.usercatalog.userdetail.UserDetailActivity

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {
    private val viewModel: UserListViewModel by viewModels()
    private val adapter = UserListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        setupRecyclerView()
        observe(viewModel.viewState, ::onStateUpdated)
        viewModel.loadUsers()
    }

    private fun setupRecyclerView() {
        userRecyclerView.adapter = adapter
        userRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.onItemClick = ::openDetail
    }

    private fun onStateUpdated(userListViewState: UserListViewState) {
        when (userListViewState) {
            UserListViewState.Error -> renderError()
            UserListViewState.Loading -> renderLoading()
            is UserListViewState.WithData -> renderWithData(userListViewState.users)
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

    private fun renderWithData(users: List<UserUiItem>) {
        progressIndicator.hide()
        notFoundImage.gone()
        userRecyclerView.visible()
        adapter.items = users
    }

    private fun openDetail(userId: String) {
        UserDetailActivity.open(this, userId)
    }
}
