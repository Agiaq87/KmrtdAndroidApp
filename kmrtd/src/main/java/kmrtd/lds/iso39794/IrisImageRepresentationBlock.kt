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
 * $Id: IrisImageRepresentationBlock.java 1896 2025-04-18 21:39:56Z martijno $
 *
 * Based on ISO-IEC-39794-6-ed-1-v1. Disclaimer:
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
package kmrtd.lds.iso39794

import kmrtd.ASN1Util
import kmrtd.lds.ImageInfo
import kmrtd.lds.iso39794.QualityBlock.Companion.decodeQualityBlocks
import kmrtd.lds.iso39794.irisimage.CompressionHistoryCode
import kmrtd.lds.iso39794.irisimage.EyeLabelCode
import kmrtd.lds.iso39794.irisimage.HorizontalOrientationCode
import kmrtd.lds.iso39794.irisimage.IrisImageKindCode
import kmrtd.lds.iso39794.irisimage.RangingErrorCode
import kmrtd.lds.iso39794.irisimage.VerticalOrientationCode
import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1OctetString
import org.bouncycastle.asn1.DEROctetString
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.Objects

class IrisImageRepresentationBlock(asn1Encodable: ASN1Encodable) : Block(), ImageInfo {
    enum class ImageDataFormatCode(override val code: Int, val mimeType: String) :
        EncodableEnum<ImageDataFormatCode> {
        PGM(0, "image/pgm"),
        PPM(1, "image/ppm"),
        PNG(2, "image/png"),
        JPEG2000_LOSSLESS(3, "image/jp2"),
        JPEG2000_LOSSY(4, "image/jp2");

        companion object {
            fun fromCode(code: Int): ImageDataFormatCode? {
                return EncodableEnum.fromCode(
                    code,
                    ImageDataFormatCode::class.java
                )
            }
        }
    }

    //  RangeOrError ::= CHOICE {
    //    range   [0]     INTEGER (2..65533),
    //    errorCode       [1]     RangingErrorCode
    //  }
    val eyeLabelCode: EyeLabelCode?

    val irisImageKind: IrisImageKindCode?

    val bitDepth: Int

    val imageDataFormat: ImageDataFormatCode?

    val horizontalOrientationCode: HorizontalOrientationCode?

    val verticalOrientationCode: VerticalOrientationCode?

    val compressionHistoryCode: CompressionHistoryCode?

    private val imageData: ByteArray?

    /** INTEGER (2..65533), is null iff rangingErrorCode is not null.  */
    private var range: Int? = null
    private var rangingErrorCode: RangingErrorCode? = null

    private val captureDateTimeBlock: DateTimeBlock


    var captureDeviceBlock: IrisImageCaptureDeviceBlock? = null
        private set

    var qualityBlocks: MutableList<QualityBlock?>? = null
        private set

    var rollAngleBlock: RollAngleBlock? = null
        private set

    var localisationBlock: IrisImageLocalisationBlock? = null
        private set

    var pADDataBlock: PADDataBlock? = null
        private set

    //  RepresentationBlock ::= SEQUENCE {
    //    eyeLabelCode              [0]          EyeLabelCode,
    //    irisImageKind             [1]          IrisImageKind,
    //    bitDepth                  [2]          INTEGER (8..24),
    //    imageDataFormat           [3]          ImageDataFormat,
    //    horizontalOrientationCode [4]          HorizontalOrientationCode,
    //    verticalOrientationCode   [5]          VerticalOrientationCode,
    //    compressionHistoryCode    [6]          CompressionHistoryCode,
    //    range                     [7]          RangeOrError,
    //    captureDateTimeBlock      [8]          CaptureDateTimeBlock,
    //    irisImageData             [9]          OCTET STRING,
    //    captureDeviceBlock        [10]         CaptureDeviceBlock             OPTIONAL,
    //    qualityBlocks             [11]         QualityBlocks                  OPTIONAL,
    //    rollAngleBlock            [12]         RollAngleBlock                 OPTIONAL,
    //    localisationBlock         [13]         LocalisationBlock              OPTIONAL,
    //    pADDataBlock              [14]         PADDataBlock                   OPTIONAL,
    //    ...
    //  }
    init {
        requireNotNull(asn1Encodable) { "Cannot decode!" }
        val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)
        eyeLabelCode = EyeLabelCode.fromCode(ASN1Util.decodeInt(taggedObjects.get(0)))
        irisImageKind = IrisImageKindCode.fromCode(
            ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects.get(1))
        )
        bitDepth = ASN1Util.decodeInt(taggedObjects.get(2))
        this.imageDataFormat = ImageDataFormatCode.Companion.fromCode(
            ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects.get(3))
        )
        horizontalOrientationCode =
            HorizontalOrientationCode.fromCode(ASN1Util.decodeInt(taggedObjects.get(4)))
        verticalOrientationCode =
            VerticalOrientationCode.fromCode(ASN1Util.decodeInt(taggedObjects.get(5)))
        compressionHistoryCode =
            CompressionHistoryCode.fromCode(ASN1Util.decodeInt(taggedObjects.get(6)))
        decodeRangeOrError(taggedObjects.get(7))
        captureDateTimeBlock = DateTimeBlock.from(taggedObjects.get(8))
        imageData = (ASN1OctetString.getInstance(taggedObjects.get(9))).getOctets()
        if (taggedObjects.containsKey(10)) {
            captureDeviceBlock = IrisImageCaptureDeviceBlock(taggedObjects.get(10))
        }
        if (taggedObjects.containsKey(11)) {
            qualityBlocks = decodeQualityBlocks(taggedObjects.get(11))
        }
        if (taggedObjects.containsKey(12)) {
            rollAngleBlock = RollAngleBlock.from(taggedObjects.get(12))
        }
        if (taggedObjects.containsKey(13)) {
            localisationBlock = IrisImageLocalisationBlock(taggedObjects.get(13))
        }
        if (taggedObjects.containsKey(14)) {
            this.pADDataBlock = PADDataBlock(taggedObjects.get(14))
        }
    }

    fun geImageData(): ByteArray? {
        return imageData
    }

    /* PACKAGE */
    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + imageData.contentHashCode()
        result = prime * result + Objects.hash(
            bitDepth, captureDateTimeBlock, captureDeviceBlock, compressionHistoryCode,
            eyeLabelCode, horizontalOrientationCode,
            this.imageDataFormat, irisImageKind, localisationBlock,
            this.pADDataBlock,
            qualityBlocks, range, rangingErrorCode, rollAngleBlock, verticalOrientationCode
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

        val other = obj as IrisImageRepresentationBlock
        return bitDepth == other.bitDepth && captureDateTimeBlock == other.captureDateTimeBlock
                && captureDeviceBlock == other.captureDeviceBlock
                && compressionHistoryCode == other.compressionHistoryCode && eyeLabelCode == other.eyeLabelCode && horizontalOrientationCode == other.horizontalOrientationCode && imageData.contentEquals(
            other.imageData
        ) && this.imageDataFormat == other.imageDataFormat && irisImageKind == other.irisImageKind && localisationBlock == other.localisationBlock
                && this.pADDataBlock == other.pADDataBlock && qualityBlocks == other.qualityBlocks
                && range == other.range && rangingErrorCode == other.rangingErrorCode && rollAngleBlock == other.rollAngleBlock
                && verticalOrientationCode == other.verticalOrientationCode
    }

    override fun toString(): String {
        return ("IrisImageRepresentationBlock ["
                + "eyeLabelCode: " + eyeLabelCode
                + ", irisImageKind: " + irisImageKind
                + ", bitDepth: " + bitDepth
                + ", imageDataFormat: " + this.imageDataFormat
                + ", horizontalOrientationCode: " + horizontalOrientationCode
                + ", verticalOrientationCode: " + verticalOrientationCode
                + ", compressionHistoryCode: " + compressionHistoryCode
                + ", imageData: " + (if (imageData != null) "null" else imageData.size)
                + ", range: " + (if (range != null) range else rangingErrorCode)
                + ", captureDateTimeBlock: " + captureDateTimeBlock
                + ", captureDeviceBlock: " + captureDeviceBlock
                + ", qualityBlocks: " + qualityBlocks
                + ", rollAngleBlock: " + rollAngleBlock
                + ", localisationBlock: " + localisationBlock
                + ", padDataBlock: " + this.pADDataBlock
                + "]")
    }

    override fun getType(): Int {
        return ImageInfo.Companion.TYPE_IRIS
    }

    override fun getMimeType(): String? {
        if (this.imageDataFormat == null) {
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
        return if (imageData == null) 0 else imageData.size
    }

    override fun getImageInputStream(): InputStream {
        return ByteArrayInputStream(imageData)
    }

    val aSN1Object: ASN1Encodable
        get() {
            val taggedObjects: MutableMap<Int?, ASN1Encodable?> =
                HashMap<Int?, ASN1Encodable?>()
            taggedObjects.put(0, ASN1Util.encodeInt(eyeLabelCode!!.code))
            taggedObjects.put(
                1,
                ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(irisImageKind!!.code)
            )
            taggedObjects.put(2, ASN1Util.encodeInt(bitDepth))
            taggedObjects.put(
                3,
                ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(imageDataFormat!!.code)
            )
            taggedObjects.put(4, ASN1Util.encodeInt(horizontalOrientationCode!!.code))
            taggedObjects.put(5, ASN1Util.encodeInt(verticalOrientationCode!!.code))
            taggedObjects.put(6, ASN1Util.encodeInt(compressionHistoryCode!!.code))
            if (range != null) {
                taggedObjects.put(7, ASN1Util.encodeInt(range!!))
            } else if (rangingErrorCode != null) {
                taggedObjects.put(7, ASN1Util.encodeInt(rangingErrorCode!!.code))
            }
            taggedObjects.put(8, captureDateTimeBlock.aSN1Object)
            taggedObjects.put(9, DEROctetString(imageData))
            if (captureDeviceBlock != null) {
                taggedObjects.put(10, captureDeviceBlock!!.aSN1Object)
            }
            if (qualityBlocks != null) {
                taggedObjects.put(10, ISO39794Util.encodeBlocks(qualityBlocks))
            }
            if (rollAngleBlock != null) {
                taggedObjects.put(12, rollAngleBlock!!.aSN1Object)
            }
            if (localisationBlock != null) {
                taggedObjects.put(13, localisationBlock!!.aSN1Object)
            }
            if (this.pADDataBlock != null) {
                taggedObjects.put(14, pADDataBlock!!.aSN1Object)
            }
            return ASN1Util.encodeTaggedObjects(taggedObjects)
        }

    val biometricSubtype: Int
        /* NOTE: 39795-6 code value happens to be consistent with 19794-6 defined subtype. */
        get() {
            if (eyeLabelCode == null) {
                return EyeLabelCode.UNKNOWN.code
            }
            return eyeLabelCode.code
        }

    /* PRIVATE */
    private fun decodeRangeOrError(asn1Encodable: ASN1Encodable?) {
        val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)
        if (taggedObjects.containsKey(0)) {
            range = ASN1Util.decodeInt(taggedObjects.get(0))
            rangingErrorCode = null
        } else if (taggedObjects.containsKey(1)) {
            range = null
            rangingErrorCode = RangingErrorCode.fromCode(ASN1Util.decodeInt(taggedObjects.get(1)))
        }
    }

    companion object {
        private val serialVersionUID = -982987535985932641L

        fun decodeRepresentationBlocks(asn1Encodable: ASN1Encodable): MutableList<IrisImageRepresentationBlock?> {
            val result: MutableList<IrisImageRepresentationBlock?> =
                ArrayList<IrisImageRepresentationBlock?>()
            if (ASN1Util.isSequenceOfSequences(asn1Encodable)) {
                val representationBlockASN1Objects = ASN1Util.list(asn1Encodable)
                for (representationBlockASN1Object in representationBlockASN1Objects) {
                    result.add(IrisImageRepresentationBlock(representationBlockASN1Object))
                }
            } else {
                result.add(IrisImageRepresentationBlock(asn1Encodable))
            }

            return result
        }
    }
}
