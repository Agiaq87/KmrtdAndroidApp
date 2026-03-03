package it.giaquinto.kmrtd.kmrtdexampleapp.ui.screen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.giaquinto.kmrtd.kmrtdexampleapp.framework.KmrtdResultBuilder

/**
 * Colori per il confronto:
 * - Verde: KMRTD ha prodotto un valore NON presente in JMRTD (valore extra)
 * - Rosso: JMRTD ha prodotto un valore che KMRTD NON ha (valore mancante)
 * - Bianco: entrambi hanno lo stesso stato (entrambi presenti o entrambi assenti)
 */
private val KmrtdExtra = Color(0xFF4CAF50)       // Verde
private val KmrtdMissing = Color(0xFFF44336)      // Rosso
private val KmrtdMatch = Color.White              // Bianco

private val HeaderBg = Color(0xFF1A1A2E)
private val RowBgEven = Color(0xFF16213E)
private val RowBgOdd = Color(0xFF0F3460)

/**
 * Stato di confronto per un singolo campo
 */
private enum class ComparisonStatus {
    MATCH,    // Entrambi hanno o entrambi non hanno il valore
    EXTRA,    // Solo KMRTD ha il valore
    MISSING   // Solo JMRTD ha il valore (KMRTD mancante)
}

/**
 * Riga di confronto: attributo, valore kmrtd, valore jmrtd, stato
 */
private data class ComparisonRow(
    val attribute: String,
    val kmrtdValue: String,
    val jmrtdValue: String,
    val status: ComparisonStatus
)

/**
 * Determina lo stato di confronto tra due valori nullable.
 * - Se entrambi null/vuoti o entrambi presenti → MATCH
 * - Se solo kmrtd ha valore → EXTRA (verde)
 * - Se solo jmrtd ha valore → MISSING (rosso)
 */
private fun compareValues(kmrtdVal: Any?, jmrtdVal: Any?): ComparisonStatus {
    val kmrtdPresent = isPresent(kmrtdVal)
    val jmrtdPresent = isPresent(jmrtdVal)

    return when {
        kmrtdPresent && !jmrtdPresent -> ComparisonStatus.EXTRA
        !kmrtdPresent && jmrtdPresent -> ComparisonStatus.MISSING
        else -> ComparisonStatus.MATCH
    }
}

private fun isPresent(value: Any?): Boolean = when (value) {
    null -> false
    is String -> value.isNotBlank()
    is Bitmap -> true
    is Boolean -> true // boolean è sempre "presente"
    is Map<*, *> -> value.isNotEmpty()
    is Set<*> -> value.isNotEmpty()
    else -> true
}

private fun toDisplayString(value: Any?): String = when (value) {
    null -> "—"
    is Bitmap -> "Bitmap(${value.width}x${value.height})"
    is Boolean -> if (value) "✓" else "✗"
    is Map<*, *> -> if (value.isEmpty()) "—" else "${value.size} entries"
    is Set<*> -> if (value.isEmpty()) "—" else value.joinToString(", ")
    is String -> value.ifBlank { "—" }
    else -> value.toString()
}

/**
 * Costruisce la lista completa delle righe di confronto
 */
private fun buildComparisonRows(
    kmrtd: KmrtdResultBuilder,
    jmrtd: KmrtdResultBuilder
): List<ComparisonRow> {
    data class Field(val name: String, val kmrtdVal: Any?, val jmrtdVal: Any?)

    val fields = listOf(
        // === Documento ===
        Field("documentType", kmrtd.documentType, jmrtd.documentType),
        Field("documentNumber", kmrtd.documentNumber, jmrtd.documentNumber),
        Field("issuingState", kmrtd.issuingState, jmrtd.issuingState),
        Field("issuingAuthority", kmrtd.issuingAuthority, jmrtd.issuingAuthority),
        Field("releasingNation", kmrtd.releasingNation, jmrtd.releasingNation),
        Field("releasingNationCode", kmrtd.releasingNationCode, jmrtd.releasingNationCode),
        Field("nationality", kmrtd.nationality, jmrtd.nationality),

        // === Persona ===
        Field("personName", kmrtd.personName, jmrtd.personName),
        Field("firstName", kmrtd.firstName, jmrtd.firstName),
        Field("lastName", kmrtd.lastName, jmrtd.lastName),
        Field("otherNames", kmrtd.otherNames, jmrtd.otherNames),
        Field("gender", kmrtd.gender, jmrtd.gender),
        Field("dateOfBirthString", kmrtd.dateOfBirthString, jmrtd.dateOfBirthString),
        Field("personBirthdayString", kmrtd.personBirthdayString, jmrtd.personBirthdayString),
        Field("cityOfBirth", kmrtd.cityOfBirth, jmrtd.cityOfBirth),
        Field("stateOfBirth", kmrtd.stateOfBirth, jmrtd.stateOfBirth),
        Field("title", kmrtd.title, jmrtd.title),
        Field("profession", kmrtd.profession, jmrtd.profession),

        // === Contatti / Indirizzo ===
        Field("address", kmrtd.address, jmrtd.address),
        Field("city", kmrtd.city, jmrtd.city),
        Field("state", kmrtd.state, jmrtd.state),
        Field("telephone", kmrtd.telephone, jmrtd.telephone),

        // === Date documento ===
        Field("expirationDateString", kmrtd.expirationDateString, jmrtd.expirationDateString),
        Field("dateOfIssueString", kmrtd.dateOfIssueString, jmrtd.dateOfIssueString),
        Field(
            "personalizationDateString",
            kmrtd.personalizationDateString,
            jmrtd.personalizationDateString
        ),
        Field("validFromString", kmrtd.validFromString, jmrtd.validFromString),
        Field("validToString", kmrtd.validToString, jmrtd.validToString),

        // === Certificato ===
        Field("emitter", kmrtd.emitter, jmrtd.emitter),
        Field("holder", kmrtd.holder, jmrtd.holder),
        Field("serialNumber", kmrtd.serialNumber, jmrtd.serialNumber),
        Field("publicKeyAlgorithm", kmrtd.publicKeyAlgorithm, jmrtd.publicKeyAlgorithm),
        Field("signatureAlgorithm", kmrtd.signatureAlgorithm, jmrtd.signatureAlgorithm),
        Field("certificateFingerprint", kmrtd.certificateFingerprint, jmrtd.certificateFingerprint),

        // === Biometria / Immagini ===
        Field("personImage", kmrtd.personImage, jmrtd.personImage),
        Field("eyeColor", kmrtd.eyeColor, jmrtd.eyeColor),
        Field("hairColor", kmrtd.hairColor, jmrtd.hairColor),
        Field("imageColorSpace", kmrtd.imageColorSpace, jmrtd.imageColorSpace),
        Field("imageDataType", kmrtd.imageDataType, jmrtd.imageDataType),
        Field("faceImageDataType", kmrtd.faceImageDataType, jmrtd.faceImageDataType),
        Field("sourceType", kmrtd.sourceType, jmrtd.sourceType),
        Field("expression", kmrtd.expression, jmrtd.expression),
        Field("feature", kmrtd.feature, jmrtd.feature),
        Field("signatureImage", kmrtd.signatureImage, jmrtd.signatureImage),
        Field("signatureImageBase64", kmrtd.signatureImageBase64, jmrtd.signatureImageBase64),

        // === Vari ===
        Field("taxId", kmrtd.taxId, jmrtd.taxId),
        Field("taxOrExitRequirements", kmrtd.taxOrExitRequirements, jmrtd.taxOrExitRequirements),
        Field("custody", kmrtd.custody, jmrtd.custody),
        Field("personalNotes", kmrtd.personalNotes, jmrtd.personalNotes),
        Field("observations", kmrtd.observations, jmrtd.observations),
        Field("systemSerialNumber", kmrtd.systemSerialNumber, jmrtd.systemSerialNumber),
        Field("sodBase64", kmrtd.sodBase64, jmrtd.sodBase64),

        // === DataGroup Hashes ===
        Field("dataGroupHashes", kmrtd.dataGroupHashes, jmrtd.dataGroupHashes),

        // === Autenticazione ===
        Field("passiveAuthentication", kmrtd.passiveAuthentication, jmrtd.passiveAuthentication),
        Field("activeAuthentication", kmrtd.activeAuthentication, jmrtd.activeAuthentication),
        Field("chipAuthentication", kmrtd.chipAuthentication, jmrtd.chipAuthentication),

        // === Timing ===
        Field("isSuccess", kmrtd.isSuccess, jmrtd.isSuccess),
        Field("initialTimeStamp", kmrtd.initialTimeStamp, jmrtd.initialTimeStamp),
        Field("elapsedTimeStamp", kmrtd.elapsedTimeStamp, jmrtd.elapsedTimeStamp),
    )

    return fields.map { (name, kVal, jVal) ->
        ComparisonRow(
            attribute = name,
            kmrtdValue = toDisplayString(kVal),
            jmrtdValue = toDisplayString(jVal),
            status = compareValues(kVal, jVal)
        )
    }
}

// ============================================================================
// Composables
// ============================================================================

@Composable
fun ComparisonScreen(
    kmrtdResult: KmrtdResultBuilder,
    jmrtdResult: KmrtdResultBuilder,
    modifier: Modifier = Modifier
) {
    val rows = remember(kmrtdResult, jmrtdResult) {
        buildComparisonRows(kmrtdResult, jmrtdResult)
    }

    val summary = remember(rows) {
        Triple(
            rows.count { it.status == ComparisonStatus.MATCH },
            rows.count { it.status == ComparisonStatus.EXTRA },
            rows.count { it.status == ComparisonStatus.MISSING }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A1A))
    ) {
        // Header fisso con summary
        ComparisonHeader(
            matchCount = summary.first,
            extraCount = summary.second,
            missingCount = summary.third
        )

        // Immagini a confronto (se presenti)
        if (kmrtdResult.personImage != null || jmrtdResult.personImage != null) {
            ImageComparisonRow(
                kmrtdImage = kmrtdResult.personImage,
                jmrtdImage = jmrtdResult.personImage
            )
        }

        // Tabella di confronto scrollabile
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            // Header tabella
            item {
                TableHeader()
            }

            items(rows) { row ->
                val index = rows.indexOf(row)
                ComparisonTableRow(
                    row = row,
                    isEven = index % 2 == 0
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ComparisonHeader(
    matchCount: Int,
    extraCount: Int,
    missingCount: Int
) {
    Surface(
        color = HeaderBg,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "KMRTD vs JMRTD",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                LegendChip(
                    color = KmrtdMatch,
                    label = "Match: $matchCount"
                )
                LegendChip(
                    color = KmrtdExtra,
                    label = "Extra KMRTD: $extraCount"
                )
                LegendChip(
                    color = KmrtdMissing,
                    label = "Missing: $missingCount"
                )
            }
        }
    }
}

@Composable
private fun LegendChip(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(color)
        )
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 12.sp
        )
    }
}

@Composable
private fun ImageComparisonRow(
    kmrtdImage: Bitmap?,
    jmrtdImage: Bitmap?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("KMRTD", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            if (kmrtdImage != null) {
                Image(
                    bitmap = kmrtdImage.asImageBitmap(),
                    contentDescription = "KMRTD person image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(KmrtdMissing.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("—", color = KmrtdMissing, fontSize = 24.sp)
                }
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("JMRTD", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            if (jmrtdImage != null) {
                Image(
                    bitmap = jmrtdImage.asImageBitmap(),
                    contentDescription = "JMRTD person image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("—", color = Color.Gray, fontSize = 24.sp)
                }
            }
        }
    }
}

@Composable
private fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderBg)
            .padding(vertical = 10.dp, horizontal = 8.dp)
            .height(IntrinsicSize.Min)
    ) {
        Text(
            text = "Attributo",
            modifier = Modifier.weight(1.2f),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
        Text(
            text = "KMRTD",
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "JMRTD",
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }

    HorizontalDivider(color = Color.White.copy(alpha = 0.3f))
}

@Composable
private fun ComparisonTableRow(
    row: ComparisonRow,
    isEven: Boolean
) {
    val textColor = when (row.status) {
        ComparisonStatus.MATCH -> KmrtdMatch
        ComparisonStatus.EXTRA -> KmrtdExtra
        ComparisonStatus.MISSING -> KmrtdMissing
    }

    val rowBg = if (isEven) RowBgEven else RowBgOdd

    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(rowBg)
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .height(IntrinsicSize.Min)
    ) {
        // Attributo
        Text(
            text = row.attribute,
            modifier = Modifier.weight(1.2f),
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        // Valore KMRTD (scrollabile orizzontalmente per valori lunghi)
        Box(modifier = Modifier.weight(1f)) {
            Text(
                text = row.kmrtdValue,
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                color = textColor,
                fontSize = 11.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }

        // Valore JMRTD
        Box(modifier = Modifier.weight(1f)) {
            Text(
                text = row.jmrtdValue,
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                color = textColor,
                fontSize = 11.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }

    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
}