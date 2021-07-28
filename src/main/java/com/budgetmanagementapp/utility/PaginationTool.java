package com.budgetmanagementapp.utility;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PaginationTool<T> {
    public Pageable service(int transactionCount, String sortField, String sortDir){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("desc") ? sort.descending() : sort.ascending();
        return PageRequest.of(transactionCount-1,10, sort);
    }

}
