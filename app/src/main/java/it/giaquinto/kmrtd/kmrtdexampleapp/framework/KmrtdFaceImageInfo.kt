package it.giaquinto.kmrtd.kmrtdexampleapp.framework

import kmrtd.lds.iso19794.support.FaceImageType
import kmrtd.lds.iso19794.support.Feature
import kmrtd.lds.iso19794.support.ImageColorSpace
import kmrtd.lds.iso19794.support.ImageDataType
import kmrtd.lds.iso19794.support.SourceType
import kotlinx.serialization.Serializable

@Serializable
data class KmrtdFaceImageInfo(
    val imageDataType: ImageDataType?,
    val imageColorSpace: ImageColorSpace?,
    val faceImageType: FaceImageType?,
    val sourceType: SourceType?,
    val featureMask: Set<Feature>?,
)