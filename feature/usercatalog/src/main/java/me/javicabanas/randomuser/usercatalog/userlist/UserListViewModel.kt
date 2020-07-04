package me.javicabanas.randomuser.usercatalog.userlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.javicabanas.randomuser.usercatalog.domain.GetAllUsers

class UserListViewModel @ViewModelInject constructor(private val getAllUsers: GetAllUsers) :
    ViewModel() {
    private val _viewState = MutableLiveData<UserListViewState>()
        .apply {
            value = UserListViewState.Empty
        }
    val viewState: LiveData<UserListViewState> = _viewState
    fun loadUsers() {
        _viewState.value = UserListViewState.Loading
        viewModelScope.launch {
            _viewState.value = getAllUsers(Unit)
                .map { users ->
                    users.map { it.toUi() }
                }
                .fold(
                    ifLeft = {
                        UserListViewState.Empty
                    },
                    ifRight = {
                        UserListViewState.WithData(it)
                    }
                )
        }
    }
}
