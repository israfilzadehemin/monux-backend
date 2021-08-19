package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Template;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    default Optional<Template> byIdAndUser(String templateId, User user) {
        return findByTemplateIdAndUser(templateId, user);
    }

    default List<Template> allByUser(User user) {
        return findAllByUser(user);
    }

    default List<Template> deleteByIdList(User user, List<String> templateIds) {
        return deleteTemplatesByUserAndTemplateIdIn(user, templateIds);
    }

    Optional<Template> findByTemplateIdAndUser(String templateId, User user);

    List<Template> findAllByUser(User user);

    List<Template> deleteTemplatesByUserAndTemplateIdIn(User user, List<String> templateIds);
}
