package com.bank.transfer.db

import com.bank.transfer.domain.Transfer
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TransferRepository {
    fun save(transfer: Transfer): UUID {
        return transaction {
            TransferTable.insert {
                it[id] = transfer.id
                it[amount] = transfer.amount
                it[from] = transfer.from
                it[to] = transfer.to
                it[date] = transfer.date
            }.resultedValues?.first()?.get(TransferTable.id)?.value!!
        }
    }

    fun getByUuid(uuid: UUID): Transfer? {
        return transaction {
            TransferTable.select { TransferTable.id eq uuid }.map { toTransfer(it) }.firstOrNull()
        }
    }

    private fun toTransfer(resultRow: ResultRow): Transfer {
        return Transfer(
            id = resultRow[TransferTable.id].value,
            from = resultRow[TransferTable.from],
            to = resultRow[TransferTable.to],
            amount = resultRow[TransferTable.amount],
            date = resultRow[TransferTable.date]
        )
    }
}