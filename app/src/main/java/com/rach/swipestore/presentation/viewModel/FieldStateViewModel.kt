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

    private val _productName = MutableStateFlow("")
    val productName : StateFlow<String> = _productName.asStateFlow()

    fun onProductNameChange(newValue: String){
        _productName.value = newValue
    }

    private val _sellingPrice = MutableStateFlow("")
    val sellingPrice: StateFlow<String> = _sellingPrice.asStateFlow()

    fun onSellingPriceChange(newValue: String){
        _sellingPrice.value = newValue
    }

    private val _taxRate = MutableStateFlow("")
    val taxRate : StateFlow<String> = _taxRate.asStateFlow()

    fun onTaxRateChange(newValue: String){
        _taxRate.value =  newValue
    }

    fun clearAllFields(){
        _productName.value = ""
        _productTypeField.value = ""
        _sellingPrice.value = ""
        _taxRate.value = ""
    }

}

