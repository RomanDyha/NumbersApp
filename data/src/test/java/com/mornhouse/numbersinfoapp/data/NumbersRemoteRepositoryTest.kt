package com.mornhouse.numbersinfoapp.data

import com.mornhouse.numbersinfoapp.data.data_source.local.NumbersLocalDataSorce
import com.mornhouse.numbersinfoapp.data.data_source.remote.NumbersApiService
import com.mornhouse.numbersinfoapp.data.data_source.remote.NumbersRemoteDataSourceImpl
import com.mornhouse.numbersinfoapp.data.repository.NumbersRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class NumbersRemoteRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: NumbersApiService
    private lateinit var numbersRemoteDataSourceImpl: NumbersRemoteDataSourceImpl

    val numbersLocalDataSource: NumbersLocalDataSorce = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Initialize MockWebServer
        mockWebServer = MockWebServer()

        // Create Retrofit client with mock URL from MockWebServer
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(
                OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NumbersApiService::class.java)

        // Create a repository with a mocked ApiService
        numbersRemoteDataSourceImpl = NumbersRemoteDataSourceImpl(apiService)

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()  // Stopping MockWebServer
        Dispatchers.resetMain()   // Resetting the test dispatcher
    }

    @Test
    fun testGetNumberInfoFlow() = runTest {

        val mockResponse = MockResponse()
            .setBody(testResponse)  // JSON response from server
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val numbersRepositoryImpl = NumbersRepositoryImpl(numbersRemoteDataSourceImpl, numbersLocalDataSource)
        // Get the result from Flow
        val result = numbersRepositoryImpl.getNumberInfo(5).toList()

        val actualNumbersInfo = result.get(1).data
        // Compare the result with the expected one
        assertEquals(5L, actualNumbersInfo?.number)
        assertEquals("5 is the number of Justices on the Supreme Court of the United States necessary to render a majority decision." +
                "", actualNumbersInfo?.description)
    }

    @Test
    fun testGetNumberInfoError() = runTest {
        // Simulate server error (code 500)
        val mockResponse = MockResponse()
            .setResponseCode(500)
            .setBody("""{"error": "Server Error"}""")

        mockWebServer.enqueue(mockResponse)

        val numbersRepositoryImpl = NumbersRepositoryImpl(numbersRemoteDataSourceImpl, numbersLocalDataSource)
        val result = numbersRepositoryImpl.getNumberInfo(5)
        assertEquals("Server Error", result.toList().get(1).message)
    }

    val testResponse = "{\n" +
            "    \"text\": \"5 is the number of Justices on the Supreme Court of the United States necessary to render a majority decision.\",\n" +
            "    \"number\": 5,\n" +
            "    \"found\": true,\n" +
            "    \"type\": \"trivia\"\n" +
            "}"


}