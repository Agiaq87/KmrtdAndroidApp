package it.giaquinto.kmrtd.kmrtdexampleapp.framework


import kmrtd.lds.iso19794.FaceImageInfo
import kotlinx.serialization.Serializable

@Serializable
data class KmrtdFaceImageInfo(
    val imageDataType: FaceImageInfo.ImageDataType?,
    val imageColorSpace: FaceImageInfo.ImageColorSpace?,
    val faceImageType: FaceImageInfo.FaceImageType?,
    val sourceType: FaceImageInfo.SourceType?,
    val featureMask: Set<FaceImageInfo.Features>?,
)