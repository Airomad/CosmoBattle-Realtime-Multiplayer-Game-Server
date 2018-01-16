package io.github.airomad.cosmobattle.server

import java.util.*

class PacketsQueue {
    companion object {
        private val packets: Queue<Packet> = PriorityQueue<Packet>()
        val size
            get() = packets.size

        fun push(packet: Packet) {
            synchronized(packets) {
                packets.add(packet)
            }
        }

        fun pop(): Packet {
            lateinit var p: Packet
            synchronized(packets) {
                p = packets.remove()
            }
            return p
        }
    }
}