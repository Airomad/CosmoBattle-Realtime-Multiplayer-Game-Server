package io.github.airomad.cosmobattle.server

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import java.net.InetSocketAddress
import java.util.*


class Server(private val inetAddress: InetSocketAddress) : WebSocketServer(inetAddress) {
    private lateinit var gmResolver: GameWorldResolver
    private val packets: Queue<String> = PriorityQueue<String>()

    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        broadcast("Connected new client: ${handshake?.resourceDescriptor}")
        println("Connected new client: ${conn?.remoteSocketAddress}")
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        println("Disconnected client: ${conn?.remoteSocketAddress}")
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
        packets.add(message)
        broadcast("[${conn?.remoteSocketAddress}] $message")
        println("[${conn?.remoteSocketAddress}] $message")
    }

    override fun onStart() {
        println("Server successfully started on ${this.inetAddress.hostName}:${this.inetAddress.port}.")
        gmResolver = GameWorldResolver()
        gmResolver.run()
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        println("Error catch client: ${conn?.remoteSocketAddress}")
    }
}

class GameWorldResolver : Runnable {
    var running: Boolean = false

    override fun run() {
        running = true
        println("Running Game World Instance..")
        while (running) {
            try {
                Thread.sleep(100)
            } catch (e: Exception) {
                println("Cant sleep for 100 ms")
            }
        }
    }
}