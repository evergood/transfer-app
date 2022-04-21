package com.bank.transfer.db

import com.bank.transfer.controller.dto.CreateAccountRequest
import com.bank.transfer.controller.dto.TransferRequest
import com.bank.transfer.domain.Account
import com.bank.transfer.domain.Transfer
import com.bank.transfer.exception.CustomException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AccountRepository(
    private val transferRepository: TransferRepository
) {
    fun create(account: CreateAccountRequest): Long {
        return transaction {
            AccountTable.insert {
                it[name] = account.name
                it[email] = account.email
                it[amount] = account.amount
            }.resultedValues?.first()?.get(AccountTable.id)?.value!!
        }
    }

    fun updateAmount(id: Long, amount: Long) {
        return transaction {
            AccountTable.update({ AccountTable.id eq id }) {
                it[AccountTable.amount] = amount
            }
        }
    }

    fun getByID(id: Long): Account? {
        return transaction {
            AccountTable.select { AccountTable.id eq id }.map { toAccount(it) }.firstOrNull()
        }
    }

    fun existsById(id: Long): Boolean {
        return transaction {
            AccountTable.select { AccountTable.id eq id }.firstOrNull() != null
        }
    }

    fun executeTransfer(transfer: TransferRequest): UUID {
        return transaction {
            val fromAccount =
                getByID(transfer.from) ?: throw CustomException(transfer.to, "Sending account doesn't exist")
            if (fromAccount.amount < transfer.amount) throw CustomException(transfer.to, "Insufficient funds")
            val toAccount =
                getByID(transfer.to) ?: throw CustomException(transfer.to, "Receiving account doesn't exist")
            updateAmount(transfer.from, fromAccount.amount - transfer.amount)
            updateAmount(transfer.to, toAccount.amount + transfer.amount)
            transferRepository.save(
                Transfer(
                    amount = transfer.amount,
                    from = transfer.from,
                    to = transfer.to
                )
            )
        }
    }

    private fun toAccount(resultRow: ResultRow): Account {
        return Account(
            id = resultRow[AccountTable.id].value,
            name = resultRow[AccountTable.name],
            email = resultRow[AccountTable.email],
            amount = resultRow[AccountTable.amount]
        )
    }
}