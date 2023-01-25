#!/usr/bin/env kscript
// Generate colors for waybar from Material You tokens.css
// kscript grepcolors.main.kts path_to_tokens.css -light / -dark (optional)

@file:DependsOn("com.sealwu:kscript-tools:1.0.2")

import runCommand
import evalBash
import java.io.File
import kotlin.system.exitProcess

// TODO: Use native methods, Nuke "@file:DependsOn("com.sealwu:kscript-tools:1.0.2")"
var isDarkTheme = true

// get user home path
val home: String = System.getProperty("user.home")

val themeArg = try {
    args[1]
} catch (e: ArrayIndexOutOfBoundsException) {
    ""
}

val pathArg = try {
    args[0]
} catch (e: ArrayIndexOutOfBoundsException) {
    println("tokens.css path argument missing..")
    exitProcess(0)
}

pathArg.let {
    val file = File(it)
    if (!file.exists()) {
        println("File missing!..\ncould not locate tokens.css")
        exitProcess(0)
    }
}

themeArg.let {

    when (it) {
        "-dark" -> isDarkTheme = true
        "-light" -> isDarkTheme = false
        else -> {
            if (it.isNotBlank()) {
                println("Invalid Argument\nUSE \n-dark  for dark colorscheme(default)\n-light for light colorscheme")
                exitProcess(0)
            }
        }
    }
}

val outPutPath = "~/.config/waybar/styling/themes/Madness.css"

// assign dunstrc
val dunstrc = File("$home/.config/dunst/dunstrc")
val dunstrcLines = dunstrc.bufferedReader().readLines() as MutableList

// this will hold the token and its map to value
data class Mapper(
    val token: String,
    val map: String
)

// Credits-chatGpt-xd
val darkColors = listOf(
    Mapper("md-sys-color-primary-dark", "primary"),
    Mapper("md-sys-color-primary-container-dark", "primary-container"),
    Mapper("md-sys-color-on-primary-dark", "on-primary"),
    Mapper("md-sys-color-on-primary-container-dark", "on-primary-container"),
    Mapper("md-sys-color-secondary-dark", "secondary"),
    Mapper("md-sys-color-secondary-container-dark", "secondary-container"),
    Mapper("md-sys-color-on-secondary-dark", "on-secondary"),
    Mapper("md-sys-color-on-secondary-container-dark", "on-secondary-container"),
    Mapper("md-sys-color-tertiary-dark", "tertiary"),
    Mapper("md-sys-color-tertiary-container-dark", "tertiary-container"),
    Mapper("md-sys-color-on-tertiary-dark", "on-tertiary"),
    Mapper("md-sys-color-on-tertiary-container-dark", "on-tertiary-container"),
    Mapper("md-sys-color-error-dark", "error"),
    Mapper("md-sys-color-error-container-dark", "error-container"),
    Mapper("md-sys-color-on-error-dark", "on-error"),
    Mapper("md-sys-color-on-error-container-dark", "on-error-container"),
    Mapper("md-sys-color-outline-dark", "outline"),
    Mapper("md-sys-color-background-dark", "background"),
    Mapper("md-sys-color-on-background-dark", "on-background"),
    Mapper("md-sys-color-surface-dark", "surface"),
    Mapper("md-sys-color-on-surface-dark", "on-surface"),
    Mapper("md-sys-color-surface-variant-dark", "surface-variant"),
    Mapper("md-sys-color-on-surface-variant-dark", "on-surface-variant"),
    Mapper("md-sys-color-inverse-surface-dark", "inverse-surface"),
    Mapper("md-sys-color-inverse-on-surface-dark", "inverse-on-surface"),
    Mapper("md-sys-color-inverse-primary-dark", "inverse-primary"),
    Mapper("md-sys-color-shadow-dark", "shadow"),
    Mapper("md-sys-color-surface-tint-dark", "surface-tint"),
    Mapper("md-sys-color-outline-variant-dark", "outline-variant"),
    Mapper("md-sys-color-scrim-dark", "scrim")
)

val lightColors = listOf(
    Mapper("md-sys-color-primary-light", "primary"),
    Mapper("md-sys-color-primary-container-light", "primary-container"),
    Mapper("md-sys-color-on-primary-light", "on-primary"),
    Mapper("md-sys-color-on-primary-container-light", "on-primary-container"),
    Mapper("md-sys-color-secondary-light", "secondary"),
    Mapper("md-sys-color-on-secondary-light", "on-secondary"),
    Mapper("md-sys-color-secondary-container-light", "secondary-container"),
    Mapper("md-sys-color-on-secondary-container-light", "on-secondary-container"),
    Mapper("md-sys-color-tertiary-light", "tertiary"),
    Mapper("md-sys-color-on-tertiary-light", "on-tertiary"),
    Mapper("md-sys-color-tertiary-container-light", "tertiary-container"),
    Mapper("md-sys-color-on-tertiary-container-light", "on-tertiary-container"),
    Mapper("md-sys-color-error-light", "error"),
    Mapper("md-sys-color-error-container-light", "error-container"),
    Mapper("md-sys-color-on-error-light", "on-error"),
    Mapper("md-sys-color-on-error-container-light", "on-error-container"),
    Mapper("md-sys-color-outline-light", "outline"),
    Mapper("md-sys-color-background-light", "background"),
    Mapper("md-sys-color-on-background-light", "on-background"),
    Mapper("md-sys-color-surface-light", "surface"),
    Mapper("md-sys-color-on-surface-light", "on-surface"),
    Mapper("md-sys-color-surface-variant-light", "surface-variant"),
    Mapper("md-sys-color-on-surface-variant-light", "on-surface-variant"),
    Mapper("md-sys-color-inverse-surface-light", "inverse-surface"),
    Mapper("md-sys-color-inverse-on-surface-light", "inverse-on-surface"),
    Mapper("md-sys-color-inverse-primary-light", "inverse-primary"),
    Mapper("md-sys-color-shadow-light", "shadow"),
    Mapper("md-sys-color-surface-tint-light", "surface-tint"),
    Mapper("md-sys-color-outline-variant-light", "outline-variant"),
    Mapper("md-sys-color-scrim-light", "scrim")
)

val theme = if (isDarkTheme) darkColors else lightColors

convert()

fun convert() {
    "echo \" \" > $outPutPath".runCommand()
    println("${theme.size}")
    theme.forEach { it ->
        try {
            val hexValue = "cat $pathArg | grep -oP -- \"${it.token}: \\K[^\\s]+\"".evalBash().getOrThrow()
            "echo \"@define-color ${it.map} $hexValue\" | tee -a $outPutPath".runCommand()
        } catch (exception: Exception) {
            println("AH F..EXCEPTION\n\n\n$exception")
            exitProcess(0)
        }
    }

    "echo \"@define-color green #a6da95;\" | tee -a $outPutPath".runCommand()
    "echo \"@define-color red #ff6961;\" | tee -a $outPutPath".runCommand()

    "killall -SIGUSR2 waybar".runCommand()

    dunst()
}

// Dunstrc Conversion

fun dunst() {
// waybar generated theme
    val madnesscss = File("$home/.config/waybar/styling/themes/Madness.css")

    if (!madnesscss.exists() || !dunstrc.exists()) {
        "Please check both dunstrc and madness.css exist in the proper dir!!".printError()
        exitProcess(0)
    }

    println("working on files...")

    val themeLines = madnesscss.readLines()

    val listOfTypes =
        listOf(
            DunstType(
                type = "urgency_low",
                background = DunstMapper("background", null),
                foreground = DunstMapper("on-background", null),
                frame = DunstMapper("primary", null),
                highlight = DunstMapper("surface-tint", null)
            ),
            DunstType(
                type = "urgency_normal",
                background = DunstMapper("background", null),
                foreground = DunstMapper("on-background", null),
                frame = DunstMapper("primary", null),
                highlight = DunstMapper("surface-tint", null)
            ),
            DunstType(
                type = "urgency_critical",
                background = DunstMapper("background", null),
                foreground = DunstMapper("on-background", null),
                frame = DunstMapper("red", null),
                highlight = DunstMapper("surface-tint", null)
            ),
            DunstType(
                type = "backlight",
                highlight = DunstMapper("surface-tint", null)
            ),
            DunstType(
                type = "volume",
                highlight = DunstMapper("surface-tint", null)
            )
        )

    //TODO: Fix Duplicate attribute restriction
    println("collecting colors from css")
    listOfTypes.forEach { item ->
        themeLines.forEach { line ->
            if (line.isNotEmpty()) {
                when {
                    line.contains(" ${item.background?.color} ") -> {
                        item.background =
                            item.background?.copy(hex = regexHexString(line, item.background!!.color))
                    }
                    line.contains(" ${item.foreground?.color} ") -> {
                        item.foreground =
                            item.foreground?.copy(hex = regexHexString(line, item.foreground!!.color))
                    }
                    line.contains(" ${item.frame?.color} ") -> {
                        item.frame = item.frame?.copy(hex = regexHexString(line, item.frame!!.color))
                    }
                    line.contains(" ${item.highlight?.color} ") -> {
                        item.highlight =
                            item.highlight?.copy(hex = regexHexString(line, item.highlight!!.color))
                    }
                    else -> {
                    }
                }
            }
        }
    }

    println("appending colors to dunstrc...")
    listOfTypes.forEach { item ->
        var inSection = false

        dunstrcLines.map { currentLine ->
            when {
                currentLine.startsWith("[${item.type}]") -> {
                    inSection = true
                    currentLine
                }

                inSection && currentLine.startsWith("background =") -> {
                    dunstrcLines[index(currentLine)] = "background = \"${item.background?.hex}\""
                }

                inSection && currentLine.startsWith("foreground =") -> {
                    dunstrcLines[index(currentLine)] = "foreground = \"${item.foreground?.hex}\""
                }

                inSection && currentLine.startsWith("frame_color =") -> {
                    dunstrcLines[index(currentLine)] = "frame_color = \"${item.frame?.hex}\""
                }

                inSection && currentLine.startsWith("highlight =") -> {
                    dunstrcLines[index(currentLine)] = "highlight = \"${item.highlight?.hex}\""
                }

                inSection && currentLine.startsWith("[") -> {
                    inSection = false
                    currentLine
                }

                else -> currentLine
            }
        }

        dunstrc.bufferedWriter().use { out ->
            dunstrcLines.forEach { line ->
                out.write("$line\n")
            }
        }
    }

    ProcessBuilder("kill", "dunst").start()

    println("Script Finished")
}

data class DunstType(
    val type: String,
    var background: DunstMapper? = null,
    var foreground: DunstMapper? = null,
    var frame: DunstMapper? = null,
    var highlight: DunstMapper? = null
)

data class DunstMapper(val color: String, var hex: String? = null)

fun index(line: String): Int {
    return dunstrcLines.indexOf(line)
}

fun regexHexString(line: String, key: String): String {
    return line.replace("@define-color $key ", "").replace(";", "")
}

fun String.printError() {
    println("\u001B[31m$this\u001B[0m")
}
