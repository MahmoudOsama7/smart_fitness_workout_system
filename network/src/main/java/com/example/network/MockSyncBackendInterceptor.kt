package com.example.network

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.util.Random
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockSyncBackendInterceptor @Inject constructor() : Interceptor {

    private val random = Random()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Only mock the workout sync endpoint
        if (request.url.encodedPath.contains("/api/v1/workouts/sync")) {

            // 1. Simulate 1.5 seconds network latency
            Thread.sleep(1500)

            // 2. Simulate 30% failure rate
            val isFailure = random.nextInt(100) < 30

            if (isFailure) {
                // Simulate periodic network drops or server failure
                val simulateTimeout = random.nextBoolean()
                if (simulateTimeout) {
                    throw IOException("Network Connection Lost. Offline mode active.")
                } else {
                    return Response.Builder()
                        .code(500)
                        .message("Internal Server Error")
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .body("{\"error\": \"Server temporarily unavailable\"}".toResponseBody("application/json".toMediaType()))
                        .build()
                }
            }

            // 3. Return Successful 200 OK Response
            val jsonResponseBody = """
                {
                    "success": true,
                    "message": "Workout session synced successfully.",
                    "synced_id": 101
                }
            """.trimIndent()

            return Response.Builder()
                .code(200)
                .message("OK")
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .body(jsonResponseBody.toResponseBody("application/json".toMediaType()))
                .build()
        }

        return chain.proceed(request)
    }
}