package com.example.ddeadlinehero.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface AssignmentApi {

    @POST("assignments")
    suspend fun createAssignment(
        @Body request: CreateAssignmentRequest
    ): CreateAssignmentResponse
}
