// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // ADDED: hacemos disponible el plugin de Hilt para los módulos que lo necesiten (app)
    alias(libs.plugins.hilt.android) apply false

    // ADDED: plugin de kapt necesario para los procesadores de anotaciones (Hilt, Room)
    alias(libs.plugins.kotlin.kapt) apply false
}