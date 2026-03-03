package kmrtd.lds.iso19794.support

import java.io.Serializable

/**
 * Feature points as described in Section 5.6.3 of ISO/IEC FCD 19794-5.
 *
 * @author The JMRTD team (info@jmrtd.org)
 * @version $Revision: 1808 $
 */
@JvmRecord
data class FeaturePoint
/**
 * Constructs a new feature point.
 *
 * @param type      feature point type
 * @param majorCode major code
 * @param minorCode minor code
 * @param x         X-coordinate
 * @param y         Y-coordinate
 */(val type: Int, val majorCode: Int, val minorCode: Int, val x: Int, val y: Int) : Serializable {
    /**
     * Constructs a new feature point.
     *
     * @param type feature point type
     * @param code combined major and minor code
     * @param x    X-coordinate
     * @param y    Y-coordinate
     */
    internal constructor(type: Int, code: Byte, x: Int, y: Int) : this(
        type,
        (code.toInt() and 0xF0) shr 4,
        code.toInt() and 0x0F,
        x,
        y
    )

    /**
     * Returns the major code of this point.
     *
     * @return major code
     */
    /*fun majorCode(): Int {
        return majorCode
    }*/

    /**
     * Returns the minor code of this point.
     *
     * @return minor code
     */
    /*public fun minorCode(): Int {
        return minorCode
    }*/

    /**
     * Returns the type of this point.
     *
     * @return type
     */
    /*public override fun type(): Int {
        return type
    }*/

    /**
     * Returns the X-coordinate of this point.
     *
     * @return X-coordinate
     */
    /*public override fun x(): Int {
        return x
    }*/

    /**
     * Returns the Y-coordinate of this point.
     *
     * @return Y-coordinate
     */
    /*public override fun y(): Int {
        return y
    }*/

    /**
     * Generates a textual representation of this point.
     *
     * @return a textual representation of this point
     * @see Object.toString
     */
    override fun toString(): String = "( point: " + majorCode + "." + minorCode +
            ", " +
            "type: " + Integer.toHexString(type) + ", " +
            "(" + x + ", " +
            y + ")" +
            ")"
}
