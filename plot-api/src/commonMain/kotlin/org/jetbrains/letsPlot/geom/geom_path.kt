/*
 * Copyright (c) 2021. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package org.jetbrains.letsPlot.geom

import org.jetbrains.letsPlot.Geom.path
import org.jetbrains.letsPlot.Stat
import org.jetbrains.letsPlot.core.spec.Option
import org.jetbrains.letsPlot.intern.Layer
import org.jetbrains.letsPlot.intern.Options
import org.jetbrains.letsPlot.intern.layer.*
import org.jetbrains.letsPlot.intern.layer.geom.PathAesthetics
import org.jetbrains.letsPlot.intern.layer.geom.PathMapping
import org.jetbrains.letsPlot.pos.positionIdentity
import org.jetbrains.letsPlot.spatial.SpatialDataset
import org.jetbrains.letsPlot.tooltips.TooltipOptions

@Suppress("ClassName")
/**
 * Connect observations in the order they appear in the data.
 *
 * ## Examples
 *
 * - [lines.ipynb](https://nbviewer.org/github/JetBrains/lets-plot-docs/blob/master/source/kotlin_examples/cookbook/lines.ipynb)
 *
 * @param data The data to be displayed in this layer. If null, the default, the data
 *  is inherited from the plot data as specified in the call to [letsPlot][org.jetbrains.letsPlot.letsPlot].
 * @param stat The statistical transformation to use on the data for this layer.
 *  Supported transformations: `Stat.identity`, `Stat.bin()`, `Stat.count()`, etc. see [Stat][org.jetbrains.letsPlot.Stat].
 * @param position Position adjustment: `positionIdentity`, `positionStack()`, `positionDodge()`, etc. see
 *  [Position](https://lets-plot.org/kotlin/-lets--plot--kotlin/org.jetbrains.letsPlot.pos/).
 * @param showLegend default = true.
 *  false - do not show legend for this layer.
 * @param inheritAes default = true.
 *  false - do not combine the layer aesthetic mappings with the plot shared mappings.
 * @param manualKey String or result of the call to the `layerKey()` function.
 *  The key to show in the manual legend. Specifies the text for the legend label or advanced settings using the `layerKey()` function.
 * @param sampling Result of the call to the `samplingXxx()` function.
 *  To prevent any sampling for this layer pass value `samplingNone`.
 *  For more info see [sampling.html](https://lets-plot.org/kotlin/sampling.html).
 * @param tooltips Result of the call to the `layerTooltips()` function.
 *  Specifies appearance, style and content.
 *  Set `tooltips = tooltipsNone` to hide tooltips from the layer.
 * @param map Data-structure containing series of planar shapes and, optionally, associates data series (for example:
 *  names of States and their boundaries).
 *
 *  Supported shapes: LineString and MultiLineString.
 *  All coordinates should be encoded as decimal degrees in WGS84 coordinate reference system.
 *
 *  Can be used with parameter `mapJoin` for joining data and map coordinates.
 * @param mapJoin Pair of Names or Pair of Lists of Names.
 *  Specifies column names to join the `data` and the `map` coordinates on.
 *  - Pair.first: column name or list of column names in the `data` dataframe.
 *  - Pair.second: column name or list of column names in the `map` dataframe.
 * @param useCRS By default, all coordinates are converted into degrees of longitude and latitude,
 *  and these map coordinates are projected onto the screen coordinates using Mercator projection.
 *  Specify useCRS = "provided" to keep the SpatialDataset's original coordinate reference system (CRS).
 * @param flat default = false.
 *  true - keeps a line straight (corresponding to a loxodrome in case of Mercator projection).
 *  false - allows a line to be reprojected, so it can become a curve.
 * @param x X-axis value.
 * @param y Y-axis value.
 * @param alpha Transparency level of a layer. Understands numbers between 0 and 1.
 * @param color Color of the geometry.
 *  For more info see: [aesthetics.html#color-and-fill](https://lets-plot.org/kotlin/aesthetics.html#color-and-fill).
 * @param linetype Type of the line.
 *  Accept codes or names (0 = "blank", 1 = "solid", 2 = "dashed", 3 = "dotted", 4 = "dotdash", 5 = "longdash", 6 = "twodash"),
 *  a hex string (up to 8 digits for dash-gap lengths),
 *  or a pattern `offset to listOf(dash, gap, ...)` / `listOf(dash, gap, ...)`.
 *  For more info see: [aesthetics.html#line-types](https://lets-plot.org/kotlin/aesthetics.html#line-types).
 * @param size Line width.
 * @param colorBy default = "color" ("fill", "color", "paint_a", "paint_b", "paint_c").
 *  Defines the color aesthetic for the geometry.
 * @param mapping Set of aesthetic mappings.
 *  Aesthetic mappings describe the way that variables in the data are mapped to plot "aesthetics".
 */
class geomPath(
    data: Map<*, *>? = null,
    stat: StatOptions = Stat.identity,
    position: PosOptions = positionIdentity,
    showLegend: Boolean = true,
    inheritAes: Boolean? = null,
    manualKey: Any? = null,
    sampling: SamplingOptions? = null,
    tooltips: TooltipOptions? = null,
    override val map: SpatialDataset? = null,
    override val mapJoin: Pair<Any, Any>? = null,
    override val useCRS: String? = null,
    private val flat: Boolean? = null,
    override val x: Number? = null,
    override val y: Number? = null,
    override val alpha: Number? = null,
    override val color: Any? = null,
    override val linetype: Any? = null,
    override val size: Number? = null,
    override val colorBy: String? = null,
    mapping: PathMapping.() -> Unit = {}

) : PathAesthetics,
    WithSpatialParameters,
    WithColorOption,
    Layer(
        mapping = PathMapping().apply(mapping).seal(),
        data = data,
        geom = path(),
        stat = stat,
        position = position,
        showLegend = showLegend,
        inheritAes = inheritAes,
        manualKey = manualKey,
        sampling = sampling,
        tooltips = tooltips
    ) {
    override fun seal(): Options {
        return super<PathAesthetics>.seal() +
                super<WithColorOption>.seal() +
                Options.of(
                    Option.Geom.Path.FLAT to flat
                )
    }
}


