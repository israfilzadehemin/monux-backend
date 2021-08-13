package com.budgetmanagementapp;

import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.Constant.STATUS_NOT_PAID;
import static com.budgetmanagementapp.utility.Constant.STATUS_PAID;

import com.budgetmanagementapp.entity.*;
import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.repository.*;
import com.budgetmanagementapp.utility.CategoryType;
import com.budgetmanagementapp.utility.UserRole;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
            CurrencyRepository currencyRepo, LabelRepository labelRepo, RoleRepository roleRepo,
            AccountRepository accountRepo, UserRepository userRepo, NotificationRepository notificationRepo,
            TransactionRepository transactionRepo
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
        accountRepo.save(cardAccount1);

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
        accountRepo.save(cardAccount2);

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
        accountRepo.save(cardAccount3);

        User user1 = User.builder()
                .userId(UUID.randomUUID().toString())
                .username("heroesofbaku@gmail.com")
                .password(encoder.encode("emin123"))
                .dateTime(LocalDateTime.now())
                .status(STATUS_ACTIVE)
                .paymentStatus(STATUS_NOT_PAID)
                .accounts(Collections.singletonList(cardAccount1))
                .build();


        User user2 = User.builder()
                .userId(UUID.randomUUID().toString())
                .username("israfilzadehemin@gmail.com")
                .password(encoder.encode("emin123"))
                .dateTime(LocalDateTime.now())
                .status(STATUS_ACTIVE)
                .paymentStatus(STATUS_NOT_PAID)
                .accounts(Collections.singletonList(cardAccount3))
                .build();

        User generalUser = User.builder()
                .userId(UUID.randomUUID().toString())
                .username("commonUser")
                .password(encoder.encode("emin123"))
                .dateTime(LocalDateTime.now())
                .status(STATUS_ACTIVE)
                .paymentStatus(STATUS_PAID)
                .build();

        userRepo.saveAll(Arrays.asList(user1, user2, generalUser));

        Category food = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name("Food")
                .type(CategoryType.OUTGOING.name())
                .user(generalUser)
                .build();

        Category clothes = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name("Clothes")
                .type(CategoryType.OUTGOING.name())
                .user(generalUser)
                .build();

        Category salary = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name("Salary")
                .type(CategoryType.INCOME.name())
                .user(generalUser)
                .build();

        categoryRepo.saveAll(Arrays.asList(food, clothes, salary));

        Label coffeeLabel = Label.builder()
                .labelId(UUID.randomUUID().toString())
                .name("Coffee")
                .visibility(true)
                .user(generalUser)
                .build();
        labelRepo.save(coffeeLabel);

        Label newLabel = Label.builder()
                .labelId(UUID.randomUUID().toString())
                .name("New custom label")
                .visibility(true)
                .user(user2)
                .build();
        labelRepo.save(newLabel);

        Notification netflixNotification = Notification.builder()
                .notificationId(UUID.randomUUID().toString())
                .name("Netflix")
                .enabled(true)
                .frequency("1-1")
                .time(LocalTime.of(18, 00))
                .user(user1)
                .build();
        notificationRepo.save(netflixNotification);

        return null;
    }

}
