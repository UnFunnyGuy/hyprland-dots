#!/usr/bin/env kscript
//Generate colors for waybar from Material You tokens.css

@file:DependsOn("com.sealwu:kscript-tools:1.0.2")

import runCommand
import evalBash
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess




val outPutPath = "~/.config/waybar/styling/themes/Madness.css"

val file = File("tokens.css")
if (!file.exists()) {
    println("File missing!..\ncould not locate tokens.css")
    exitProcess(0)
}

//this will hold the token and its mapto value
data class Mapper (
    val token: String,
    val map: String,
)

//Credits-chatGpt-xd
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

convert()




fun convert(){
    "echo \" \" > $outPutPath".runCommand()

    darkColors.forEach { it ->
        try {
            val hexValue = "cat tokens.css | grep -oP -- \"${it.token}: \\K[^\\s]+\"".evalBash().getOrThrow()
            val append = "echo \"@define-color ${it.map} $hexValue\" | tee -a $outPutPath".runCommand()
        } catch (exception: Exception){
            println("AH F..EXCEPTION\n\n\n$exception")
        }
    }

    "echo \"@define-color green #a6da95;\" | tee -a $outPutPath".runCommand()
    "echo \"@define-color red #ff6961;\" | tee -a $outPutPath".runCommand()

}


