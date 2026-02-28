/*
 * JMRTD - A Java API for accessing machine readable travel documents.
 *
 * Copyright (C) 2006 - 2022  The JMRTD team
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
 * $Id: $
 */

package org.jmrtd.lds;

import java.util.Arrays;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DLSequence;

/*
 * EFDIRInfo ::= SEQUENCE {
 *   protocol OBJECT IDENTIFIER(id-EFDIR),
 *   eFDIR OCTET STRING
 * }
 *
 * id-EFDIR OBJECT IDENTIFIER ::= {
 *   id-icao-mrtd-security 13
 * }
 */

/**
 * Encapsulates a full copy of the content of the
 * transparent elementary file EF-DIR contained in the Master File.
 *
 * @author The JMRTD team (info@jmrtd.org)
 *
 * @version $Revision: $
 */
public class EFDIRInfo extends SecurityInfo {

  private static final long serialVersionUID = 6778691696414558842L;

  private static final String EF_DIR_PROTOCOL_OID = "2.23.136.1.1.13";

  private byte[] efDIR;

  public EFDIRInfo(byte[] efDIR) {
    if (efDIR == null) {
      throw new IllegalArgumentException("Cannot create EFDIRInfo for null");
    }
    this.efDIR = Arrays.copyOf(efDIR, efDIR.length);
  }

  /**
   * The contents of the EF-DIR file.
   *
   * @return the contents of the EF-DIR file
   */
  public byte[] getEFDIR() {
    return Arrays.copyOf(efDIR, efDIR.length);
  }

  @Override
  public ASN1Primitive getDERObject() {
    ASN1EncodableVector v = new ASN1EncodableVector();
    v.add(new ASN1ObjectIdentifier(EF_DIR_PROTOCOL_OID));
    v.add(ASN1OctetString.getInstance(efDIR));
    return DLSequence.getInstance(v);
  }

  @Override
  public String getObjectIdentifier() {
    return EF_DIR_PROTOCOL_OID;
  }

  @Override
  public String getProtocolOIDString() {
    return "id-EFDIR";
  }
}
