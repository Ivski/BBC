package rocks.ivski.bbc.data.dto

import kotlinx.serialization.SerialName
import rocks.ivski.bbc.utils.NA
import java.io.Serializable

@kotlinx.serialization.Serializable
data class Character(
    @SerialName("char_id")
    val charId: Int? = null,
    val name: String = NA,
    val birthday: String = NA,
    val occupation: List<String>? = null,
    @SerialName("img")
    val imageUrl: String? = null,
    val status: String = NA,
    val nickname: String = NA,
    val appearance: List<Int>? = null,
    val portrayed: String = NA,
    val category: String = NA,
    @SerialName("better_call_saul_appearance")
    val betterCallSaulAppearance: List<Int>? = null
) : Serializable