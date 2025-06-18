package com.db.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(
    val x: Double,
    val y: Double
)
@Serializable
data class SessionEvent(
    val userTimeUtc:String,
    val position:Coordinate
)

@Serializable
data class UserSession(
    val userId:String,
    val sessionId:String,
    val startTimeUtc:String,
    val endTimeUtc:String,
    val startTimeLocal:String,
    val path:List<SessionEvent>
)

@Serializable
data class Venue(
    val id:String,
    val name:String,
    val position:Coordinate
)

