package com.example.domain

interface UseCase<Request, Response> {
    suspend operator fun invoke(request: Request): Response
}