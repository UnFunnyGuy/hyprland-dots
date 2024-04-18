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
    Mapper("md-sys-color-primary", "primary"),
    Mapper("md-sys-color-primary-container", "primary-container"),
    Mapper("md-sys-color-on-primary", "on-primary"),
    Mapper("md-sys-color-on-primary-container", "on-primary-container"),
    Mapper("md-sys-color-secondary", "secondary"),
    Mapper("md-sys-color-secondary-container", "secondary-container"),
    Mapper("md-sys-color-on-secondary", "on-secondary"),
    Mapper("md-sys-color-on-secondary-container", "on-secondary-container"),
    Mapper("md-sys-color-tertiary", "tertiary"),
    Mapper("md-sys-color-tertiary-container", "tertiary-container"),
    Mapper("md-sys-color-on-tertiary", "on-tertiary"),
    Mapper("md-sys-color-on-tertiary-container", "on-tertiary-container"),
    Mapper("md-sys-color-error", "error"),
    Mapper("md-sys-color-error-container", "error-container"),
    Mapper("md-sys-color-on-error", "on-error"),
    Mapper("md-sys-color-on-error-container", "on-error-container"),
    Mapper("md-sys-color-outline", "outline"),
    Mapper("md-sys-color-background", "background"),
    Mapper("md-sys-color-on-background", "on-background"),
    Mapper("md-sys-color-surface", "surface"),
    Mapper("md-sys-color-on-surface", "on-surface"),
    Mapper("md-sys-color-surface-variant", "surface-variant"),
    Mapper("md-sys-color-on-surface-variant", "on-surface-variant"),
    Mapper("md-sys-color-inverse-surface", "inverse-surface"),
    Mapper("md-sys-color-inverse-on-surface", "inverse-on-surface"),
    Mapper("md-sys-color-inverse-primary", "inverse-primary"),
    Mapper("md-sys-color-shadow", "shadow"),
    Mapper("md-sys-color-surface-tint", "surface-tint"),
    Mapper("md-sys-color-outline-variant", "outline-variant"),
    Mapper("md-sys-color-scrim", "scrim")
)


val theme = darkColors

convert()

fun convert() {
    "echo \" \" > $outPutPath".runCommand()
    
    theme.forEach { it ->
        try {
            val rgbRegex = Regex("rgb\\((\\d+) (\\d+) (\\d+)\\)")
        
            val rgbMatchResult = rgbRegex.find("cat $pathArg | grep -oP -- \"${it.token}: \\Krgb\\([^\\)]+\\)\"".evalBash().getOrThrow())
            if (rgbMatchResult != null) {
                val (red, green, blue) = rgbMatchResult.destructured
                val hexValue = String.format("#%02x%02x%02x", red.toInt(), green.toInt(), blue.toInt())
                "echo \"@define-color ${it.map} $hexValue;\" | tee -a $outPutPath".runCommand()
            } else {
                println("RGB value not found")
            }
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
