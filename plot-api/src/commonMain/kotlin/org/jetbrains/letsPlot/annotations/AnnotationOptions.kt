/*
 * Copyright (c) 2022. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package org.jetbrains.letsPlot.annotations

import org.jetbrains.letsPlot.core.spec.Option

typealias AnnotationOptions = layerLabels


/**
 * Configure annotations for geometry layers.
 *
 * Annotations are currently supported for bar, pie, and crossbar geometry
 * layers. This class provides methods to customize the appearance and
 * content of text labels displayed on these geometries.
 *
 * ## Notes
 *
 * By default, annotation text color is automatically selected for optimal
 * contrast: white text appears on darker filled geometries, and black text
 * appears on lighter filled geometries.
 *
 * The text color can be manually specified using:
 * ``theme(labelText=elementText(color=...))``
 *
 * Alternatively, the ``inheritColor()`` method can be used to override both
 * automatic and manual color settings, making the annotation text use the
 * geometry's ``color`` aesthetic instead.
 *
 * ## Examples
 *
 * - [annotations_for_pie.ipynb](https://nbviewer.org/github/JetBrains/lets-plot-docs/blob/master/source/kotlin_examples/cookbook/annotations_for_pie.ipynb)
 *
 * ```kotlin
 * val data = mapOf("name" to listOf("a", "b", "c", "d", "b"), "value" to listOf(40, 90, 10, 50, 20))
 * letsPlot(data) +
 *      geomPie(
 *          size = 15, hole = 0.2,
 *          labels = layerLabels("..proppct..").format("..proppct..", "{.1f}%").size(15)
 *      ) { fill = asDiscrete("name", orderBy = "..count.."); weight = "value" }
 * ```
 *
 * @param variables Variable names to place in the annotation with default formatting.
 *  Useful for specifying the annotation content quickly, instead of
 *  configuring it via the `line()` method.
 *
 */
@Suppress("ClassName")
class layerLabels(vararg variables: String) {

    private val parameters = HashMap<String, Any>().apply {
        variables.toList().let {
            if (it.isNotEmpty()) {
                this[VARIABLES] = it
            }
        }
    }

    internal val options: Any
        get() = parameters

    private constructor(other: layerLabels) : this() {
        this.parameters.putAll(other.parameters)
    }

    private fun addListOption(key: String, value: Any): layerLabels {
        val newLabels = layerLabels(this)
        val newOptions = newLabels.parameters.getOrPut(key) { mutableListOf<Any>() }
        @Suppress("UNCHECKED_CAST")
        (newOptions as MutableList<Any>).add(value)
        return newLabels
    }

    private fun setOption(key: String, value: Any): layerLabels {
        val newTooltips = layerLabels(this)
        newTooltips.parameters[key] = value
        return newTooltips
    }

    /**
     * Defines the format for displaying the value.
     * The format will be applied to the corresponding value specified in the line template.
     *
     * @param field Aesthetic or variable name to apply the format to.
     *  The field name starts with a '^' prefix for aesthetics, variable names are specified without prefix or with a '@' prefix.
     * @param format A number format ('1.f'), a string template ('{.1f}') or a date/time format ('%d.%m.%y').
     *  The numeric format for non-numeric value will be ignored.
     *
     *  If you need to include a brace character in the literal text, it can be escaped 
     *  by doubling: {{ and }}, e.g.,
     *  - .format('^color', '{{ {.1f} }}') -> "{ 17.0 }"
     *  - .format('model', '{} {{text}}') -> "mustang {text}"
     *
     *  Aes and var formats are not interchangeable, i.e. var format will not be applied to 
     *  aes, mapped to this variable.
     */
    fun format(field: String, format: String): layerLabels {
        return addListOption(
            FORMATS, mapOf(
                FIELD to field,
                FORMAT to format
            )
        )
    }

    /**
     * Specifies the string template to use in the annotation.
     *
     * @param template A line template to show in the annotation.
     *  Variables and aesthetics can be accessed via a special syntax:
     *  - ^color for aes
     *  - @year for variable
     *  - @{number of cylinders} for variable with spaces in the name
     *  - @{square m^2} for variable with spaces and '^' symbol in the name
     *  - @nameWith^ for the variable with '^' symbol in its name
     *  
     *  A '^' symbol can be escaped with a backslash, a brace character in the 
     *  literal text - by doubling:
     *  - .line("text") -> "text"
     *  - .line("{{text}}") -> "{text}"
     *  - .line("@model") -> "mustang"
     *  - .line("{{@model}}") -> "{mustang}"
     */
    fun line(template: String): layerLabels {
        return addListOption(LINES, template)
    }

    /**
     * Specifies a text size in the annotation.

     * @param value Text size in the annotation.
     */
    fun size(value: Number): layerLabels {
        return setOption(ANNOTATION_SIZE, value)
    }


    /**
     * Use the layer's color for the annotation text.
     *
     * When enabled, the annotation text will inherit the color from the
     * layer it's associated with, rather than using a default or
     * explicitly set color.
     */
    fun inheritColor(): layerLabels {
        return setOption(USE_LAYER_COLOR, true)
    }

    private companion object {
        private const val VARIABLES = Option.LinesSpec.VARIABLES
        private const val FORMATS = Option.LinesSpec.FORMATS
        private const val FIELD = Option.LinesSpec.Format.FIELD
        private const val FORMAT = Option.LinesSpec.Format.FORMAT
        private const val LINES = Option.LinesSpec.LINES
        private const val ANNOTATION_SIZE = Option.AnnotationSpec.ANNOTATION_SIZE
        private const val USE_LAYER_COLOR = Option.AnnotationSpec.USE_LAYER_COLOR
    }
}