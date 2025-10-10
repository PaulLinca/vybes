package com.vybes.dto.request;

import com.vybes.model.AnswerType;
import com.vybes.model.ChallengeType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitChallengeRequestDTO {

    @NotBlank(message = "Question is required")
    @Size(min = 5, max = 500, message = "Question must be between 5 and 500 characters")
    private String question;

    @NotNull(message = "Challenge type is required")
    private ChallengeType challengeType;

    @NotNull(message = "Answer type is required")
    private AnswerType answerType;

    @Valid private List<String> options;

    @AssertTrue(message = "POLL challenges must have at least 2 options")
    public boolean isPollValidation() {
        if (challengeType == ChallengeType.POLL) {
            return options != null && options.size() >= 2;
        }
        return true;
    }

    @AssertTrue(message = "USER_SUBMISSIONS challenges should not have predefined options")
    public boolean isUserSubmissionValidation() {
        if (challengeType == ChallengeType.USER_SUBMISSIONS) {
            return options == null || options.isEmpty();
        }
        return true;
    }
}
