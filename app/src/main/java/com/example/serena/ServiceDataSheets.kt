
package com.example.serena
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
@Serializable
data class ILoginResponseApi(
    val accessToken: String,
    val userId: String
)

@Serializable
data class ILoginRequestApi(
    val email: String,
    val password: String
)

@Serializable
data class IMusicCardData(
    val id: String?,
    val title: String?,
    val artist: String?,
    val album: String?,
    val release_year: Int,
    val cover_image: String?,
    val preview_link: String?
)

@Serializable
data class IDeviceSerenBox(
    val id: String,
    val credentials: String?,
    val ip_address: String?,
    val name: String,
    val userId: String?,
    val image_name: String?,
    val added: String?,
    val slotAId: String?,
    val slotBId: String?,
)

@Serializable
data class IPlaceCardData(
    val id: String?,
    val name: String?,
    val price_idr: Int,
    val description: String?,
    val stock: Int,
    val image_name: String?,
    val type: String?
)


@Serializable
data class IDeletedAccountData(
    val id: String?,
    val username: String?,
    val email: String?,
)

@Serializable
data class IEmotionDetectorResData(
    val angry: Float,
    val disgust: Float,
    val fear: Float,
    val happy: Float,
    val neutral: Float,
    val sad: Float,
    val surprise: Float,
)

@Serializable
data class ISerenProductResData(
    val id: String,
    val name: String,
    val price_idr: Int,
    val description: String,
    val stock: Int,
    val image_name: String,
    val type: String,
)


@Serializable
data class IUserEmotionsEnergeticResData(
    val energetic: Float,
    val anger: Float,
    val fear: Float,
    val surprise: Float,
    val total: Float,
)

@Serializable
data class IUserEmotionsRelaxResData(
    val disgust: Float,
    val joy: Float,
    val neutral: Float,
    val sadness: Float,
    val total: Float,
)

@Serializable
data class IUserEmotionsResData(
    val energetic: IUserEmotionsEnergeticResData,
    val relax: IUserEmotionsRelaxResData,
    val id: String,
    val userId: String,
    val created_time: String,
    val user_photo: String,
    val serenBoxSessionId: String?,
)
@Serializable
data class IResponseApiError(
    val message: String,
    val status: Int,
)

@Serializable
data class IResponCreatedSerenBoxSlot (
    val id: String,
    val name: String,
    val slot: String,
    val label: String,
    val capacity_ml: Int,
    val current_capacity_ml: Int,
    val is_active: Boolean,
    val image_url: String,
)

@Serializable
data class IResponseUserDetail (
    val id: String,
    val username: String,
    val email: String,
    val image_name: String,
)


@Serializable
data class IResponCreatedSerenBox(
    val id: String,
    val credentials: String,
    val ip_address: String?,
    val name: String,
    val userId: String,
    val image_name: String,
    val added: String,
    val slotAId: String,
    val slotBId: String,
    val slotA: IResponCreatedSerenBoxSlot,
    val slotB: IResponCreatedSerenBoxSlot,
)

@Serializable
data class IUserTestData(
    val username: String,
    val email: String,
    val password: String,
)

@Serializable
data class IAccordionData(
    val label: String,
    val value: String,
)


@Serializable
data class ISerenBoxConfiguration(
    val duration: Int,
    val detection_mode: String,
    val diffusion_option: String,
)








