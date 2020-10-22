package com.armboldmind.baggleapp.root.viewCommand

import androidx.annotation.StringRes

interface ViewCommand

class NetworkErrorViewCommand : ViewCommand

class ShowMessageViewCommand(@StringRes val resId: Int) : ViewCommand

class ShowMessageTextViewCommand(val errorMessage: String) : ViewCommand

class HideLoadingViewCommand : ViewCommand
class ShowLoadingViewCommand : ViewCommand

class ShowSecondaryLoading : ViewCommand
class HideSecondaryLoading : ViewCommand

class RefreshTokenViewCommand : ViewCommand

class NavigateToTripDetailsViewCommand(val tripID:Int):ViewCommand