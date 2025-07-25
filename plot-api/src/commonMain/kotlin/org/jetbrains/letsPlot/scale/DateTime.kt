/*
 * Copyright (c) 2021. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package org.jetbrains.letsPlot.scale

import org.jetbrains.letsPlot.core.spec.Option.Scale.DATE_TIME
import org.jetbrains.letsPlot.core.spec.Option.Scale.TIME
import org.jetbrains.letsPlot.core.plot.base.Aes
import org.jetbrains.letsPlot.intern.Options
import org.jetbrains.letsPlot.intern.Scale
import org.jetbrains.letsPlot.intern.checkScaleExpand

/**
 * Position scale for the x-axis with date/time data.
 * The input is expected to be either a series of integers representing milliseconds since the Unix epoch,
 * or kotlinx.datetime, or java.util datetime objects.
 * Assumes UTC timezone if no timezone information is present in the data (naive datetime).
 * For timezone-aware datetime objects, the timezone information from the data is preserved.
 *
 * ## Examples
 *
 * - [formatting_axes_etc.ipynb](https://nbviewer.org/github/JetBrains/lets-plot-docs/blob/master/source/kotlin_examples/cookbook/formatting_axes_etc.ipynb)
 *
 * - [trading_chart.ipynb](https://nbviewer.org/github/JetBrains/lets-plot-docs/blob/master/source/kotlin_examples/demo/trading_chart.ipynb)
 *
 * @param name The name of the scale - used as the axis label or the legend title.
 *  If null, the default, the name of the scale is taken from the first mapping used for that aesthetic.
 * @param breaks A list of data values specifying the positions of ticks,
 *  or a dictionary which maps the tick labels to the breaks values.
 * @param labels A list of labels on ticks, or a dictionary which maps the breaks values to the tick labels.
 * @param lablim The maximum label length (in characters) before trimming is applied.
 * @param limits Data range for this scale.
 *  A pair of values providing limits of the scale. Use `null` to refer to default min/max.
 * @param expand A numeric vector of length two giving multiplicative and additive expansion constants.
 *  The vector size == 1 => only multiplicative expand (and additive expand by default).
 *  Defaults: multiplicative = 0.05, additive = 0.
 * @param naValue Missing values will be replaced with this value.
 * @param format Defines the format for labels on the scale. Also applies to `breaks`.
 * @param position The position of the axis:
 *  - "left", "right" or "both" for y-axis;
 *  - "top", "bottom" or "both" for x-axis.
 *
 * Format patterns in the `format` parameter can be just a number format (like "d") or
 * a string template where number format is surrounded by curly braces: "{d} cylinders".
 * Note: the "$" must be escaped as "\$".
 * For more info see: [formats.html](https://lets-plot.org/kotlin/formats.html)
 *
 * Examples:
 * - "%d.%m.%y" -> "06.08.19"
 * - "%B %Y" -> "August 2019"
 * - "%a, %e %b %Y %H:%M:%S" -> "Tue, 6 Aug 2019 04:46:35"
 *
 */
fun scaleXDateTime(
    name: String? = null,
    breaks: Any? = null,      // ToDo: should understand Date
    labels: Any? = null,
    lablim: Int? = null,
    limits: Pair<Any?, Any?>? = null,
    expand: List<Number>? = null,
    naValue: Any? = null,
    format: String? = null,
    position: String? = null
): Scale {
    checkScaleExpand(expand)
    return Scale(
        aesthetic = Aes.X,
        name = name,
        breaks = breaks,
        labels = labels,
        lablim = lablim,
        limits = limits,
        expand = expand,
        naValue = naValue,
        format = format,
        position = position,
        otherOptions = Options(
            mapOf(
                DATE_TIME to true
            )
        )
    )
}

/**
 * Position scale for the y-axis with date/time data.
 * The input is expected to be either a series of integers representing milliseconds since the Unix epoch,
 * or kotlinx.datetime, or java.util datetime objects.
 * Assumes UTC timezone if no timezone information is present in the data (naive datetime).
 * For timezone-aware datetime objects, the timezone information from the data is preserved.
 *
 * ## Examples
 *
 * - [formatting_axes_etc.ipynb](https://nbviewer.org/github/JetBrains/lets-plot-docs/blob/master/source/kotlin_examples/cookbook/formatting_axes_etc.ipynb)
 *
 * @param name The name of the scale - used as the axis label or the legend title.
 *  If null, the default, the name of the scale is taken from the first mapping used for that aesthetic.
 * @param breaks A list of data values specifying the positions of ticks,
 *  or a dictionary which maps the tick labels to the breaks values.
 * @param labels A list of labels on ticks, or a dictionary which maps the breaks values to the tick labels.
 * @param lablim The maximum label length (in characters) before trimming is applied.
 * @param limits Data range for this scale.
 *  A pair of values providing limits of the scale. Use `null` to refer to default min/max.
 * @param expand A numeric vector of length two giving multiplicative and additive expansion constants.
 *  The vector size == 1 => only multiplicative expand (and additive expand by default).
 *  Defaults: multiplicative = 0.05, additive = 0.
 * @param naValue Missing values will be replaced with this value.
 * @param format Defines the format for labels on the scale. Also applies to `breaks`.
 * @param position The position of the axis:
 *  - "left", "right" or "both" for y-axis;
 *  - "top", "bottom" or "both" for x-axis.
 *
 * Format patterns in the `format` parameter can be just a number format (like "d") or
 * a string template where number format is surrounded by curly braces: "{d} cylinders".
 * Note: the "$" must be escaped as "\$".
 * For more info see: [formats.html](https://lets-plot.org/kotlin/formats.html)
 *
 * Examples:
 * - "%d.%m.%y" -> "06.08.19"
 * - "%B %Y" -> "August 2019"
 * - "%a, %e %b %Y %H:%M:%S" -> "Tue, 6 Aug 2019 04:46:35"
 *
 */
fun scaleYDateTime(
    name: String? = null,
    breaks: Any? = null,      // ToDo: should understand Date
    labels: Any? = null,
    lablim: Int? = null,
    limits: Pair<Any?, Any?>? = null,
    expand: List<Number>? = null,
    naValue: Any? = null,
    format: String? = null,
    position: String? = null
): Scale {
    checkScaleExpand(expand)
    return Scale(
        aesthetic = Aes.Y,
        name = name,
        breaks = breaks,
        labels = labels,
        lablim = lablim,
        limits = limits,
        expand = expand,
        naValue = naValue,
        format = format,
        position = position,
        otherOptions = Options(
            mapOf(
                DATE_TIME to true
            )
        )
    )
}

/**
 * Position scale x for data representing "time delta" values expressed in milliseconds.
 *
 * ## Examples
 *
 * - [scale_time.ipynb](https://nbviewer.org/github/JetBrains/lets-plot-docs/blob/master/source/kotlin_examples/cookbook/scale_time.ipynb)
 *
 * @param name The name of the scale - used as the axis label or the legend title.
 *  If null, the default, the name of the scale is taken from the first mapping used for that aesthetic.
 * @param breaks A list of data values specifying the positions of ticks,
 *  or a dictionary which maps the tick labels to the breaks values.
 * @param labels A list of labels on ticks, or a dictionary which maps the breaks values to the tick labels.
 * @param lablim The maximum label length (in characters) before trimming is applied.
 * @param limits A numeric vector of length two providing limits of the scale.
 * @param expand A numeric vector of length two giving multiplicative and additive expansion constants.
 *  The vector size == 1 => only multiplicative expand (and additive expand by default).
 *  Defaults: multiplicative = 0.05, additive = 0.
 * @param naValue Missing values will be replaced with this value.
 * @param position The position of the axis:
 *  - "left", "right" or "both" for y-axis;
 *  - "top", "bottom" or "both" for x-axis.
 *
 */
fun scaleXTime(
    name: String? = null,
    breaks: Any? = null,       // ToDo: List<Number>?
    labels: Any? = null,
    lablim: Int? = null,
    limits: Pair<Any?, Any?>? = null,
    expand: List<Number>? = null,
    naValue: Any? = null,
    position: String? = null
): Scale {
    checkScaleExpand(expand)
    return Scale(
        aesthetic = Aes.X,
        name = name,
        breaks = breaks,
        labels = labels,
        lablim = lablim,
        limits = limits,
        expand = expand,
        naValue = naValue,
        position = position,
        otherOptions = Options(
            mapOf(
                TIME to true
            )
        )
    )
}

/**
 * Position scale y for data representing "time delta" values expressed in milliseconds.
 *
 * ## Examples
 *
 * - [scale_time.ipynb](https://nbviewer.org/github/JetBrains/lets-plot-docs/blob/master/source/kotlin_examples/cookbook/scale_time.ipynb)
 *
 * @param name The name of the scale - used as the axis label or the legend title.
 *  If null, the default, the name of the scale is taken from the first mapping used for that aesthetic.
 * @param breaks A list of data values specifying the positions of ticks,
 *  or a dictionary which maps the tick labels to the breaks values.
 * @param labels A list of labels on ticks, or a dictionary which maps the breaks values to the tick labels.
 * @param lablim The maximum label length (in characters) before trimming is applied.
 * @param limits A numeric vector of length two providing limits of the scale.
 * @param expand A numeric vector of length two giving multiplicative and additive expansion constants.
 *  The vector size == 1 => only multiplicative expand (and additive expand by default).
 *  Defaults: multiplicative = 0.05, additive = 0.
 * @param naValue Missing values will be replaced with this value.
 * @param position The position of the axis:
 *  - "left", "right" or "both" for y-axis;
 *  - "top", "bottom" or "both" for x-axis.
 *
 */
fun scaleYTime(
    name: String? = null,
    breaks: Any? = null,
    labels: Any? = null,
    lablim: Int? = null,
    limits: Pair<Any?, Any?>? = null,
    expand: List<Number>? = null,
    naValue: Any? = null,
    position: String? = null
): Scale {
    checkScaleExpand(expand)
    return Scale(
        aesthetic = Aes.Y,
        name = name,
        breaks = breaks,
        labels = labels,
        lablim = lablim,
        limits = limits,
        expand = expand,
        naValue = naValue,
        position = position,
        otherOptions = Options(
            mapOf(
                TIME to true
            )
        )
    )
}