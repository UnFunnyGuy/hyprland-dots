#!/usr/bin/env kscript
// Generate colors for waybar from Material You tokens.css
// kscript grepcolors.kts path_to_tokens.css -light / -dark (optional)

@file:DependsOn("com.sealwu:kscript-tools:1.0.2")

import runCommand
import evalBash
import java.io.File
import kotlin.system.exitProcess


var isDarkTheme = true

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

pathArg?.let {
    val file = File(it)
    if (!file.exists()) {
        println("File missing!..\ncould not locate tokens.css")
        exitProcess(0)
    }
}

themeArg?.let {

    when (it) {
        "-dark" -> isDarkTheme = true
        "-light" -> isDarkTheme = false
        else -> {
            if (!it.isNullOrBlank()) {
                println("Inavlid Argument\nUSE \n-dark  for dark colorscheme(default)\n-light for light colorscheme")
                exitProcess(0)
            }
        }
    }
} ?: { isDarkTheme = true }

val outPutPath = "~/.config/waybar/styling/themes/Madness.css"


// this will hold the token and its mapto value
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

fun convert() {
    "echo \" \" > $outPutPath".runCommand()
    println("${theme?.size}")
    theme.forEach { it ->
        try {
            val hexValue = "cat $pathArg | grep -oP -- \"${it.token}: \\K[^\\s]+\"".evalBash().getOrThrow()
            val append = "echo \"@define-color ${it.map} $hexValue\" | tee -a $outPutPath".runCommand()
        } catch (exception: Exception) {
            println("AH F..EXCEPTION\n\n\n$exception")
            exitProcess(0)
        }
    }

    "echo \"@define-color green #a6da95;\" | tee -a $outPutPath".runCommand()
    "echo \"@define-color red #ff6961;\" | tee -a $outPutPath".runCommand()

    "killall -SIGUSR2 waybar".runCommand()
}

convert()
