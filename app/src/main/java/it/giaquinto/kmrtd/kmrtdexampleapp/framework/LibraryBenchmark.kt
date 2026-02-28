package it.giaquinto.kmrtd.kmrtdexampleapp.framework

import android.util.Log
import org.kmrtd.lds.SODFile
import org.kmrtd.lds.icao.COMFile
import org.kmrtd.lds.icao.DG14File
import org.kmrtd.lds.icao.DG15File
import org.kmrtd.lds.icao.DG1File
import org.kmrtd.lds.icao.DG2File
import java.io.ByteArrayInputStream
import java.io.InputStream

/**
 * Strumento di benchmark per confrontare le prestazioni di parsing
 * tra la libreria originale JMRTD (Java) e kmrtd (Kotlin port).
 *
 * Misura:
 *   - Tempo di parsing di ogni Data Group
 *   - Memoria allocata (approssimata)
 *   - Confronto diretto jmrtd vs kmrtd
 *
 * Uso:
 *   // Dopo aver letto i bytes dal chip NFC:
 *   val benchmark = LibraryBenchmark()
 *   benchmark.addSample("COM", comBytes)
 *   benchmark.addSample("DG1", dg1Bytes)
 *   benchmark.addSample("DG2", dg2Bytes)
 *   benchmark.addSample("SOD", sodBytes)
 *
 *   val report = benchmark.run(iterations = 10)
 *   Log.i("Benchmark", report.toFormattedString())
 *
 * Nota: Chiama addSample() con i bytes RAW letti dal chip
 *       (tag + length + value, cioè il TLV completo).
 *       Puoi ottenere i bytes con: passportService.getInputStream(EF_xxx).readBytes()
 */
class LibraryBenchmark {

    companion object {
        private const val TAG = "LibraryBenchmark"

        // Warmup per stabilizzare JIT
        private const val DEFAULT_WARMUP = 3
    }

    /**
     * Sample da testare: nome leggibile + bytes grezzi del data group.
     */
    private data class Sample(
        val name: String,
        val rawBytes: ByteArray
    )

    /**
     * Risultato di un singolo parsing.
     */
    data class ParseTiming(
        val label: String,          // es. "jmrtd" o "kmrtd"
        val dataGroup: String,      // es. "COM", "DG1"
        val inputSizeBytes: Int,
        val times: List<Long>,      // tempi in microsecondi per ogni iterazione
        val errors: List<String>    // errori se il parsing fallisce
    ) {
        val medianMicros: Long get() = times.sorted().let { it[it.size / 2] }
        val meanMicros: Long get() = if (times.isEmpty()) 0L else times.sum() / times.size
        val minMicros: Long get() = times.minOrNull() ?: 0L
        val maxMicros: Long get() = times.maxOrNull() ?: 0L
        val p95Micros: Long get() = times.sorted().let { it[(it.size * 0.95).toInt().coerceAtMost(it.size - 1)] }
        val successRate: Double get() = if (times.size + errors.size == 0) 0.0
        else times.size.toDouble() / (times.size + errors.size)
    }

    /**
     * Report completo del benchmark.
     */
    data class BenchmarkReport(
        val iterations: Int,
        val warmupIterations: Int,
        val results: List<ParseTiming>
    ) {
        /**
         * Formatta il report come tabella leggibile.
         */
        fun toFormattedString(): String = buildString {
            appendLine("╔══════════════════════════════════════════════════════════════════╗")
            appendLine("║                    BENCHMARK REPORT                             ║")
            appendLine("║  Iterations: $iterations (warmup: $warmupIterations)${" ".repeat(40 - iterations.toString().length - warmupIterations.toString().length)}║")
            appendLine("╠══════════════════════════════════════════════════════════════════╣")
            appendLine("║ DG     │ Lib    │ Size    │ Median  │ Mean    │ p95     │ Succ  ║")
            appendLine("╠══════════════════════════════════════════════════════════════════╣")

            // Raggruppa per data group per un confronto side-by-side
            val byDg = results.groupBy { it.dataGroup }
            for ((dg, timings) in byDg) {
                for (timing in timings) {
                    val sizeKb = "%.1fK".format(timing.inputSizeBytes / 1024.0)
                    val succPct = "%.0f%%".format(timing.successRate * 100)
                    appendLine(
                        "║ %-6s │ %-6s │ %7s │ %5dμs │ %5dμs │ %5dμs │ %5s ║".format(
                            dg, timing.label, sizeKb,
                            timing.medianMicros, timing.meanMicros,
                            timing.p95Micros, succPct
                        )
                    )
                }
                appendLine("╟──────────────────────────────────────────────────────────────────╢")
            }

            // Riepilogo comparativo
            appendLine("║                     CONFRONTO DIRETTO                           ║")
            appendLine("╠══════════════════════════════════════════════════════════════════╣")
            for ((dg, timings) in byDg) {
                val jmrtdTiming = timings.find { it.label == "jmrtd" }
                val kmrtdTiming = timings.find { it.label == "kmrtd" }
                if (jmrtdTiming != null && kmrtdTiming != null &&
                    jmrtdTiming.medianMicros > 0 && kmrtdTiming.medianMicros > 0
                ) {
                    val ratio = kmrtdTiming.medianMicros.toDouble() / jmrtdTiming.medianMicros
                    val faster = if (ratio < 1.0) "kmrtd" else "jmrtd"
                    val pct = "%.1f%%".format(Math.abs(1.0 - ratio) * 100)
                    appendLine("║ %-6s │ %s più veloce di %s ${" ".repeat(30 - faster.length - pct.length)}║".format(dg, faster, pct))
                }
            }
            appendLine("╚══════════════════════════════════════════════════════════════════╝")
        }

        /**
         * Ritorna il report come Map serializzabile (per logging/analytics).
         */
        fun toMap(): Map<String, Any> = mapOf(
            "iterations" to iterations,
            "warmup" to warmupIterations,
            "results" to results.map { timing ->
                mapOf(
                    "label" to timing.label,
                    "dataGroup" to timing.dataGroup,
                    "inputSizeBytes" to timing.inputSizeBytes,
                    "medianMicros" to timing.medianMicros,
                    "meanMicros" to timing.meanMicros,
                    "minMicros" to timing.minMicros,
                    "maxMicros" to timing.maxMicros,
                    "p95Micros" to timing.p95Micros,
                    "successRate" to timing.successRate
                )
            }
        )
    }

    // ---

    private val samples = mutableListOf<Sample>()

    /**
     * Registra un campione di bytes da usare nel benchmark.
     *
     * @param name  Nome del data group (es. "COM", "DG1", "DG2", "SOD")
     * @param bytes Bytes grezzi TLV letti dal chip
     */
    fun addSample(name: String, bytes: ByteArray) {
        samples.add(Sample(name, bytes.copyOf()))
        Log.d(TAG, "Sample aggiunto: $name (${bytes.size} bytes)")
    }

    /**
     * Esegue il benchmark su tutti i campioni registrati.
     *
     * @param iterations Numero di iterazioni per ogni test
     * @param warmup     Numero di iterazioni di warmup (scartate)
     *
     * @return BenchmarkReport con tutti i risultati
     */
    fun run(
        iterations: Int = 10,
        warmup: Int = DEFAULT_WARMUP
    ): BenchmarkReport {
        require(samples.isNotEmpty()) { "Nessun sample registrato. Chiama addSample() prima di run()." }

        val results = mutableListOf<ParseTiming>()

        for (sample in samples) {
            Log.i(TAG, "Benchmarking ${sample.name} (${sample.rawBytes.size} bytes)...")

            // Benchmark JMRTD (Java originale)
            val jmrtdTiming = benchmarkParsing(
                label = "jmrtd",
                dataGroup = sample.name,
                rawBytes = sample.rawBytes,
                iterations = iterations,
                warmup = warmup,
                parser = { inputStream -> parseWithJmrtd(sample.name, inputStream) }
            )
            results.add(jmrtdTiming)

            // Benchmark kmrtd (Kotlin port)
            val kmrtdTiming = benchmarkParsing(
                label = "kmrtd",
                dataGroup = sample.name,
                rawBytes = sample.rawBytes,
                iterations = iterations,
                warmup = warmup,
                parser = { inputStream -> parseWithKmrtd(sample.name, inputStream) }
            )
            results.add(kmrtdTiming)
        }

        return BenchmarkReport(
            iterations = iterations,
            warmupIterations = warmup,
            results = results
        )
    }

    /**
     * Esegue N iterazioni di parsing e raccoglie i tempi.
     */
    private fun benchmarkParsing(
        label: String,
        dataGroup: String,
        rawBytes: ByteArray,
        iterations: Int,
        warmup: Int,
        parser: (InputStream) -> Any?
    ): ParseTiming {
        val times = mutableListOf<Long>()
        val errors = mutableListOf<String>()

        // Warmup (risultati scartati)
        repeat(warmup) {
            try {
                parser(ByteArrayInputStream(rawBytes))
            } catch (_: Exception) {}
        }

        // Forza GC prima delle misurazioni reali
        System.gc()
        Thread.sleep(50)

        // Misurazioni
        repeat(iterations) { i ->
            val input = ByteArrayInputStream(rawBytes)
            val startNanos = System.nanoTime()
            try {
                val result = parser(input)
                val elapsedMicros = (System.nanoTime() - startNanos) / 1_000
                times.add(elapsedMicros)
                Log.v(TAG, "  [$label] $dataGroup iter $i: ${elapsedMicros}μs")
            } catch (e: Exception) {
                errors.add("iter $i: ${e.javaClass.simpleName}: ${e.message}")
                Log.w(TAG, "  [$label] $dataGroup iter $i: ERRORE ${e.message}")
            }
        }

        return ParseTiming(
            label = label,
            dataGroup = dataGroup,
            inputSizeBytes = rawBytes.size,
            times = times,
            errors = errors
        )
    }

    // --- Parser wrappers ---

    /**
     * Parsing con JMRTD originale (Java).
     *
     * NOTA: Importa dal package org.jmrtd.lds.*
     * Adatta gli import se i package sono diversi nel tuo progetto.
     */
    private fun parseWithJmrtd(name: String, inputStream: InputStream): Any? {
        return when (name.uppercase()) {
            "COM"  -> COMFile(inputStream)
            "DG1"  -> DG1File(inputStream)
            "DG2"  -> DG2File(inputStream)
            "DG14" -> DG14File(inputStream)
            "DG15" -> DG15File(inputStream)
            "SOD"  -> SODFile(inputStream)
            else   -> {
                Log.w(TAG, "Parser jmrtd non disponibile per: $name")
                null
            }
        }
    }

    /**
     * Parsing con kmrtd (Kotlin port).
     *
     * NOTA: Importa dal package kmrtd.lds.*
     * Adatta gli import se i package sono diversi nel tuo progetto.
     */
    private fun parseWithKmrtd(name: String, inputStream: InputStream): Any? {
        /*return when (name.uppercase()) {
            "COM"  -> kmrtd.lds.icao.COMFile(inputStream)
            "DG1"  -> kmrtd.lds.icao.DG1File(inputStream)
            "DG2"  -> kmrtd.lds.icao.DG2File(inputStream)
            "DG14" -> kmrtd.lds.icao.DG14File(inputStream)
            "DG15" -> kmrtd.lds.icao.DG15File(inputStream)
            "SOD"  -> kmrtd.lds.SODFile(inputStream)
            else   -> {
                Log.w(TAG, "Parser kmrtd non disponibile per: $name")
                null
            }
        }*/
        return null
    }
}
