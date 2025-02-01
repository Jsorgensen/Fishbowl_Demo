package com.example.fishbowl_demo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fishbowl_demo.repositories.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesRepository: JokesRepository,
): ViewModel() {

    val jokesFlow = jokesRepository.jokesFlow

}