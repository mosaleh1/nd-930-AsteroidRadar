package com.udacity.asteroidradar.model

data class Response
    (
    val links: Links,
    val element_count: Int,
    val near_earth_objects : List<Asteroid>
)

data class Links(
    val next: String,
    val previous: String,
    val self: String
)