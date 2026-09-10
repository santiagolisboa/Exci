package com.examen.civique.ui.account

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertEquals
import org.junit.Test
import com.examen.civique.domain.repository.AccountPreferenceRepository
import com.examen.civique.data.repository.UnavailableAuthRepository

class AccountValidationTest {
    @Test fun validCredentialsPassValidation() {
        assertNull(AccountViewModel.validateEmail("personne@example.fr"))
        assertNull(AccountViewModel.validatePassword("motdepasse-solide"))
    }

    @Test fun invalidCredentialsHaveReadableErrors() {
        assertNotNull(AccountViewModel.validateEmail("incorrect"))
        assertNotNull(AccountViewModel.validatePassword("court"))
    }

    @Test fun decliningProposalMakesItStayDismissed() {
        val preferences = MemoryPreferences()
        val viewModel = AccountViewModel(UnavailableAuthRepository(), preferences)
        assertEquals(true, viewModel.shouldShowProposal())
        viewModel.markProposalSeen()
        assertEquals(false, viewModel.shouldShowProposal())
    }

    private class MemoryPreferences : AccountPreferenceRepository {
        private var seen = false
        override fun hasSeenAccountProposal() = seen
        override fun markAccountProposalSeen() { seen = true }
    }
}
