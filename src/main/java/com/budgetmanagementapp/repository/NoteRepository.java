package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.InOutTransaction;
import com.budgetmanagementapp.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
