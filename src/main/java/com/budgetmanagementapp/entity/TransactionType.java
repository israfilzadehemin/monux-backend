//package com.budgetmanagementapp.entity;
//
//import java.util.List;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Getter
//@Setter
//@Builder
//public class TransactionType {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private long id;
//
//    @Column(name = "transaction_type_id")
//    private String transactionTypeId;
//
//    @Column(name = "transaction_type_name")
//    private String transactionTypeName;
//
//    @OneToMany(mappedBy = "transactionType")
//    List<InOutTransaction> inOutTransactions;
//
//    @OneToMany(mappedBy = "transactionType")
//    List<TransferTransaction> transferTransactions;
//
//    @OneToMany(mappedBy = "transactionType")
//    List<DebtTransaction> debtTransactions;
//
//    @OneToMany(mappedBy = "transactionType")
//    List<InOutTemplate> inOutTemplates;
//
//    @OneToMany(mappedBy = "transactionType")
//    List<TransferTemplate> transferTemplates;
//
//    @OneToMany(mappedBy = "transactionType")
//    List<DebtTemplate> debtTemplates;
//}
