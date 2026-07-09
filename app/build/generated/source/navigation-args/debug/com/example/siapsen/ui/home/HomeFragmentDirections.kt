package com.example.siapsen.ui.home

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.siapsen.R
import kotlin.Int
import kotlin.String

public class HomeFragmentDirections private constructor() {
  private data class ActionHomeToAbsen(
    public val attendanceType: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_home_to_absen

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("attendanceType", this.attendanceType)
        return result
      }
  }

  private data class ActionHomeToIzinCuti(
    public val leaveType: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_home_to_izinCuti

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("leaveType", this.leaveType)
        return result
      }
  }

  public companion object {
    public fun actionHomeToAbsen(attendanceType: String): NavDirections =
        ActionHomeToAbsen(attendanceType)

    public fun actionHomeToIzinCuti(leaveType: String): NavDirections =
        ActionHomeToIzinCuti(leaveType)
  }
}
