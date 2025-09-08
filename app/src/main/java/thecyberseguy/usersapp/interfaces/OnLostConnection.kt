package thecyberseguy.usersapp.interfaces

interface OnLostConnection {
    fun onRetry(apiCode: String?)
}