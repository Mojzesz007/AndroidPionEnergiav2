package com.platform
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.security.crypto.MasterKeys
import androidx.security.crypto.EncryptedSharedPreferences
import java.io.IOException
import java.lang.NullPointerException
import java.security.GeneralSecurityException

class EncryptedPreferencesProvider(private val mContext: Context) {

    companion object {
        private const val SHARED_PREFERENCE_NAME = "ems_encrypted_data"
    }

    @get:kotlin.jvm.Throws(NullPointerException::class)
    val sharedPreferences: SharedPreferences
        get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                var masterKeyAlias: String? = null
                try {
                    masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
                    return EncryptedSharedPreferences.create(
                        SHARED_PREFERENCE_NAME,
                        masterKeyAlias,
                        mContext,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    )
                } catch (e: GeneralSecurityException) {
                    Log.w(EncryptedPreferencesProvider::class.java.canonicalName, e)
                } catch (e: IOException) {
                    Log.w(EncryptedPreferencesProvider::class.java.canonicalName, e)
                }
            } else {
                return mContext.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
            }
            return mContext.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        }

    /**
     * Funkcja zapisująca pola do Encrypted Storage
     * @param fieldName nazwa pola do zapisu
     * @param content  zawartosc pola
     */
    fun saveToEncryptedStorage(fieldName: String?, content: String?) {
        if (fieldName != null) {
            sharedPreferences.edit()
                .putString(fieldName, content)
                .apply()
        } else {
            Log.w(EncryptedPreferencesProvider::class.java.canonicalName, "")
        }
    }

    /**
     * Funkcja odczytujaca z Encrypted Storage
     * @param fieldName
     * @return pole albo null
     */
    fun readCredentials(fieldName: String?): String? {
        val prfs = sharedPreferences
        if (prfs != null) {
            if (fieldName != null) {
                return prfs.getString(fieldName, "")
            } else {
                Log.w(
                    EncryptedPreferencesProvider::class.java.canonicalName,
                    "fieldName can't be null, it cannot be read"
                )
            }
        } else {
            Log.w(
                EncryptedPreferencesProvider::class.java.canonicalName,
                "Failed to find shared preferences provider"
            )
        }
        return ""
    }
}