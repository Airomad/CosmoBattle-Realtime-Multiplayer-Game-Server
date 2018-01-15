package io.github.airomad.cosmobattle.server

import java.net.InetSocketAddress

/**
 * Author: Airomad
 * created: 15.01.18
 */
fun main(args: Array<String>) {
    val host = "localhost"
    val port = 8081

    Server(InetSocketAddress(host, port)).run()
}
