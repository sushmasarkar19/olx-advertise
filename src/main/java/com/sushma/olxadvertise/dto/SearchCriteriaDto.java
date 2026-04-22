package com.sushma.olxadvertise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteriaDto {
    private String searchText;
    private String category;
    private String postedBy;
    private String dateCondition;   // equals | greaterthan | lessthan | between
    private String onDate;          // yyyy-MM-dd, used with "equals"
    private String fromDate;        // yyyy-MM-dd
    private String toDate;          // yyyy-MM-dd, used with "between"
    private String sortBy;
    private int startIndex;
    private int records;
}
