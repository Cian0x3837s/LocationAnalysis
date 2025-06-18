package com.db.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class DwellTimeResult(
    val users:List<UserDwellTime>
)

@Serializable
data class UserDwellTime(
    val userId: String,
    val venueTimes:List<VenueDwellTime>
)

@Serializable
data class VenueDwellTime(
    val venueId: String,
    val dwellTime: Double
)