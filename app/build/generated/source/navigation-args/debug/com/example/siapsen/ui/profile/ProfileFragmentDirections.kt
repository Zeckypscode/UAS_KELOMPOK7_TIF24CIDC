package com.example.siapsen.ui.profile

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.siapsen.R

public class ProfileFragmentDirections private constructor() {
  public companion object {
    public fun actionProfileToEdit(): NavDirections =
        ActionOnlyNavDirections(R.id.action_profile_to_edit)

    public fun actionProfileToChangePassword(): NavDirections =
        ActionOnlyNavDirections(R.id.action_profile_to_changePassword)
  }
}
