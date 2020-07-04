package me.javicabanas.randomuser.usercatalog.userlist

sealed class UserListViewState {
    object Empty : UserListViewState()
    object Loading : UserListViewState()
    data class WithData(val users: List<UserUiModel>) : UserListViewState()
}
