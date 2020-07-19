package com.leonardoamurca.lmdb.network

enum class HttpStatus(val code: Int) {
    MOVED_PERMANENTLY(301),
    FOUND(302),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
}