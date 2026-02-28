package it.giaquinto.kmrtd.kmrtdexampleapp.ui.activity

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import it.giaquinto.kmrtd.kmrtdexampleapp.framework.KmrtdManager
import it.giaquinto.kmrtd.kmrtdexampleapp.framework.KmrtdState
import it.giaquinto.kmrtd.kmrtdexampleapp.framework.LibraryBenchmark
import it.giaquinto.kmrtd.kmrtdexampleapp.model.MRZInput
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.navigation.KmrtdNavGraph
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.theme.KmrtdExampleAppTheme
import kotlinx.coroutines.launch
import org.jmrtd.PassportService
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "KmrtdExampleApp"
    }

    private var nfcAdapter: NfcAdapter? = null
    private val kmrtdManager: KmrtdManager = KmrtdManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KmrtdExampleAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    KmrtdNavGraph(
                        padding = innerPadding,
                        showGithub = { openUrl("https://github.com/giaquinto/kmrtd-example-app".toUri()) },
                        showLinkedin = { openUrl("https://www.linkedin.com/in/giaquale/".toUri()) },
                        jmrtd = {
                            readNfc(it)
                        },
                        kmrtd = {
                            readNfc(it)
                        }
                    )
                }
            }
        }

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC non disponibile", Toast.LENGTH_LONG).show()
            finish()
            return
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            kmrtdManager.state.collect {
                when (it) {
                    is KmrtdState.Success -> {
                        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Success: ${it.kmrtdResultBuilder}")
                        nfcAdapter?.disableReaderMode(this@MainActivity)
                    }
                    is KmrtdState.Error -> {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
                        Log.e(TAG, "Error: ${it.error}, Cause: ${it.cause}")
                        nfcAdapter?.disableReaderMode(this@MainActivity)
                    }
                    else -> Log.d(TAG, "State: $it")
                }
            }
        }
    }

    fun readNfc(mrzInput: MRZInput) {
        nfcAdapter?.enableReaderMode(
            this@MainActivity,
            { tag ->
                IsoDep.get(tag)?.let {
                    lifecycleScope.launch {
                        kmrtdManager.start(it, mrzInput, false)
                    }
                }
            },
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_NFC_B,
            Bundle().apply {
                putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)
            }
        )
    }


    private fun enableNfcForegroundDispatch() {
        val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_MUTABLE
        )
        val techFilter = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        nfcAdapter?.enableForegroundDispatch(
            this,
            pendingIntent,
            arrayOf(techFilter),
            arrayOf(arrayOf(IsoDep::class.java.name))
        )
    }

    fun openUrl(uri: Uri) =
        startActivity(Intent(Intent.ACTION_VIEW, uri))
}