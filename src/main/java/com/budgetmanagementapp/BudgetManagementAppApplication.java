package com.budgetmanagementapp;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.entity.CustomCategory;
import com.budgetmanagementapp.entity.CustomNotification;
import com.budgetmanagementapp.entity.CustomTag;
import com.budgetmanagementapp.entity.Tag;
import com.budgetmanagementapp.entity.User;
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
import com.budgetmanagementapp.repository.TransferTemplateRepository;
import com.budgetmanagementapp.repository.TransferTransactionRepository;
import com.budgetmanagementapp.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
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
            TagRepository tagRepo,
            TransferTemplateRepository transferTemplateRepo,
            TransferTransactionRepository transferTransactionRepo, UserRepository userRepo
    ) {


        AccountType cashAccountType = AccountType.builder()
                .accountTypeId(UUID.randomUUID().toString())
                .accountTypeName("Cash account")
                .build();
        accountTypeRepo.save(cashAccountType);

        Currency azn = Currency.builder()
                .currencyId(UUID.randomUUID().toString())
                .name("AZN")
                .build();
        currencyRepo.save(azn);

        Account cashAccount = Account.builder()
                .accountId(UUID.randomUUID().toString())
                .allowNegative(true)
                .balance(BigDecimal.valueOf(150))
                .enabled(true)
                .name("Cash")
                .showInSum(true)
                .accountType(cashAccountType)
                .currency(azn)
                .build();
        accountRepo.save(cashAccount);

        Category food = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name("Food")
                .type("Outcome")
                .build();

        Category clothes = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name("Clothes")
                .type("Outcome")
                .build();

        Category salary = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name("Salary")
                .type("Income")
                .build();

        categoryRepo.saveAll(Arrays.asList(food, clothes, salary));

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

        customCategoryRepo.saveAll(Arrays.asList(customIncome, customOutcome));

        CustomNotification netflixNotification = CustomNotification.builder()
                .customNotificationId(UUID.randomUUID().toString())
                .name("Netflix")
                .enabled(true)
                .frequency("1-1")
                .time(LocalTime.of(18, 00))
                .build();
        customNotificationRepo.save(netflixNotification);

        CustomTag newCustomTag = CustomTag.builder()
                .customTagId(UUID.randomUUID().toString())
                .name("New custom tag")
                .visibility(true)
                .build();
        customTagRepo.save(newCustomTag);

        Tag coffeeTag = Tag.builder()
                .tagId(UUID.randomUUID().toString())
                .name("Coffee")
                .visibility(true)
                .build();
        tagRepo.save(coffeeTag);

        User user = User.builder()
                .userId(UUID.randomUUID().toString())
                .username("israfilzadehemin@gmail.com")
                .password("12345")
                .creationDateTime(LocalDateTime.now())
                .enabled(true)
                .paymentStatus(true)
                .accounts(Arrays.asList(cashAccount))
                .customCategories(Arrays.asList(customIncome))
                .customTags(Arrays.asList(newCustomTag))
                .customNotifications(Arrays.asList(netflixNotification))
                .build();
        userRepo.save(user);

        return null;

    }

}
