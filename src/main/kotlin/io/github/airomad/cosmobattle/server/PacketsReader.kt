package io.github.airomad.cosmobattle.server

class PacketsReader {
    companion object {
        fun read(packet: String) {
            val array = packet.split('|')
            println(array)
        }
    }
}