/*
 * JMRTD - A Java API for accessing machine readable travel documents.
 *
 * Copyright (C) 2006 - 2025  The JMRTD team
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * $Id: FingerImageRepresentationBlock.java 1896 2025-04-18 21:39:56Z martijno $
 *
 * Based on ISO-IEC-39794-4-ed-1-v2. Disclaimer:
 * THE SCHEMA ON WHICH THIS SOFTWARE IS BASED IS PROVIDED BY THE COPYRIGHT
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THE CODE COMPONENTS, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

import kmrtd.ASN1Util
import kmrtd.cbeff.CBEFFInfo
import kmrtd.lds.ImageInfo
import kmrtd.lds.iso39794.FingerImageAnnotationBlock.Companion.decodeFingerImageAnnotationBlocks
import kmrtd.lds.iso39794.FingerImageSegmentationBlock.Companion.decodeFingerImageSegmentationBlocks
import kmrtd.lds.iso39794.QualityBlock.Companion.decodeQualityBlocks
import kmrtd.lds.iso39794.fingerimage.FingerImagePositionCode
import kmrtd.lds.iso39794.fingerimage.ImageDataFormatCode
import kmrtd.lds.iso39794.fingerimage.ImpressionCode
import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1OctetString
import org.bouncycastle.asn1.ASN1Sequence
import org.bouncycastle.asn1.ASN1VisibleString
import org.bouncycastle.asn1.DEROctetString
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.DERVisibleString
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.Objects

data class FingerImageRepresentationBlock(
    val position: FingerImagePositionCode?,
    val impression: ImpressionCode?,
    val imageDataFormat: ImageDataFormatCode?,
    val captureDateTimeBlock: DateTimeBlock?,
    val captureDeviceBlock: FingerImageCaptureDeviceBlock?,
    val qualityBlocks: List<QualityBlock>?,
    val spatialSamplingRateBlock: FingerImageSpatialSamplingRateBlock?,
    val isPositionComputedByCaptureSystem: Boolean?,
    /** INTEGER (0..359)  */
    val fingerRotation: Int?,
    val isImageRotatedToVertical: Boolean?,
    val isImageHasBeenLossilyCompressed: Boolean?,
    val segmentationBlocks: List<FingerImageSegmentationBlock>?,
    val annotationBlocks: List<FingerImageAnnotationBlock>?,
    val padDataBlock: PADDataBlock?,
    private val imageData: ByteArray,
    val commentBlocks: List<String>?,
    val vendorSpecificDataBlocks: List<ExtendedDataBlock>?,
) : Block(), ImageInfo {


    //  RepresentationBlock ::= SEQUENCE {
    //    position [0] Position,
    //    impression [1] Impression,
    //    imageDataFormat [2] ImageDataFormat,
    //    imageData [3] OCTET STRING,
    //    captureDateTimeBlock [4] CaptureDateTimeBlock OPTIONAL,
    //    captureDeviceBlock [5] CaptureDeviceBlock OPTIONAL,
    //    qualityBlocks [6] QualityBlocks OPTIONAL,
    //    spatialSamplingRateBlock [7] SpatialSamplingRateBlock OPTIONAL,
    //    positionComputedByCaptureSystem [8] BOOLEAN OPTIONAL,
    //    originalRotation [9] FingerRotation OPTIONAL,
    //    imageRotatedToVertical [10] BOOLEAN OPTIONAL,
    //    imageHasBeenLossilyCompressed [11] BOOLEAN OPTIONAL,
    //    segmentationBlocks [12] SegmentationBlocks OPTIONAL,
    //    annotationBlocks [13] AnnotationBlocks OPTIONAL,
    //    pADDataBlock [14] PADDataBlock OPTIONAL,
    //    commentBlocks [15] CommentBlocks OPTIONAL,
    //    vendorSpecificDataBlocks [16] VendorSpecificDataBlocks OPTIONAL,
    //    ...
    //  }
    internal constructor(asn1Encodable: ASN1Encodable) {
        requireNotNull(asn1Encodable) { "Cannot decode!" }
        val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)
        position = FingerImagePositionCode.fromCode(
            ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects[0])
        )
        impression = ImpressionCode.fromCode(
            ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects[1])
        )
        imageDataFormat = ImageDataFormatCode.fromCode(
            ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects[2])
        )
        imageData = (ASN1OctetString.getInstance(taggedObjects[3])).octets
        if (taggedObjects.containsKey(4)) {
            captureDateTimeBlock = DateTimeBlock.from(taggedObjects[4])
        }
        if (taggedObjects.containsKey(5)) {
            captureDeviceBlock = FingerImageCaptureDeviceBlock.from(taggedObjects[5])
        }
        if (taggedObjects.containsKey(6)) {
            qualityBlocks = decodeQualityBlocks(taggedObjects[6])
        }
        if (taggedObjects.containsKey(7)) {
            spatialSamplingRateBlock = FingerImageSpatialSamplingRateBlock(taggedObjects[7])
        }
        if (taggedObjects.containsKey(8)) {
            isPositionComputedByCaptureSystem = ASN1Util.decodeBoolean(taggedObjects[8])
        }
        if (taggedObjects.containsKey(9)) {
            fingerRotation = ASN1Util.decodeInt(taggedObjects[9])
        }
        if (taggedObjects.containsKey(10)) {
            isImageRotatedToVertical = ASN1Util.decodeBoolean(taggedObjects[10])
        }
        if (taggedObjects.containsKey(11)) {
            isImageHasBeenLossilyCompressed = ASN1Util.decodeBoolean(taggedObjects[11])
        }
        if (taggedObjects.containsKey(12)) {
            segmentationBlocks = decodeFingerImageSegmentationBlocks(taggedObjects.get(12))
        }
        if (taggedObjects.containsKey(13)) {
            annotationBlocks = decodeFingerImageAnnotationBlocks(taggedObjects[13])
        }
        if (taggedObjects.containsKey(14)) {
            padDataBlock = PADDataBlock.from(taggedObjects[14])
        }
        if (taggedObjects.containsKey(15)) {
            commentBlocks = decodeCommentBlocks(taggedObjects[15])
        }
        if (taggedObjects.containsKey(16)) {
            vendorSpecificDataBlocks =
                ExtendedDataBlock.decodeExtendedDataBlocks(taggedObjects[16]!!)
        }
    }

    /*fun geImageData(): ByteArray {
        return imageData
    }*/

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + imageData.contentHashCode()
        result = prime * result + Objects.hash(
            annotationBlocks,
            captureDateTimeBlock,
            captureDeviceBlock,
            commentBlocks,
            fingerRotation,
            imageDataFormat,
            impression,
            isImageHasBeenLossilyCompressed,
            isImageRotatedToVertical,
            isPositionComputedByCaptureSystem,
            padDataBlock,
            position,
            qualityBlocks,
            segmentationBlocks,
            spatialSamplingRateBlock,
            vendorSpecificDataBlocks
        )
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) {
            return true
        }
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }

        val other = obj as FingerImageRepresentationBlock
        return annotationBlocks == other.annotationBlocks
                && captureDateTimeBlock == other.captureDateTimeBlock
                && captureDeviceBlock == other.captureDeviceBlock
                && commentBlocks == other.commentBlocks && fingerRotation == other.fingerRotation
                && imageData.contentEquals(other.imageData) && imageDataFormat == other.imageDataFormat && impression == other.impression && isImageHasBeenLossilyCompressed == other.isImageHasBeenLossilyCompressed
                && isImageRotatedToVertical == other.isImageRotatedToVertical
                && isPositionComputedByCaptureSystem == other.isPositionComputedByCaptureSystem
                && padDataBlock == other.padDataBlock && position == other.position && qualityBlocks == other.qualityBlocks
                && segmentationBlocks == other.segmentationBlocks
                && spatialSamplingRateBlock == other.spatialSamplingRateBlock
                && vendorSpecificDataBlocks == other.vendorSpecificDataBlocks
    }

    /*override fun toString(): String {
        return ("FingerImageRepresentationBlock ["
                + "position: " + position
                + ", impression: " + impression
                + ", imageDataFormat: " + imageDataFormat
                + ", captureDateTimeBlock: " + captureDateTimeBlock
                + ", captureDeviceBlock: " + captureDeviceBlock
                + ", qualityBlocks: " + qualityBlocks
                + ", spatialSamplingRateBlock: " + spatialSamplingRateBlock
                + ", isPositionComputedByCaptureSystem: " + isPositionComputedByCaptureSystem
                + ", fingerRotation: " + fingerRotation
                + ", isImageRotatedToVertical: " + isImageRotatedToVertical
                + ", isImageHasBeenLossilyCompressed: " + isImageHasBeenLossilyCompressed
                + ", segmentationBlocks: " + segmentationBlocks
                + ", annotationBlocks: " + annotationBlocks
                + ", padDataBlock: " + padDataBlock
                + ", imageData: " + imageData.size
                + ", commentBlocks: " + commentBlocks
                + ", vendorSpecificDataBlocks: " + vendorSpecificDataBlocks
                + "]")
    }*/

    override fun getType(): Int {
        return ImageInfo.TYPE_FINGER
    }

    override fun getMimeType(): String? {
        if (imageDataFormat == null) {
            return "image/raw"
        }
        return imageDataFormat.mimeType
    }

    override fun getWidth(): Int {
        return 0
    }

    override fun getHeight(): Int {
        return 0
    }

    override fun getRecordLength(): Long {
        return 0
    }

    override fun getImageLength(): Int {
        return imageData.size
    }

    override fun getImageInputStream(): InputStream {
        return ByteArrayInputStream(imageData)
    }

    /* PACKAGE */
    val biometricSubtype: Int
        /**
         * Returns the biometric sub-type.
         * 
         * @return the ICAO/CBEFF (BHT) biometric sub-type
         */
        get() = toBiometricSubtype(position)

    override val aSN1Object: ASN1Encodable
        get() {
            val taggedObjects: MutableMap<Int?, ASN1Encodable?> =
                HashMap<Int?, ASN1Encodable?>()
            taggedObjects[0] =
                ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(position!!.code)
            taggedObjects[1] =
                ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(impression!!.code)
            taggedObjects[2] =
                ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(imageDataFormat!!.code)
            taggedObjects[3] = DEROctetString(imageData)
            if (captureDateTimeBlock != null) {
                taggedObjects[4] = captureDateTimeBlock.aSN1Object
            }
            if (captureDeviceBlock != null) {
                taggedObjects[5] = captureDeviceBlock.aSN1Object
            }
            if (qualityBlocks != null) {
                taggedObjects[6] = ISO39794Util.encodeBlocks(qualityBlocks)
            }
            if (spatialSamplingRateBlock != null) {
                taggedObjects[7] = spatialSamplingRateBlock.aSN1Object
            }
            if (isPositionComputedByCaptureSystem != null) {
                taggedObjects[8] = ASN1Util.encodeBoolean(isPositionComputedByCaptureSystem)
            }
            if (fingerRotation != null) {
                taggedObjects[9] = ASN1Util.encodeInt(fingerRotation)
            }
            if (isImageRotatedToVertical != null) {
                taggedObjects[10] = ASN1Util.encodeBoolean(isImageRotatedToVertical)
            }
            if (isImageHasBeenLossilyCompressed != null) {
                taggedObjects[11] = ASN1Util.encodeBoolean(isImageHasBeenLossilyCompressed)
            }
            if (segmentationBlocks != null) {
                taggedObjects[12] = ISO39794Util.encodeBlocks(segmentationBlocks)
            }
            if (annotationBlocks != null) {
                taggedObjects[13] = ISO39794Util.encodeBlocks(annotationBlocks)
            }
            if (padDataBlock != null) {
                taggedObjects[14] = padDataBlock.aSN1Object
            }
            if (commentBlocks != null) {
                taggedObjects[15] = Companion.encodeCommentBlocks(commentBlocks!!)
            }
            if (vendorSpecificDataBlocks != null) {
                taggedObjects[16] = ISO39794Util.encodeBlocks(vendorSpecificDataBlocks)
            }
            return ASN1Util.encodeTaggedObjects(taggedObjects)
        }

    companion object {
        private const val serialVersionUID = -9136319709147388829L

        private fun decodeCommentBlocks(asn1Encodable: ASN1Encodable?): List<String>? {
            if (asn1Encodable is ASN1Sequence) {
                val blockASN1Objects = ASN1Util.list(asn1Encodable)
                val blocks: MutableList<String> = ArrayList<String>(blockASN1Objects.size)
                for (blockASN1Object in blockASN1Objects) {
                    blocks.add(ASN1VisibleString.getInstance(blockASN1Object).getString())
                }
                return blocks
            } else if (asn1Encodable is ASN1VisibleString) {
                return mutableListOf<String?>(
                    ASN1VisibleString.getInstance(asn1Encodable).getString()
                )
            }

            //LOGGER.warning("Cannot decode comment blocks!");
            return null
        }

        fun decodeRepresentationBlocks(asn1Encodable: ASN1Encodable): List<FingerImageRepresentationBlock?> {
            val blocks: MutableList<FingerImageRepresentationBlock?> =
                ArrayList<FingerImageRepresentationBlock?>()
            if (ASN1Util.isSequenceOfSequences(asn1Encodable)) {
                val blockASN1Objects = ASN1Util.list(asn1Encodable)
                for (blockASN1Object in blockASN1Objects) {
                    blocks.add(FingerImageRepresentationBlock(blockASN1Object))
                }
            } else {
                blocks.add(FingerImageRepresentationBlock(asn1Encodable))
            }

            return blocks
        }

        /* PRIVATE */
        /**
         * Converts from 37984-4 position coding to 7816-11 (BHT) coding.
         * 
         * @param position an ISO 39794-4 finger position code
         * 
         * @return a ISO7816-11 biometric subtype mask combination
         */
        private fun toBiometricSubtype(position: FingerImagePositionCode?): Int =
            if (position == null) {
                return CBEFFInfo.BIOMETRIC_SUBTYPE_NONE
            } else when (position) {
                FingerImagePositionCode.UNKNOWN_POSITION -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE
                FingerImagePositionCode.RIGHT_THUMB_FINGER -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RIGHT or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_THUMB
                FingerImagePositionCode.RIGHT_INDEX_FINGER -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RIGHT or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_POINTER_FINGER
                FingerImagePositionCode.RIGHT_MIDDLE_FINGER -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RIGHT or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_MIDDLE_FINGER
                FingerImagePositionCode.RIGHT_RING_FINGER -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RIGHT or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RING_FINGER
                FingerImagePositionCode.RIGHT_LITTLE_FINGER -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RIGHT or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LITTLE_FINGER
                FingerImagePositionCode.LEFT_THUMB_FINGER -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_THUMB
                FingerImagePositionCode.LEFT_INDEX_FINGER -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_POINTER_FINGER
                FingerImagePositionCode.LEFT_MIDDLE_FINGER -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_MIDDLE_FINGER
                FingerImagePositionCode.LEFT_RING_FINGER -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RING_FINGER
                FingerImagePositionCode.LEFT_LITTLE_FINGER -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LITTLE_FINGER
                FingerImagePositionCode.RIGHT_FOUR_FINGERS -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RIGHT
                FingerImagePositionCode.LEFT_FOUR_FINGERS -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT
                FingerImagePositionCode.BOTH_THUMB_FINGERS -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_THUMB
                FingerImagePositionCode.UNKNOWN_PALM -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE
                FingerImagePositionCode.RIGHT_FULL_PALM -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RIGHT
                FingerImagePositionCode.RIGHT_WRITERS_PALM -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE
                FingerImagePositionCode.LEFT_FULL_PALM -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT
                FingerImagePositionCode.LEFT_WRITERS_PALM -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT
                FingerImagePositionCode.RIGHT_LOWER_PALM -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RIGHT
                FingerImagePositionCode.RIGHT_UPPER_PALM -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RIGHT
                FingerImagePositionCode.LEFT_LOWER_PALM -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT
                FingerImagePositionCode.LEFT_UPPER_PALM -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT
                FingerImagePositionCode.RIGHT_INTERDIGITAL -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RIGHT
                FingerImagePositionCode.RIGHT_THENAR -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RIGHT
                FingerImagePositionCode.RIGHT_HYPOTHENAR -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_RIGHT
                FingerImagePositionCode.LEFT_INTERDIGITAL -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT
                FingerImagePositionCode.LEFT_THENAR -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT
                FingerImagePositionCode.LEFT_HYPOTHENAR -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE or CBEFFInfo.BIOMETRIC_SUBTYPE_MASK_LEFT
                else -> CBEFFInfo.BIOMETRIC_SUBTYPE_NONE
            }

        private fun encodeCommentBlocks(comments: List<String>): ASN1Encodable {
            val asn1Objects = arrayOfNulls<ASN1Encodable>(comments.size)
            var i = 0
            for (comment in comments) {
                asn1Objects[i++] = DERVisibleString(comment)
            }
            return DERSequence(asn1Objects)
        }
    }
}
