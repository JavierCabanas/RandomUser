package me.javicabanas.randomuser.usercatalog.userdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.javicabanas.randomuser.usercatalog.domain.GetUser

class UserDetailViewModel @ViewModelInject constructor(private val getUser: GetUser) : ViewModel() {
    private val _viewState = MutableLiveData<UserDetailViewState>()
        .apply {
            value = UserDetailViewState.Loading
        }
    val viewState: LiveData<UserDetailViewState> = _viewState
    fun loadUser(userId: String?) {
        _viewState.value = UserDetailViewState.Loading
        viewModelScope.launch {
            _viewState.value =
                if (userId == null) {
                    UserDetailViewState.Error
                } else {
                    doLoadUser(userId)
                }
        }
    }

    private suspend fun doLoadUser(userId: String): UserDetailViewState = getUser(userId)
        .map { it.toDetailUi() }
        .fold(
            ifLeft = {
                UserDetailViewState.Error
            },
            ifRight = {
                UserDetailViewState.WithData(it)
            }
        )
}
