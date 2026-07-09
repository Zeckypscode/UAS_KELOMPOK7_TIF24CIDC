package com.example.siapsen.ui.izin

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class IzinCutiFragmentArgs(
  public val leaveType: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("leaveType", this.leaveType)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("leaveType", this.leaveType)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): IzinCutiFragmentArgs {
      bundle.setClassLoader(IzinCutiFragmentArgs::class.java.classLoader)
      val __leaveType : String?
      if (bundle.containsKey("leaveType")) {
        __leaveType = bundle.getString("leaveType")
        if (__leaveType == null) {
          throw IllegalArgumentException("Argument \"leaveType\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"leaveType\" is missing and does not have an android:defaultValue")
      }
      return IzinCutiFragmentArgs(__leaveType)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): IzinCutiFragmentArgs {
      val __leaveType : String?
      if (savedStateHandle.contains("leaveType")) {
        __leaveType = savedStateHandle["leaveType"]
        if (__leaveType == null) {
          throw IllegalArgumentException("Argument \"leaveType\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"leaveType\" is missing and does not have an android:defaultValue")
      }
      return IzinCutiFragmentArgs(__leaveType)
    }
  }
}
