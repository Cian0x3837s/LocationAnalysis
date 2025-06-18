package com.db.myapplication.utils

import com.db.myapplication.model.Coordinate
import kotlin.math.pow
import kotlin.math.sqrt

object Utils {


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

}