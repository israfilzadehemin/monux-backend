package com.budgetmanagementapp;

import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.Constant.STATUS_NOT_PAID;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.entity.CustomNotification;
import com.budgetmanagementapp.entity.CustomTag;
import com.budgetmanagementapp.entity.Role;
import com.budgetmanagementapp.entity.Tag;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.repository.AccountRepository;
import com.budgetmanagementapp.repository.AccountTypeRepository;
import com.budgetmanagementapp.repository.CategoryRepository;
import com.budgetmanagementapp.repository.CurrencyRepository;
import com.budgetmanagementapp.repository.CustomCategoryRepository;
import com.budgetmanagementapp.repository.CustomNotificationRepository;
import com.budgetmanagementapp.repository.CustomTagRepository;
import com.budgetmanagementapp.repository.RoleRepository;
import com.budgetmanagementapp.repository.TagRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.utility.CategoryType;
import com.budgetmanagementapp.utility.UserRole;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@AllArgsConstructor
public class BudgetManagementAppApplication {

    private final BCryptPasswordEncoder encoder;


    public static void main(String[] args) {
        SpringApplication.run(BudgetManagementAppApplication.class, args);
    }


    @Bean
    CommandLineRunner createInitialData(
            AccountTypeRepository accountTypeRepo, CategoryRepository categoryRepo,
            CurrencyRepository currencyRepo, CustomCategoryRepository customCategoryRepo,
            CustomNotificationRepository customNotificationRepo, CustomTagRepository customTagRepo,
            TagRepository tagRepo, RoleRepository roleRepo, AccountRepository accountRepo, UserRepository userRepo
    ) {

        AccountType cashAccountType = AccountType.builder()
                .accountTypeId(UUID.randomUUID().toString())
                .accountTypeName("Nəğd pul hesabı")
                .build();
        accountTypeRepo.save(cashAccountType);

        Currency azn = Currency.builder()
                .currencyId(UUID.randomUUID().toString())
                .name("AZN")
                .build();
        currencyRepo.save(azn);

        Category food = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name("Food")
                .type(CategoryType.OUTCOME)
                .build();

        Category clothes = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name("Clothes")
                .type(CategoryType.OUTCOME)
                .build();

        Category salary = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name("Salary")
                .type(CategoryType.INCOME)
                .build();

        categoryRepo.saveAll(Arrays.asList(food, clothes, salary));

        CustomNotification netflixNotification = CustomNotification.builder()
                .customNotificationId(UUID.randomUUID().toString())
                .name("Netflix")
                .enabled(true)
                .frequency("1-1")
                .time(LocalTime.of(18, 00))
                .build();
        customNotificationRepo.save(netflixNotification);

        Tag coffeeTag = Tag.builder()
                .tagId(UUID.randomUUID().toString())
                .name("Coffee")
                .visibility(true)
                .build();
        tagRepo.save(coffeeTag);

        Role adminRole = Role.builder()
                .roleId(UUID.randomUUID().toString())
                .name(UserRole.ADMIN.getRoleName())
                .build();

        Role userRole = Role.builder()
                .roleId(UUID.randomUUID().toString())
                .name(UserRole.USER.getRoleName())
                .build();

        roleRepo.saveAll(Arrays.asList(adminRole, userRole));

        Account cardAccount1 = Account.builder()
                .accountId(UUID.randomUUID().toString())
                .name("Card1")
                .accountType(cashAccountType)
                .currency(azn)
                .allowNegative(false)
                .balance(BigDecimal.valueOf(100))
                .enabled(true)
                .showInSum(true)
                .build();

        Account cardAccount2 = Account.builder()
                .accountId(UUID.randomUUID().toString())
                .name("Card2")
                .accountType(cashAccountType)
                .currency(azn)
                .allowNegative(true)
                .balance(BigDecimal.valueOf(100))
                .enabled(true)
                .showInSum(true)
                .build();

        Account cardAccount3 = Account.builder()
                .accountId(UUID.randomUUID().toString())
                .name("Card2")
                .accountType(cashAccountType)
                .currency(azn)
                .allowNegative(false)
                .balance(BigDecimal.valueOf(100))
                .enabled(true)
                .showInSum(true)
                .build();

        accountRepo.saveAll(Arrays.asList(cardAccount1, cardAccount2, cardAccount3));

        User user1 = User.builder()
                .userId(UUID.randomUUID().toString())
                .username("heroesofbaku@gmail.com")
                .password(encoder.encode("emin123"))
                .creationDateTime(LocalDateTime.now())
                .status(STATUS_ACTIVE)
                .paymentStatus(STATUS_NOT_PAID)
                .accounts(Arrays.asList(cardAccount1, cardAccount2))
                .build();

        User user2 = User.builder()
                .userId(UUID.randomUUID().toString())
                .username("israfilzadehemin@gmail.com")
                .password(encoder.encode("emin123"))
                .creationDateTime(LocalDateTime.now())
                .status(STATUS_ACTIVE)
                .paymentStatus(STATUS_NOT_PAID)
                .accounts(Collections.singletonList(cardAccount3))
                .build();
        userRepo.saveAll(Arrays.asList(user1, user2));

        CustomTag newCustomTag = CustomTag.builder()
                .customTagId(UUID.randomUUID().toString())
                .name("New custom tag")
                .visibility(true)
                .user(user2)
                .build();
        customTagRepo.save(newCustomTag);


        return null;

    }

}
