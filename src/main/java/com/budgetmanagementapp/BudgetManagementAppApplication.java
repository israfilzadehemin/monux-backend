package com.budgetmanagementapp;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.entity.CustomCategory;
import com.budgetmanagementapp.entity.CustomNotification;
import com.budgetmanagementapp.entity.CustomTag;
import com.budgetmanagementapp.entity.DebtTemplate;
import com.budgetmanagementapp.repository.AccountRepository;
import com.budgetmanagementapp.repository.AccountTypeRepository;
import com.budgetmanagementapp.repository.CategoryRepository;
import com.budgetmanagementapp.repository.CurrencyRepository;
import com.budgetmanagementapp.repository.CustomCategoryRepository;
import com.budgetmanagementapp.repository.CustomNotificationRepository;
import com.budgetmanagementapp.repository.CustomTagRepository;
import com.budgetmanagementapp.repository.DebtTemplateRepository;
import com.budgetmanagementapp.repository.DebtTransactionRepository;
import com.budgetmanagementapp.repository.FeedbackRepository;
import com.budgetmanagementapp.repository.InOutTemplateRepository;
import com.budgetmanagementapp.repository.InOutTransactionRepository;
import com.budgetmanagementapp.repository.NoteRepository;
import com.budgetmanagementapp.repository.NotificationRepository;
import com.budgetmanagementapp.repository.OtpRepository;
import com.budgetmanagementapp.repository.TagRepository;
import com.budgetmanagementapp.repository.TransactionTypeRepository;
import com.budgetmanagementapp.repository.TransferTemplateRepository;
import com.budgetmanagementapp.repository.TransferTransactionRepository;
import com.budgetmanagementapp.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BudgetManagementAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetManagementAppApplication.class, args);
    }

    @Bean
    CommandLineRunner createInitialData(
            AccountRepository accountRepo, AccountTypeRepository accountTypeRepo, CategoryRepository categoryRepo,
            CurrencyRepository currencyRepo, CustomCategoryRepository customCategoryRepo,
            CustomNotificationRepository customNotificationRepo, CustomTagRepository customTagRepo,
            DebtTemplateRepository debtTemplateRepo, DebtTransactionRepository debtTransactionRepo,
            FeedbackRepository feedbackRepo, InOutTemplateRepository inOutTemplateRepo,
            InOutTransactionRepository inOutTransactionRepo, NoteRepository noteRepo,
            NotificationRepository notificationRepo, OtpRepository otpRepo,
            TagRepository tagRepo, TransactionTypeRepository transactionTypeRepo,
            TransferTemplateRepository transferTemplateRepo,
            TransferTransactionRepository transferTransactionRepo, UserRepository userRepo
    ) {

        Account cashAccount = Account.builder()
                .accountId(UUID.randomUUID().toString())
                .allowNegative(true)
                .balance(BigDecimal.valueOf(150))
                .enabled(true)
                .name("Cash")
                .showInSum(true)
                .build();

        AccountType cashAccountType = AccountType.builder()
                .accountTypeId(UUID.randomUUID().toString())
                .accountTypeName("Cash account")
                .build();

        Category food = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name("Food")
                .type("Outcome")
                .build();

        Category salary = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name("Salary")
                .type("Income")
                .build();

        Currency azn = Currency.builder()
                .currencyId(UUID.randomUUID().toString())
                .name("AZN")
                .build();

        CustomCategory customIncome = CustomCategory.builder()
                .customCategoryId(UUID.randomUUID().toString())
                .name("New custom income category")
                .type("Income")
                .build();

        CustomCategory customOutcome = CustomCategory.builder()
                .customCategoryId(UUID.randomUUID().toString())
                .name("New custom outcome category")
                .type("Outcome")
                .build();

        CustomNotification netflixNotification = CustomNotification.builder()
                .customNotificationId(UUID.randomUUID().toString())
                .name("Netflix")
                .enabled(true)
                .frequency("1-1")
                .time(LocalTime.of(18, 00))
                .build();

        CustomTag newCustomTag = CustomTag.builder()
                .customTagId(UUID.randomUUID().toString())
                .name("New custom tag")
                .visibility(true)
                .build();

        DebtTemplate debtTemplate = DebtTemplate.builder()
                .debtTemplateId(UUID.randomUUID().toString())
                .creationDate(LocalDateTime.now())
                .amount(BigDecimal.valueOf(15))
                .oppositeAccount("Emin")
                .account(cashAccount)
                .build();

        return null;

    }

}
