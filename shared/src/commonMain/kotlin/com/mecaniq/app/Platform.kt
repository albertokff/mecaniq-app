package com.mecaniq.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform