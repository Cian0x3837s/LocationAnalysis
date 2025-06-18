package com.db.myapplication.utils

import android.content.Context
import android.util.Log
import com.db.myapplication.model.Coordinate
import com.db.myapplication.model.DwellTimeResult
import com.db.myapplication.model.UserDwellTime
import com.db.myapplication.model.UserSession
import com.db.myapplication.model.Venue
import com.db.myapplication.model.VenueDwellTime
import kotlin.math.pow
import kotlin.math.sqrt

object Utils {


    fun readJsonFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }


    fun distanceCalculator(
        currentLocation: Coordinate,
        targetLocation: Coordinate
    ): Double {
        return sqrt(
            (targetLocation.x - currentLocation.x).pow(2) + (targetLocation.y - currentLocation.y).pow(
                2
            )
        )
    }

    fun userDwellDurationCalculator(
        sessionData: List<UserSession>,
        venueData: List<Venue>,
        radius: Double = 2.0
    ): DwellTimeResult {

        val perUserDwellTimeListForEachVenue = sessionData.map { userSessions ->
            val venueTimes = currentUserDwellDurationForVenues(userSessions, venueData, radius)

            UserDwellTime(
                userId = userSessions.userId,
                venueTimes = venueTimes.map { (venueId, dwellTime) ->
                    VenueDwellTime(
                        venueId = venueId,
                        dwellTime = dwellTime
                    )
                }
            )
        }

        return DwellTimeResult(users = perUserDwellTimeListForEachVenue)

    }

    private fun currentUserDwellDurationForVenues(
        session: UserSession,
        venueList: List<Venue>,
        threshold: Double
    ): Map<String, Double> {
        val dwellDurationPerVenue = mutableMapOf<String, Double>()

        for (pth in session.path) {
            val matchedVenue = venueList.firstOrNull { venue ->
                distanceCalculator(pth.position, venue.position) <= threshold
            }

            matchedVenue?.let { venue ->
                dwellDurationPerVenue[venue.id] =
                    dwellDurationPerVenue.getOrDefault(venue.id, 0.0) + 1.0
            }
        }

        return dwellDurationPerVenue
    }

}