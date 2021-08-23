package com.heb.soli

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}