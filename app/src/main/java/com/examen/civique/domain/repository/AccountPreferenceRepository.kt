package com.examen.civique.domain.repository

interface AccountPreferenceRepository {
    fun hasSeenAccountProposal(): Boolean
    fun markAccountProposalSeen()
}

