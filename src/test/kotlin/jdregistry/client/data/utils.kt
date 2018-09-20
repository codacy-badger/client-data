package jdregistry.client.data

internal fun <A, B> cross(it1: Iterable<A>, it2: Iterable<B>) =
        it1.flatMap { first -> it2.map { second -> Pair(first, second) } }
