package com.rach.swipestore.presentation.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FieldStateViewModel @Inject constructor() : ViewModel(){

    private val _productTypeField = MutableStateFlow("")
    val productTypeField : StateFlow<String> = _productTypeField.asStateFlow()

    fun onProductTypeFieldChange(newValue: String){
        _productTypeField.value = newValue
    }

}

