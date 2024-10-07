package com.utbm.da50.freelyform.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TypeRule {
        @JsonProperty("max_length")
        MAX_LENGTH,

        @JsonProperty("min_length")
        MIN_LENGTH,

        @JsonProperty("is_radio")
        IS_RADIO,

        @JsonProperty("is_email")
        IS_EMAIL,

        @JsonProperty("is_multiple_choice")
        IS_MULTIPLE_CHOICE,

        @JsonProperty("regex_match")
        REGEX_MATCH
}