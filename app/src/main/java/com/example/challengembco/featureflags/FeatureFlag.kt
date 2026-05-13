package com.example.challengembco.featureflags

data class RemoteConfigFlag<T>(
    val key: String,
    val defaultValue: T,
    val valueType: RemoteConfigValueType,
    val description: String,
    val tags: List<String> = emptyList()
)

enum class RemoteConfigValueType { BOOLEAN, STRING, NUMBER, JSON }

object BankRemoteFlags {

    val TRANSFERENCIAS_V2 = RemoteConfigFlag(
        key = "transferencias_v2_enabled",
        defaultValue = false,
        valueType = RemoteConfigValueType.BOOLEAN,
        description = "Activa el flujo de transferencias v2 con nueva UI",
        tags = listOf("transferencias", "ui")
    )

    val BIOMETRIA_ENABLED = RemoteConfigFlag(
        key = "biometria_enabled",
        defaultValue = true,
        valueType = RemoteConfigValueType.BOOLEAN,
        description = "Habilita autenticación biométrica en login",
        tags = listOf("auth", "seguridad")
    )

    val MODO_MANTENIMIENTO = RemoteConfigFlag(
        key = "modo_mantenimiento",
        defaultValue = false,
        valueType = RemoteConfigValueType.BOOLEAN,
        description = "Muestra pantalla de mantenimiento y bloquea operaciones",
        tags = listOf("ops", "critico")
    )

    val MIN_APP_VERSION = RemoteConfigFlag(
        key = "min_app_version",
        defaultValue = "2.0.0",
        valueType = RemoteConfigValueType.STRING,
        description = "Versión mínima requerida para usar la app",
        tags = listOf("versiones", "critico")
    )

    val MAX_MONTO_TRANSFERENCIA = RemoteConfigFlag(
        key = "max_monto_transferencia",
        defaultValue = 50000L,
        valueType = RemoteConfigValueType.NUMBER,
        description = "Límite máximo en pesos para transferencias",
        tags = listOf("transferencias", "limites")
    )

    val LIMITE_RETIRO_ATM = RemoteConfigFlag(
        key = "limite_retiro_atm",
        defaultValue = 10000L,
        valueType = RemoteConfigValueType.NUMBER,
        description = "Límite diario de retiro en ATM",
        tags = listOf("atm", "limites")
    )

    val ALL: List<RemoteConfigFlag<*>> = listOf(
        TRANSFERENCIAS_V2,
        BIOMETRIA_ENABLED,
        MODO_MANTENIMIENTO,
        MIN_APP_VERSION,
        MAX_MONTO_TRANSFERENCIA,
        LIMITE_RETIRO_ATM
    )
}