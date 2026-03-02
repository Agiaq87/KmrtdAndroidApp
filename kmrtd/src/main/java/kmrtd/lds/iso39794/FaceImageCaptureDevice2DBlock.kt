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
 * $Id: FaceImageCaptureDevice2DBlock.java 1896 2025-04-18 21:39:56Z martijno $
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
import kmrtd.lds.iso39794.faceimagecapturedevice2d.CaptureDeviceTechnologyId2DCode
import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1Sequence

data class FaceImageCaptureDevice2DBlock(
    val captureDeviceSpectral2DBlock: FaceImageCaptureDeviceSpectral2DBlock?,
    val captureDeviceTechnologyId2D: CaptureDeviceTechnologyId2DCode?
) : Block() {

    /*constructor(
        captureDeviceSpectral2DBlock: FaceImageCaptureDeviceSpectral2DBlock?,
        captureDeviceTechnologyId2D: CaptureDeviceTechnologyId2DCode?
    ) {
        this.captureDeviceSpectral2DBlock = captureDeviceSpectral2DBlock
        this.captureDeviceTechnologyId2D = captureDeviceTechnologyId2D
    }*/

    //  CaptureDevice2DBlock ::= SEQUENCE {
    //    captureDeviceSpectral2DBlock [0] CaptureDeviceSpectral2DBlock OPTIONAL,
    //    captureDeviceTechnologyId2D [1] CaptureDeviceTechnologyId2D OPTIONAL,
    //    ...
    //  }
    /*internal constructor(asn1Encodable: ASN1Encodable) {
        requireNotNull(asn1Encodable) { "Cannot decode null!" }

        if (asn1Encodable is ASN1Sequence) {
            val taggedObjects =
                ASN1Util.decodeTaggedObjects(ASN1Sequence.getInstance(asn1Encodable))
            if (taggedObjects.containsKey(0)) {
                captureDeviceSpectral2DBlock =
                    FaceImageCaptureDeviceSpectral2DBlock.from(taggedObjects[0])
            }

            if (taggedObjects.containsKey(1)) {
                captureDeviceTechnologyId2D = fromCode(
                    ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(
                        taggedObjects[1]
                    )
                )
            }
        }
    }*/

    /*public override fun hashCode(): Int {
        return Objects.hash(captureDeviceSpectral2DBlock, captureDeviceTechnologyId2D)
    }

    public override fun equals(obj: Any?): Boolean {
        if (this === obj) {
            return true
        }
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }

        val other = obj as FaceImageCaptureDevice2DBlock
        return captureDeviceSpectral2DBlock == other.captureDeviceSpectral2DBlock
                && captureDeviceTechnologyId2D == other.captureDeviceTechnologyId2D
    }

    override fun toString(): String {
        return ("FaceImageCaptureDevice2DBlock ["
                + "captureDeviceSpectral2DBlock: " + captureDeviceSpectral2DBlock
                + ", captureDeviceTechnologyId2D: " + captureDeviceTechnologyId2D
                + "]")
    }*/

    override val aSN1Object: ASN1Encodable
        get() = ASN1Util.encodeTaggedObjects(
            buildMap {
                captureDeviceSpectral2DBlock?.let {
                    put(0, it.aSN1Object)
                }
                captureDeviceTechnologyId2D?.let {
                    put(1, ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(it.code))
                }
            }
        )
    /* PACKAGE */
    /*get() {
        val taggedObjects: MutableMap<Int?, ASN1Encodable?> =
            HashMap<Int?, ASN1Encodable?>()
        if (captureDeviceSpectral2DBlock != null) {
            taggedObjects[0] = captureDeviceSpectral2DBlock.aSN1Object
        }
        if (captureDeviceTechnologyId2D != null) {
            taggedObjects[1] = ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(
                captureDeviceTechnologyId2D.code
            )
        }
        return ASN1Util.encodeTaggedObjects(taggedObjects)
    }*/

    companion object {
        private const val serialVersionUID = -8445632002285427924L

        /**
         * Factory method
         *
         * CaptureDevice2DBlock ::= SEQUENCE {
         *   captureDeviceSpectral2DBlock [0] CaptureDeviceSpectral2DBlock OPTIONAL,
         *   captureDeviceTechnologyId2D [1] CaptureDeviceTechnologyId2D OPTIONAL,
         *   ...
         * }
         */
        @JvmStatic
        fun from(asn1Encodable: ASN1Encodable): FaceImageCaptureDevice2DBlock {
            if (asn1Encodable is ASN1Sequence) {

                val taggedObjects =
                    ASN1Util.decodeTaggedObjects(ASN1Sequence.getInstance(asn1Encodable))

                return FaceImageCaptureDevice2DBlock(
                    captureDeviceSpectral2DBlock = if (taggedObjects.containsKey(0)) FaceImageCaptureDeviceSpectral2DBlock.from(
                        taggedObjects[0]
                    ) else null,
                    captureDeviceTechnologyId2D = if (taggedObjects.containsKey(1)) CaptureDeviceTechnologyId2DCode.fromCode(
                        ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(
                            taggedObjects[1]
                        )
                    ) else null
                )
            }

            return FaceImageCaptureDevice2DBlock(
                captureDeviceSpectral2DBlock = null,
                captureDeviceTechnologyId2D = null
            )
        }
    }
}
