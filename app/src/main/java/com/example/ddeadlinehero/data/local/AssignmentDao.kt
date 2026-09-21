package com.example.ddeadlinehero.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentDao {

    // home
    @Query("""
        SELECT * FROM assignments
        WHERE isCompleted = 0
        ORDER BY id DESC
    """)
    fun loadActiveAssignments(): Flow<List<AssignmentEntity>>

    // completed
    @Query("""
        SELECT * FROM assignments
        WHERE isCompleted = 1
        ORDER BY completedAt DESC
    """)
    fun loadCompletedAssignments(): Flow<List<AssignmentEntity>>

    // shto assignment
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignment(entity: AssignmentEntity)

    // mark completed
    @Query("""
        UPDATE assignments 
        SET isCompleted = 1, completedAt = :completedAt 
        WHERE id = :assignmentId
    """)
    suspend fun markAsCompleted(
        assignmentId: Long,
        completedAt: Long
    )

    @Query("DELETE FROM assignments WHERE id = :id")
    suspend fun deleteAssignment(id: Long)

    @Update
    suspend fun updateAssignment(entity: AssignmentEntity)

    @Query("DELETE FROM assignments WHERE isCompleted = 1")
    suspend fun deleteAllCompleted()

}
