package com.example.android.personalgrowthjournal.Authentication

import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import android.util.Log


// Source for much of this: https://dev.to/adammc331/android-fingerprint-authentication-tutorial-2835

class FingerprintController(
    private val fingerprintManager: FingerprintManagerCompat,
    private val callback: Callback
) : FingerprintManagerCompat.AuthenticationCallback() {

    private var cancellationSignal: CancellationSignal? = null
    private var selfCancelled = false

    private val isFingerprintAuthAvailable: Boolean
        get() = fingerprintManager.isHardwareDetected && fingerprintManager.hasEnrolledFingerprints()

    fun startListening(cryptoObject: FingerprintManagerCompat.CryptoObject) {
        if (!isFingerprintAuthAvailable) return

        cancellationSignal = CancellationSignal()
        selfCancelled = false
        fingerprintManager.authenticate(cryptoObject, 0, cancellationSignal, this, null)
    }

    fun stopListening() {
        cancellationSignal?.let {
            selfCancelled = true
            it.cancel()
            cancellationSignal = null
        }
    }

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
        callback.onError(errString as String)
    }

    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
        Log.i("FingerprintController", "Authenticated")
        callback.onAuthenticated()
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {

    }

    override fun onAuthenticationFailed() {

    }

    /**
     * A callback that allows a class to be updated when fingerprint authentication is complete.
     */
    interface Callback {
        /**
         * Callback method used for a successful fingerprint authentication.
         */
        fun onAuthenticated()

        /**
         * Callback method used if there is any error authenticating the fingerprint.
         */
        fun onError(err: String)
    }


}