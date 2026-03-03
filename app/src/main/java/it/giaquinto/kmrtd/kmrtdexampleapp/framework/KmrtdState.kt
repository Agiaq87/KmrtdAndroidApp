package it.giaquinto.kmrtd.kmrtdexampleapp.framework

import it.giaquinto.kmrtd.kmrtdexampleapp.model.MRZInput

sealed class KmrtdState {
    object Idle : KmrtdState()
    object CardService : KmrtdState()
    object PassportService : KmrtdState()
    object SecurityInfos : KmrtdState()
    object PaceInfos : KmrtdState()
    object BACKey : KmrtdState()
    object PACEKey : KmrtdState()
    object PACEConnection : KmrtdState()
    object PACESuccess : KmrtdState()
    object FallbackToBAC : KmrtdState()
    object BACSuccess : KmrtdState()
    object SODFile : KmrtdState()
    object DG1File : KmrtdState()
    object DG2File : KmrtdState()
    object DG7File : KmrtdState()

    data class Success(
        val withPACE: Boolean,
        val kmrtdResultBuilder: KmrtdResultBuilder,
        val mrzInput: MRZInput
    ) : KmrtdState()

    data class Error(
        val error: KmrtdError,
        val cause: Throwable? = null
    ) : KmrtdState()
}
