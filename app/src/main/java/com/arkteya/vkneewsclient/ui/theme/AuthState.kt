package com.arkteya.vkneewsclient.ui.theme


sealed class AuthState {

    object Authorized: AuthState()

    object NotAuthorized: AuthState()

    object Initial: AuthState()
}