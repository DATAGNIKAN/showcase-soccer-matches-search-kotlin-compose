package com.example.showcase_soccer_matches_search_kotlin_compose.utils

import android.os.Build
import androidx.annotation.RequiresApi
import org.typesense.api.Client
import org.typesense.api.Configuration
import org.typesense.resources.Node
import java.time.Duration


@RequiresApi(Build.VERSION_CODES.O)
fun setupTypesenseClient(): Client {
    val nodes: MutableList<Node> = ArrayList()
    nodes.add(
        Node(
            "http",
            "192.168.10.164", // your Typesense hostname
            "8108"
        )
    )

    val config = Configuration(nodes, Duration.ofSeconds(2), "xyz") //your Typesense API key

    return Client(config)
}