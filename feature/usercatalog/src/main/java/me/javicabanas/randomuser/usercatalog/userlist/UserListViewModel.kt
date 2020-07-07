package me.javicabanas.randomuser.usercatalog.userlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.javicabanas.randomuser.usercatalog.domain.DeleteUser
import me.javicabanas.randomuser.usercatalog.domain.GetAllUsers

class UserListViewModel @ViewModelInject constructor(
    private val getAllUsers: GetAllUsers,
    private val deleteUser: DeleteUser
) :
    ViewModel() {
    private val _viewState = MutableLiveData<UserListViewState>()
        .apply {
            value = UserListViewState.Loading
        }
    val viewState: LiveData<UserListViewState> = _viewState
    fun loadUsers() {
        _viewState.value = UserListViewState.Loading
        viewModelScope.launch {
            _viewState.value = doLoadUsers()
        }
    }

    private suspend fun doLoadUsers(): UserListViewState =
        getAllUsers(Unit)
            .map { users ->
                users.map { it.toListUi() }
            }
            .fold(
                ifLeft = {
                    UserListViewState.Error
                },
                ifRight = {
                    UserListViewState.WithData(it)
                }
            )

    fun deleteUserWithId(userId: String) {
        _viewState.value = UserListViewState.Loading
        viewModelScope.launch {
            deleteUser(userId)
            _viewState.value = doLoadUsers()
        }
    }
}
