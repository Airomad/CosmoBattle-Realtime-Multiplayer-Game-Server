package io.github.airomad.cosmobattle.server

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import java.net.InetSocketAddress


class Server(private val inetAddress: InetSocketAddress) : WebSocketServer(inetAddress) {
    private lateinit var gmResolver: GameWorldInstance


    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        broadcast("Connected new client: ${handshake?.resourceDescriptor}")
        println("Connected new client: ${conn?.remoteSocketAddress}")
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        println("Disconnected client: ${conn?.remoteSocketAddress}")
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
        val packet = PacketsReader.read(message)
        PacketsQueue.push(packet)
        println("packet pushed. Size is: ${PacketsQueue.size}")
        broadcast("[${conn?.remoteSocketAddress}] $message")
        println("[${conn?.remoteSocketAddress}] $message")
    }

    override fun onStart() {
        println("Server successfully started on ${this.inetAddress.hostName}:${this.inetAddress.port}.")
        gmResolver = GameWorldInstance()
        Thread(gmResolver).start()
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        println("Error catch client: ${conn?.remoteSocketAddress}")
    }
}

class GameWorldInstance : Runnable {
    var running: Boolean = false

    override fun run() {
        running = true
        println("Running Game World Instance..")
        while (running) {
            if (PacketsQueue.size > 0) {
                val packet = PacketsQueue.pop()
                println("packet poped. Size is: ${PacketsQueue.size}")
            }
            try {
                Thread.sleep(100)
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
            } catch (e: Exception) {
                println("Cant sleep for 100 ms")
            }
        }
        println("Stopping Game World Instance..")
    }
}