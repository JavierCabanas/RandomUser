package me.javicabanas.randomuser.usercatalog.userdetail

sealed class UserDetailViewState {
    object Error : UserDetailViewState()
    object Loading : UserDetailViewState()
    data class WithData(val users: UserUiDetail) : UserDetailViewState()
}
