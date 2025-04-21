package com.example.architectcoders.ui.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.architectcoders.Result
import com.example.architectcoders.domain.Stock
import com.example.architectcoders.ui.detail.DetailViewModel
import com.example.architectcoders.usecases.FetchStocksUseCase
import com.example.architectcoders.usecases.ToggleFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    private lateinit var sut : HomeViewModel
    private val fetchStocksUseCase : FetchStocksUseCase = mockk(relaxed = true)
    private val toggleFavoriteUseCase : ToggleFavoriteUseCase = mockk(relaxed = true)
    private val fakeStocks = listOf(
        Stock("AAPL", "Apple", "Technology", isFavorite = false),
        Stock("GOOGL", "Google", "Tech", isFavorite = true)
    )
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        sut = HomeViewModel(fetchStocksUseCase = fetchStocksUseCase , toggleFavoriteUseCase)
    }

    @AfterEach
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun initialStateIsLoading() = runTest{
        sut.state.test {
            assertEquals(Result.Loading, awaitItem())
        }
    }

    @Test
    fun uiReady() = runTest {
        every { fetchStocksUseCase.invoke()} answers {
            flowOf(fakeStocks)
        }
        sut.state.test {
            assertEquals(Result.Loading, awaitItem())
            sut.onUiReady()
            assertEquals(Result.Success(fakeStocks), awaitItem())
        }
    }

    @Test
    fun onFavoriteClickCallsToggleFavoriteUseCase() = runTest {
        val symbol = "AAPL"

        sut.onFavoriteClick(symbol)

        coVerify { toggleFavoriteUseCase(symbol) }
    }

    @Test
    fun onClearMethodAsExpected() = runTest {
        coEvery { fetchStocksUseCase() } returns flowOf(fakeStocks)

        sut.onClear()
        sut.state.test {
            assertEquals(Result.Success(emptyList<Stock>()), awaitItem())
        }
    }

}