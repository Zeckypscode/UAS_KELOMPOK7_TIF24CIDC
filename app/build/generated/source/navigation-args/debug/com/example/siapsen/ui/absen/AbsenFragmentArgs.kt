package com.example.siapsen.ui.absen

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class AbsenFragmentArgs(
  public val attendanceType: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("attendanceType", this.attendanceType)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("attendanceType", this.attendanceType)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): AbsenFragmentArgs {
      bundle.setClassLoader(AbsenFragmentArgs::class.java.classLoader)
      val __attendanceType : String?
      if (bundle.containsKey("attendanceType")) {
        __attendanceType = bundle.getString("attendanceType")
        if (__attendanceType == null) {
          throw IllegalArgumentException("Argument \"attendanceType\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"attendanceType\" is missing and does not have an android:defaultValue")
      }
      return AbsenFragmentArgs(__attendanceType)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): AbsenFragmentArgs {
      val __attendanceType : String?
      if (savedStateHandle.contains("attendanceType")) {
        __attendanceType = savedStateHandle["attendanceType"]
        if (__attendanceType == null) {
          throw IllegalArgumentException("Argument \"attendanceType\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"attendanceType\" is missing and does not have an android:defaultValue")
      }
      return AbsenFragmentArgs(__attendanceType)
    }
  }
}
