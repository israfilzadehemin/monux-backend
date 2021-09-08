package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.COMMON_USERNAME;
import static com.budgetmanagementapp.utility.MsgConstant.ALL_LABELS_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DUPLICATE_LABEL_NAME_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.LABEL_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.LABEL_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.LABEL_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_LABEL_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.VISIBILITY_TOGGLED_MSG;
import static java.lang.String.format;

import com.budgetmanagementapp.builder.LabelBuilder;
import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.DuplicateLabelException;
import com.budgetmanagementapp.exception.LabelNotFoundException;
import com.budgetmanagementapp.mapper.LabelMapper;
import com.budgetmanagementapp.model.label.LabelRqModel;
import com.budgetmanagementapp.model.label.LabelRsModel;
import com.budgetmanagementapp.model.label.UpdateLabelRqModel;
import com.budgetmanagementapp.repository.LabelRepository;
import com.budgetmanagementapp.service.LabelService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomValidator;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class LabelServiceImpl implements LabelService {
    private final UserService userService;
    private final LabelRepository labelRepo;
    private final LabelBuilder labelBuilder;

    @Override
    public LabelRsModel createLabel(LabelRqModel requestBody, String username) {
        User user = userByUsername(username);
        checkDuplicate(requestBody.getLabelName(), user);
        Label label = buildLabel(requestBody, user);

        log.info(format(LABEL_CREATED_MSG, user.getUsername(), LabelMapper.INSTANCE.buildLabelResponseModel(label)));
        return LabelMapper.INSTANCE.buildLabelResponseModel(label);
    }

    @Override
    public List<LabelRsModel> getLabelsByUser(String username, boolean includeCommonLabels) {
        User user = userByUsername(username);
        User generalUser = userByUsername(COMMON_USERNAME);

        List<LabelRsModel> labels = labelsByUser(includeCommonLabels, user, generalUser);

        if (labels.isEmpty()) {
            throw new LabelNotFoundException(format(LABEL_NOT_FOUND_MSG, username));
        }

        log.info(format(ALL_LABELS_MSG, user.getUsername(), labels));
        return labels;
    }

    @Override
    public LabelRsModel updateLabel(UpdateLabelRqModel requestBody, String username) {
        Label label = byIdAndUser(requestBody.getLabelId(), username);
        updateLabelValues(requestBody, label);

        log.info(format(LABEL_UPDATED_MSG, username, LabelMapper.INSTANCE.buildLabelResponseModel(label)));
        return LabelMapper.INSTANCE.buildLabelResponseModel(label);
    }

    @Override
    public LabelRsModel toggleVisibility(String labelId, String username) {
        Label label = byIdAndUser(labelId, username);
        toggleLabelVisibility(label);

        log.info(format(VISIBILITY_TOGGLED_MSG, username, LabelMapper.INSTANCE.buildLabelResponseModel(label)));
        return LabelMapper.INSTANCE.buildLabelResponseModel(label);
    }

    @Override
    public List<Label> allByIdsAndTypeAndUser(List<String> labelIds, String type, User user) {
        return labelIds
                .stream()
                .filter(id -> byIdAndTypeAndUser(id, type, user).isPresent())
                .map(id -> byIdAndTypeAndUser(id, type, user).get())
                .collect(Collectors.toList());
    }

    private Label buildLabel(LabelRqModel requestBody, User user) {
        CustomValidator.validateCategoryType(requestBody.getLabelCategory());

        return labelRepo.save(labelBuilder.buildLabel(requestBody, user));
    }

    private User userByUsername(String username) {
        return userService.findByUsername(username);
    }

    private List<LabelRsModel> labelsByUser(boolean includeCommonLabels, User user, User generalUser) {
        return includeCommonLabels
                ? labelRepo.allByUserOrGeneralUser(user, generalUser)
                .stream()
                .map(LabelMapper.INSTANCE::buildLabelResponseModel)
                .collect(Collectors.toList())
                : labelRepo.allByUser(user)
                .stream()
                .map(LabelMapper.INSTANCE::buildLabelResponseModel)
                .collect(Collectors.toList());
    }

    private Label byIdAndUser(String labelId, String username) {
        return labelRepo.byIdAndUser(labelId, userByUsername(username))
                .orElseThrow(() -> new LabelNotFoundException(format(UNAUTHORIZED_LABEL_MSG, username, labelId)));
    }

    private Optional<Label> byIdAndTypeAndUser(String labelId, String type, User user) {
        return labelRepo.byIdAndTypeAndUsers(
                labelId,
                type,
                Arrays.asList(user, userService.findByUsername(COMMON_USERNAME)));
    }

    private void updateLabelValues(UpdateLabelRqModel requestBody, Label label) {
        CustomValidator.validateCategoryType(requestBody.getLabelCategory());
        label.setName(requestBody.getLabelName());
        label.setType(requestBody.getLabelCategory());
        labelRepo.save(label);
    }

    private void toggleLabelVisibility(Label label) {
        label.setVisibility(!label.isVisibility());
        labelRepo.save(label);
    }

    private void checkDuplicate(String labelName, User user) {
        if (labelRepo.byNameAndUser(labelName, user).isPresent()
                || labelRepo.byNameAndUser(labelName, userByUsername(COMMON_USERNAME)).isPresent()) {
            throw new DuplicateLabelException(format(DUPLICATE_LABEL_NAME_MSG, user.getUsername(), labelName));
        }
    }

}
