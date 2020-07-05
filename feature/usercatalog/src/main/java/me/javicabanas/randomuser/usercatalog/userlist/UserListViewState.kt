package me.javicabanas.randomuser.usercatalog.userlist

sealed class UserListViewState {
    object Error : UserListViewState()
    object Loading : UserListViewState()
    data class WithData(val users: List<UserUiItem>) : UserListViewState()
}
