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
 * $Id: FaceImageRepresentationBlock.java 1898 2025-06-04 12:05:45Z martijno $
 *
 * Based on ISO-IEC-39794-5-ed-1-v1. Disclaimer:
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
import kmrtd.lds.ImageInfo
import kmrtd.lds.iso39794.PADDataBlock.Companion.decodePADDataBlocks
import kmrtd.lds.iso39794.QualityBlock.Companion.decodeQualityBlocks
import org.bouncycastle.asn1.ASN1Encodable
import java.io.InputStream
import java.math.BigInteger

data class FaceImageRepresentationBlock(
    val representationId: BigInteger,
    val imageRepresentation2DBlock: FaceImageRepresentation2DBlock?,
    var captureDateTimeBlock: DateTimeBlock?,
    var qualityBlocks: List<QualityBlock>?,
    var padDataBlocks: List<PADDataBlock>?,
    var sessionId: BigInteger?,
    var derivedFrom: BigInteger?,
    var captureDeviceBlock: FaceImageCaptureDeviceBlock?,
    var identityMetadataBlock: FaceImageIdentityMetadataBlock?,
    var landmarkBlocks: List<FaceImageLandmarkBlock>?
) : Block(), ImageInfo {

    /*constructor(
        representationId: BigInteger,
        imageRepresentation2DBlock: FaceImageRepresentation2DBlock?,
        captureDateTimeBlock: DateTimeBlock?,
        qualityBlocks: MutableList<QualityBlock?>?,
        padDataBlocks: MutableList<PADDataBlock?>?,
        sessionId: BigInteger?,
        derivedFrom: BigInteger?,
        captureDeviceBlock: FaceImageCaptureDeviceBlock?,
        identityMetadataBlock: FaceImageIdentityMetadataBlock?,
        landmarkBlocks: MutableList<FaceImageLandmarkBlock?>?
    ) {
        this.representationId = representationId
        this.imageRepresentation2DBlock = imageRepresentation2DBlock
        this.captureDateTimeBlock = captureDateTimeBlock
        this.qualityBlocks = qualityBlocks
        this.padDataBlocks = padDataBlocks
        this.sessionId = sessionId
        this.derivedFrom = derivedFrom
        this.captureDeviceBlock = captureDeviceBlock
        this.identityMetadataBlock = identityMetadataBlock
        this.landmarkBlocks = landmarkBlocks
    }*/

    //  RepresentationBlock ::= SEQUENCE {
    //    representationId [0] INTEGER (0..MAX),
    //    imageRepresentation [1] ImageRepresentation,
    //    captureDateTimeBlock [2] CaptureDateTimeBlock OPTIONAL,
    //    qualityBlocks [3] QualityBlocks OPTIONAL,
    //    padDataBlock [4] PADDataBlock OPTIONAL,
    //    sessionId [5] INTEGER (0..MAX) OPTIONAL,
    //    derivedFrom [6] INTEGER (0..MAX) OPTIONAL,
    //    captureDeviceBlock [7] CaptureDeviceBlock OPTIONAL,
    //    identityMetadataBlock [8] IdentityMetadataBlock OPTIONAL,
    //    landmarkBlocks [9] LandmarkBlocks OPTIONAL,
    //    ...
    //  }
    /*internal constructor(asn1Encodable: ASN1Encodable?) {
        val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)
        representationId = ASN1Util.decodeBigInteger(taggedObjects[0])
        imageRepresentation2DBlock = decodeImageRepresentation2DBlock(taggedObjects[1])
        if (taggedObjects.containsKey(2)) {
            captureDateTimeBlock = DateTimeBlock.from(taggedObjects[2])
        }
        if (taggedObjects.containsKey(3)) {
            qualityBlocks = decodeQualityBlocks(taggedObjects[3])
        }
        if (taggedObjects.containsKey(4)) {
            padDataBlocks = decodePADDataBlocks(taggedObjects[4])
        }
        if (taggedObjects.containsKey(5)) {
            sessionId = ASN1Util.decodeBigInteger(taggedObjects[5])
        }
        if (taggedObjects.containsKey(6)) {
            derivedFrom = ASN1Util.decodeBigInteger(taggedObjects[6])
        }
        if (taggedObjects.containsKey(7)) {
            captureDeviceBlock = FaceImageCaptureDeviceBlock.from(taggedObjects[7])
        }
        if (taggedObjects.containsKey(8)) {
            identityMetadataBlock = FaceImageIdentityMetadataBlock.from(taggedObjects[8])
        }
        if (taggedObjects.containsKey(9)) {
            landmarkBlocks = FaceImageLandmarkBlock.decodeLandmarkBlocks(taggedObjects[9])
        }
    }*/

    /*override fun hashCode(): Int {
        return Objects.hash(
            captureDateTimeBlock,
            captureDeviceBlock,
            derivedFrom,
            identityMetadataBlock,
            imageRepresentation2DBlock,
            landmarkBlocks,
            padDataBlocks,
            qualityBlocks,
            representationId,
            sessionId
        )
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

        val other = obj as FaceImageRepresentationBlock
        return captureDateTimeBlock == other.captureDateTimeBlock
                && captureDeviceBlock == other.captureDeviceBlock
                && derivedFrom == other.derivedFrom
                && identityMetadataBlock == other.identityMetadataBlock
                && imageRepresentation2DBlock == other.imageRepresentation2DBlock
                && landmarkBlocks == other.landmarkBlocks && padDataBlocks == other.padDataBlocks
                && qualityBlocks == other.qualityBlocks
                && representationId == other.representationId && sessionId == other.sessionId
    }

    override fun toString(): String {
        return ("FaceImageRepresentationBlock ["
                + "representationId: " + representationId
                + ", imageRepresentation: " + imageRepresentation2DBlock
                + ", captureDateTimeBlock: " + captureDateTimeBlock
                + ", qualityBlocks: " + qualityBlocks
                + ", padDataBlocks: " + padDataBlocks
                + ", sessionId: " + sessionId
                + ", derivedFrom: " + derivedFrom
                + ", captureDeviceBlock: " + captureDeviceBlock
                + ", identityMetadataBlock: " + identityMetadataBlock
                + ", landmarkBlocks: " + landmarkBlocks
                + "]")
    }*/


    override fun getType(): Int =
        ImageInfo.TYPE_PORTRAIT

    // TODO need refactor
    override fun getMimeType(): String =
        imageRepresentation2DBlock?.representationData2DInputMimeType ?: "image/raw" /*{
        if (imageRepresentation2DBlock == null) {
            return "image/raw"
        }
        return imageRepresentation2DBlock.representationData2DInputMimeType
    }*/

    override fun getWidth(): Int {
        if (imageRepresentation2DBlock == null) {
            return 0
        }
        val faceImageInformation2DBlock = imageRepresentation2DBlock.imageInformation2DBlock
        if (faceImageInformation2DBlock == null) {
            return 0
        }
        val imageSizeBlock = faceImageInformation2DBlock.imageSizeBlock
        if (imageSizeBlock == null) {
            return 0
        }
        return imageSizeBlock.width ?: 0
    }

    override fun getHeight(): Int {
        if (imageRepresentation2DBlock == null) {
            return 0
        }
        val faceImageInformation2DBlock = imageRepresentation2DBlock.imageInformation2DBlock
        if (faceImageInformation2DBlock == null) {
            return 0
        }
        val imageSizeBlock = faceImageInformation2DBlock.imageSizeBlock
        if (imageSizeBlock == null) {
            return 0
        }
        return imageSizeBlock.height ?: 0
    }

    override fun getRecordLength(): Long {
        return 0
    }

    override fun getImageLength(): Int {
        if (imageRepresentation2DBlock == null) {
            return 0
        }
        return imageRepresentation2DBlock.representationData2DInputLength.toInt()
    }

    override fun getImageInputStream(): InputStream? {
        if (imageRepresentation2DBlock == null) {
            return null
        }
        return imageRepresentation2DBlock.representationData2DInputStream
    }

    override val aSN1Object: ASN1Encodable?
        get() = ASN1Util.encodeTaggedObjects(
            buildMap {
                put(0, ASN1Util.encodeBigInteger(representationId))
                put(
                    1,
                    Companion.encodeImageRepresentation2DBlock(
                        imageRepresentation2DBlock
                    )
                )
                captureDateTimeBlock?.let {
                    put(2, it.aSN1Object)
                }
                qualityBlocks?.let {
                    put(3, ISO39794Util.encodeBlocks(it))
                }
                padDataBlocks?.let {
                    put(4, ISO39794Util.encodeBlocks(it))
                }
                sessionId?.let {
                    put(5, ASN1Util.encodeBigInteger(it))
                }
                derivedFrom?.let {
                    put(6, ASN1Util.encodeBigInteger(derivedFrom))
                }
                captureDeviceBlock?.let {
                    put(7, it.aSN1Object)
                }
                identityMetadataBlock?.let {
                    put(8, it.aSN1Object)
                }
                landmarkBlocks?.let {
                    put(9, ISO39794Util.encodeBlocks(it))
                }
            }
        )
    /* PACKAGE */
    /*get()
    {
        val taggedObjects: MutableMap<Int?, ASN1Encodable?> =
            HashMap<Int?, ASN1Encodable?>()
        taggedObjects.put(0, ASN1Util.encodeBigInteger(representationId))
        taggedObjects.put(
            1,
            Companion.encodeImageRepresentation2DBlock(
                imageRepresentation2DBlock!!
            )
        )
        if (captureDateTimeBlock != null) {
            taggedObjects.put(2, captureDateTimeBlock!!.aSN1Object)
        }
        if (qualityBlocks != null) {
            taggedObjects.put(3, ISO39794Util.encodeBlocks(qualityBlocks))
        }
        if (padDataBlocks != null) {
            taggedObjects.put(4, ISO39794Util.encodeBlocks(padDataBlocks))
        }
        if (sessionId != null) {
            taggedObjects.put(5, ASN1Util.encodeBigInteger(sessionId))
        }
        if (derivedFrom != null) {
            taggedObjects.put(6, ASN1Util.encodeBigInteger(derivedFrom))
        }
        if (captureDeviceBlock != null) {
            taggedObjects.put(7, captureDeviceBlock!!.aSN1Object)
        }
        if (identityMetadataBlock != null) {
            taggedObjects.put(8, identityMetadataBlock!!.aSN1Object)
        }
        if (landmarkBlocks != null) {
            taggedObjects.put(9, ISO39794Util.encodeBlocks(landmarkBlocks))
        }
        return ASN1Util.encodeTaggedObjects(taggedObjects)
    }*/

    companion object {
        private const val serialVersionUID = -8372278398595506771L

        // RepresentationBlocks ::= SEQUENCE SIZE (1) OF RepresentationBlock
        fun decodeRepresentationBlocks(asn1Encodable: ASN1Encodable): List<FaceImageRepresentationBlock> =
            if (ASN1Util.isSequenceOfSequences(asn1Encodable)) {
                ASN1Util.list(asn1Encodable).map { FaceImageRepresentationBlock.from(it) }
            } else {
                listOf(FaceImageRepresentationBlock.from(asn1Encodable))
            }

        /*{
            val blocks: MutableList<FaceImageRepresentationBlock> = mutableListOf()
                //ArrayList<FaceImageRepresentationBlock?>()
            if (ASN1Util.isSequenceOfSequences(asn1Encodable)) {
                val blockASN1Objects = ASN1Util.list(asn1Encodable)
                for (blockASN1Object in blockASN1Objects) {
                    blocks.add(FaceImageRepresentationBlock(blockASN1Object))
                }
            } else {
                blocks.add(FaceImageRepresentationBlock(asn1Encodable))
            }

            return blocks
        }*/

        /* PRIVATE */ //  ImageRepresentation ::= CHOICE {
        //    base [0] ImageRepresentationBase,
        //    extensionBlock [1] ImageRepresentationExtensionBlock
        //  }
        //
        //  ImageRepresentationBase ::= CHOICE {
        //    imageRepresentation2DBlock [0] ImageRepresentation2DBlock
        //  }
        //
        //  ImageRepresentationExtensionBlock ::= SEQUENCE {
        //    ...
        //  }
        private fun decodeImageRepresentation2DBlock(asn1Encodable: ASN1Encodable?): FaceImageRepresentation2DBlock? {
            val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)
            if (taggedObjects.containsKey(0)) {
                val baseTaggedObjects = ASN1Util.decodeTaggedObjects(taggedObjects[0])
                if (baseTaggedObjects.containsKey(0)) {
                    return FaceImageRepresentation2DBlock.from(baseTaggedObjects[0])
                }

                /* NOTE: Not supporting [1] ShapeRepresentation3DBlock... */
            }

            return null
        }

        private fun encodeImageRepresentation2DBlock(faceImageRepresentation2DBlock: FaceImageRepresentation2DBlock?): ASN1Encodable =
            ASN1Util.encodeTaggedObjects(
                mapOf(
                    0 to ASN1Util.encodeTaggedObjects(
                        mapOf(
                            0 to faceImageRepresentation2DBlock?.aSN1Object
                        )
                    )
                )
            )

        /*{
            val baseTaggedObjects: MutableMap<Int, ASN1Encodable?> = mutableMapOf()
            baseTaggedObjects[0] = faceImageRepresentation2DBlock.aSN1Object

            val taggedObjects: MutableMap<Int?, ASN1Encodable?> = HashMap<Int?, ASN1Encodable?>()
            taggedObjects[0] = ASN1Util.encodeTaggedObjects(baseTaggedObjects)
            return ASN1Util.encodeTaggedObjects(taggedObjects)
        }*/

        /**
         * Factory method
         *
         * RepresentationBlock ::= SEQUENCE {
         *   representationId [0] INTEGER (0..MAX),
         *   imageRepresentation [1] ImageRepresentation,
         *   captureDateTimeBlock [2] CaptureDateTimeBlock OPTIONAL,
         *   qualityBlocks [3] QualityBlocks OPTIONAL,
         *   padDataBlock [4] PADDataBlock OPTIONAL,
         *   sessionId [5] INTEGER (0..MAX) OPTIONAL,
         *   derivedFrom [6] INTEGER (0..MAX) OPTIONAL,
         *   captureDeviceBlock [7] CaptureDeviceBlock OPTIONAL,
         *   identityMetadataBlock [8] IdentityMetadataBlock OPTIONAL,
         *   landmarkBlocks [9] LandmarkBlocks OPTIONAL,
         *   ...
         * }
         */
        @JvmStatic
        fun from(asn1Encodable: ASN1Encodable?): FaceImageRepresentationBlock {
            val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)

            return FaceImageRepresentationBlock(
                representationId = ASN1Util.decodeBigInteger(taggedObjects[0]),
                imageRepresentation2DBlock = decodeImageRepresentation2DBlock(taggedObjects[1]),
                captureDateTimeBlock = if (taggedObjects.containsKey(2)) DateTimeBlock.from(
                    taggedObjects[2]
                ) else null,
                qualityBlocks = if (taggedObjects.containsKey(3)) decodeQualityBlocks(taggedObjects[3]) else null,
                padDataBlocks = if (taggedObjects.containsKey(4)) decodePADDataBlocks(taggedObjects[4]) else null,
                sessionId = if (taggedObjects.containsKey(5)) ASN1Util.decodeBigInteger(
                    taggedObjects[5]
                ) else null,
                derivedFrom = if (taggedObjects.containsKey(6)) ASN1Util.decodeBigInteger(
                    taggedObjects[6]
                ) else null,
                captureDeviceBlock = if (taggedObjects.containsKey(7)) FaceImageCaptureDeviceBlock.from(
                    taggedObjects[7]
                ) else null,
                identityMetadataBlock = if (taggedObjects.containsKey(8)) FaceImageIdentityMetadataBlock.from(
                    taggedObjects[8]
                ) else null,
                landmarkBlocks = if (taggedObjects.containsKey(9)) FaceImageLandmarkBlock.decodeLandmarkBlocks(
                    taggedObjects[9]
                ) else null
            )
        }
    }
}
