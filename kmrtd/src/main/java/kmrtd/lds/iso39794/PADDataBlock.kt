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
 * $Id: PADDataBlock.java 1892 2025-03-18 15:15:52Z martijno $
 *
 * Based on ISO-IEC-39794-1-ed-1-v1. Disclaimer:
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
import kmrtd.lds.iso39794.PADScoreBlock.Companion.decodePADScoreBlocks
import kmrtd.lds.iso39794.pad.PADCaptureContextCode
import kmrtd.lds.iso39794.pad.PADCriteriaCategoryCode
import kmrtd.lds.iso39794.pad.PADDecisionCode
import kmrtd.lds.iso39794.pad.PADSupervisionLevelCode
import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1OctetString
import org.bouncycastle.asn1.DEROctetString
import org.bouncycastle.asn1.DERSequence
import java.util.Objects

data class PADDataBlock(
    val pADDecisionCode: PADDecisionCode?,
    val padScoreBlocks: List<PADScoreBlock>?,
    val padExtendedDataBlocks: List<ExtendedDataBlock>?,
    val pADCaptureContextCode: PADCaptureContextCode?,
    val pADSupervisionLevelCode: PADSupervisionLevelCode?,
    /** INTEGER (0..100).  */
    val riskLevel: Int,
    val pADCriteriaCategoryCode: PADCriteriaCategoryCode?,
    val parameter: ByteArray?,
    val challenges: List<ByteArray>?,
    val captureDateTimeBlock: DateTimeBlock?
) : Block() {


    /*constructor(
        padDecisionCode: PADDecisionCode?,
        padScoreBlocks: MutableList<PADScoreBlock?>?,
        padExtendedDataBlocks: MutableList<ExtendedDataBlock?>?,
        padCaptureContextCode: PADCaptureContextCode?,
        padSupervisionLevelCode: PADSupervisionLevelCode?,
        riskLevel: Int,
        padCriteriaCategoryCode: PADCriteriaCategoryCode?,
        parameter: ByteArray?,
        challenges: MutableList<ByteArray>?,
        captureDateTimeBlock: DateTimeBlock?
    ) {
        this.pADDecisionCode = padDecisionCode
        this.padScoreBlocks = padScoreBlocks
        this.padExtendedDataBlocks = padExtendedDataBlocks
        this.pADCaptureContextCode = padCaptureContextCode
        this.pADSupervisionLevelCode = padSupervisionLevelCode
        this.riskLevel = riskLevel
        this.pADCriteriaCategoryCode = padCriteriaCategoryCode
        this.parameter = parameter
        this.challenges = challenges
        this.captureDateTimeBlock = captureDateTimeBlock
    }*/

    //  PADDataBlock ::= SEQUENCE {
    //    decision              [0] PADDecision            OPTIONAL,
    //    scoreBlocks           [1] PADScoreBlocks         OPTIONAL,
    //    extendedDataBlocks    [2] PADExtendedDataBlocks  OPTIONAL,
    //    captureContext        [3] PADCaptureContext      OPTIONAL,
    //    supervisionLevel      [4] PADSupervisionLevel    OPTIONAL,
    //    riskLevel             [5] PADRiskLevel           OPTIONAL,
    //    criteriaCategory      [6] PADCriteriaCategory    OPTIONAL,
    //    parameter             [7] OCTET STRING           OPTIONAL,
    //    challenges            [8] PADChallenges          OPTIONAL,
    //    captureDateTimeBlock  [9] CaptureDateTimeBlock   OPTIONAL,
    //    ...
    //  }
    /*internal constructor(asn1Encodable: ASN1Encodable?) {
        val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)
        if (taggedObjects.containsKey(0)) {
            this.pADDecisionCode = PADDecisionCode.fromCode(
                ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects.get(0))
            )
        }
        if (taggedObjects.containsKey(1)) {
            padScoreBlocks = decodePADScoreBlocks(taggedObjects.get(1))
        }
        if (taggedObjects.containsKey(2)) {
            padExtendedDataBlocks =
                ExtendedDataBlock.decodeExtendedDataBlocks(taggedObjects.get(2)!!)
        }
        if (taggedObjects.containsKey(3)) {
            this.pADCaptureContextCode = PADCaptureContextCode.fromCode(
                ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects.get(3))
            )
        }
        if (taggedObjects.containsKey(4)) {
            this.pADSupervisionLevelCode = PADSupervisionLevelCode.fromCode(
                ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects.get(4))
            )
        }
        if (taggedObjects.containsKey(5)) {
            riskLevel = ASN1Util.decodeInt(taggedObjects.get(5))
        }
        if (taggedObjects.containsKey(6)) {
            this.pADCriteriaCategoryCode = PADCriteriaCategoryCode.fromCode(
                ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects.get(6))
            )
        }
        if (taggedObjects.containsKey(7)) {
            parameter = ASN1OctetString.getInstance(taggedObjects.get(7)).getOctets()
        }
        if (taggedObjects.containsKey(8)) {
            challenges = decodePADChallenges(taggedObjects.get(8))
        }
        if (taggedObjects.containsKey(9)) {
            captureDateTimeBlock = from(taggedObjects.get(9))
        }
    }*/

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + parameter.contentHashCode()
        result = (prime * result
                + Objects.hash(
            captureDateTimeBlock, challenges,
            this.pADCaptureContextCode,
            this.pADCriteriaCategoryCode,
            this.pADDecisionCode, padExtendedDataBlocks, padScoreBlocks,
            this.pADSupervisionLevelCode, riskLevel
        ))
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }

        val that = other as PADDataBlock
        return captureDateTimeBlock == that.captureDateTimeBlock
                && equalBytes(challenges, that.challenges)
                && this.pADCaptureContextCode == that.pADCaptureContextCode &&
                this.pADCriteriaCategoryCode == that.pADCriteriaCategoryCode &&
                this.pADDecisionCode == that.pADDecisionCode && padExtendedDataBlocks == that.padExtendedDataBlocks &&
                padScoreBlocks == that.padScoreBlocks &&
                this.pADSupervisionLevelCode == that.pADSupervisionLevelCode &&
                parameter.contentEquals(
                    that.parameter
                ) &&
                riskLevel == that.riskLevel
    }

    /*    override fun toString(): String {
            return ("PADDataBlock [padDecisionCode: " + this.pADDecisionCode
                    + ", padScoreBlocks: " + padScoreBlocks
                    + ", padExtendedDataBlocks: " + padExtendedDataBlocks
                    + ", padCaptureContextCode: " + this.pADCaptureContextCode
                    + ", padSupervisionLevelCode: " + this.pADSupervisionLevelCode
                    + ", riskLevel: " + riskLevel
                    + ", padCriteriaCategoryCode: " + this.pADCriteriaCategoryCode
                    + ", parameter: " + Hex.bytesToHexString(parameter)
                    + ", challenges: " + toString(challenges)
                    + ", captureDateTimeBlock: " + captureDateTimeBlock + "]")
        }*/

    override val aSN1Object: ASN1Encodable
        get() = ASN1Util.encodeTaggedObjects(
            buildMap {
                pADDecisionCode?.let {
                    put(0, ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(it.code))
                }
                padScoreBlocks?.let {
                    put(1, ISO39794Util.encodeBlocks(it))
                }
                padExtendedDataBlocks?.let {
                    put(2, ISO39794Util.encodeBlocks(it))

                }
                pADCaptureContextCode?.let {
                    put(3, ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(it.code))
                }
                pADSupervisionLevelCode?.let {
                    put(4, ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(it.code))
                }
                if (riskLevel >= 0) {
                    put(5, ASN1Util.encodeInt(riskLevel))
                }
                pADCriteriaCategoryCode?.let {
                    put(6, ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(it.code))
                }
                parameter?.let {
                    put(7, DEROctetString(it))
                }
                challenges?.let {
                    put(8, encodePADChallenges(it))
                }
                captureDateTimeBlock?.let {
                    put(9, it.aSN1Object)
                }
            }
        )
    /*get() {
        if (pADDecisionCode != null) {
            taggedObjects[0] = ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(pADDecisionCode.code)
        }
        if (padScoreBlocks != null) {
            taggedObjects[1] = ISO39794Util.encodeBlocks(padScoreBlocks)
        }
        if (padExtendedDataBlocks != null) {
            taggedObjects[2] = ISO39794Util.encodeBlocks(padExtendedDataBlocks)
        }
        pADCaptureContextCode != null) {
            taggedObjects[3] = ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(pADCaptureContextCode.code)
        }
        pADSupervisionLevelCode != null) {
            taggedObjects[4] = ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(pADSupervisionLevelCode.code)
        }
        if (riskLevel >= 0) {
            taggedObjects[5] = ASN1Util.encodeInt(riskLevel)
        }
        if (this.pADCriteriaCategoryCode != null) {
            taggedObjects[6] = ISO39794Util.encodeCodeAsChoiceExtensionBlockFallback(pADCriteriaCategoryCode.code)
        }
        if (parameter != null) {
            taggedObjects[7] = DEROctetString(parameter)
        }
        if (challenges != null) {
            taggedObjects[8] = Companion.encodePADChallenges(challenges)
        }
        if (captureDateTimeBlock != null) {
            taggedObjects[9] = captureDateTimeBlock.aSN1Object
        }
        return ASN1Util.encodeTaggedObjects(taggedObjects)
    }*/

    companion object {
        private const val serialVersionUID = 1498548397505331884L

        /* PACKAGE */
        @JvmStatic
        fun decodePADDataBlocks(asn1Encodable: ASN1Encodable?): List<PADDataBlock> =
            if (ASN1Util.isSequenceOfSequences(asn1Encodable)) {
                ASN1Util.list(asn1Encodable).map { from(it) }
            } else {
                listOf(from(asn1Encodable))
            }

        /*{
        if (ASN1Util.isSequenceOfSequences(asn1Encodable)) {
            val blockASN1Objects = ASN1Util.list(asn1Encodable)
            val blocks: MutableList<PADDataBlock?> =
                ArrayList<PADDataBlock?>(blockASN1Objects.size)
            for (blockASN1Object in blockASN1Objects) {
                blocks.add(PADDataBlock(blockASN1Object))
            }
            return blocks
        } else {
            val block = PADDataBlock(asn1Encodable)
            return mutableListOf<PADDataBlock?>(block)
        }*/
        /*}*/

        /* PRIVATE */
        private fun decodePADChallenges(asn1Encodable: ASN1Encodable?): List<ByteArray> =
            ASN1Util.list(asn1Encodable).map { ASN1OctetString.getInstance(it).octets }

        /*{
            val challengeASN1Objects = ASN1Util.list(asn1Encodable)
            val padChallenges: MutableList<ByteArray> =
                ArrayList<ByteArray>(challengeASN1Objects.size)
            for (challengeASN1Object in challengeASN1Objects) {
                padChallenges.add(ASN1OctetString.getInstance(challengeASN1Object).octets)
            }
            return padChallenges
        }*/

        private fun encodePADChallenges(padChallenges: List<ByteArray>): ASN1Encodable {
            val asn1Encodables = arrayOfNulls<ASN1Encodable>(padChallenges.size)
            var i = 0
            for (padChallenge in padChallenges) {
                asn1Encodables[i++] = DEROctetString(padChallenge)
            }
            return DERSequence(asn1Encodables)
        }

        private fun equalBytes(
            challenges1: List<ByteArray>?,
            challenges2: List<ByteArray>?
        ): Boolean {
            if (challenges1 == challenges2) {
                return true
            }
            if (challenges1 == null && challenges2 != null) {
                return false
            }
            if (challenges1 != null && challenges2 == null) {
                return false
            }
            if (challenges1!!.size != challenges2!!.size) {
                return false
            }
            val length = challenges1.size
            for (i in 0..<length) {
                if (!(challenges1[i].contentEquals(challenges2[i]))) {
                    return false
                }
            }
            return true
        }

        // UNUSED
        /*private fun toString(challenges: MutableList<ByteArray>?): String {
            if (challenges == null) {
                return "null"
            }
            var isFirst = true
            val stringBuilder = StringBuilder().append("[")
            for (challenge in challenges) {
                if (isFirst) {
                    isFirst = false
                } else {
                    stringBuilder.append(", ")
                }
                stringBuilder.append(Hex.bytesToHexString(challenge))
            }
            return stringBuilder.append("]").toString()
        }*/

        /**
         * Factory method
         *
         * PADDataBlock ::= SEQUENCE {
         *   decision              [0] PADDecision            OPTIONAL,
         *   scoreBlocks           [1] PADScoreBlocks         OPTIONAL,
         *   extendedDataBlocks    [2] PADExtendedDataBlocks  OPTIONAL,
         *   captureContext        [3] PADCaptureContext      OPTIONAL,
         *   supervisionLevel      [4] PADSupervisionLevel    OPTIONAL,
         *   riskLevel             [5] PADRiskLevel           OPTIONAL,
         *   criteriaCategory      [6] PADCriteriaCategory    OPTIONAL,
         *   parameter             [7] OCTET STRING           OPTIONAL,
         *   challenges            [8] PADChallenges          OPTIONAL,
         *   captureDateTimeBlock  [9] CaptureDateTimeBlock   OPTIONAL,
         *   ...
         * }
         */
        @JvmStatic
        fun from(asn1Encodable: ASN1Encodable?): PADDataBlock {
            val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)

            return PADDataBlock(
                pADDecisionCode = if (taggedObjects.containsKey(0)) PADDecisionCode.fromCode(
                    ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects[0])
                ) else null,
                padScoreBlocks = if (taggedObjects.containsKey(1)) decodePADScoreBlocks(
                    taggedObjects[1]
                ) else null,
                padExtendedDataBlocks = if (taggedObjects.containsKey(2)) taggedObjects[2]?.let {
                    ExtendedDataBlock.decodeExtendedDataBlocks(it)
                } else null,
                pADCaptureContextCode = if (taggedObjects.containsKey(3)) PADCaptureContextCode.fromCode(
                    ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects[3])
                ) else null,
                pADSupervisionLevelCode = if (taggedObjects.containsKey(4)) PADSupervisionLevelCode.fromCode(
                    ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects[4])
                ) else null,
                riskLevel = if (taggedObjects.containsKey(5)) ASN1Util.decodeInt(taggedObjects[5]) else -1,
                pADCriteriaCategoryCode = if (taggedObjects.containsKey(6)) PADCriteriaCategoryCode.fromCode(
                    ISO39794Util.decodeCodeFromChoiceExtensionBlockFallback(taggedObjects[6])
                ) else null,
                parameter = if (taggedObjects.containsKey(7)) ASN1OctetString.getInstance(
                    taggedObjects[7]
                ).octets else null,
                challenges = if (taggedObjects.containsKey(8)) decodePADChallenges(taggedObjects[8]) else null,
                captureDateTimeBlock = if (taggedObjects.containsKey(9)) DateTimeBlock.from(
                    taggedObjects[9]
                ) else null
            )
        }
    }
}
