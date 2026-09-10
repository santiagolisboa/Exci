package com.examen.civique.data.repository

import android.content.Context
import androidx.core.content.edit
import com.examen.civique.domain.repository.AccountPreferenceRepository

class AndroidAccountPreferenceRepository(context: Context) : AccountPreferenceRepository {
    private val preferences = context.applicationContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    override fun hasSeenAccountProposal() = preferences.getBoolean(SEEN, false)
    override fun markAccountProposalSeen() = preferences.edit { putBoolean(SEEN, true) }

    private companion object {
        const val NAME = "account_preferences"
        const val SEEN = "account_proposal_seen"
    }
}
