package com.isspass.domain.usecase

interface UseCase<Request, Response> {
    suspend operator fun invoke(request: Request): Response
}