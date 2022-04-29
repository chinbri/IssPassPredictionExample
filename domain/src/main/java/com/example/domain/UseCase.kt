package com.example.domain

interface UseCase<Request, Response> {
    operator fun invoke(request: Request): Response
}