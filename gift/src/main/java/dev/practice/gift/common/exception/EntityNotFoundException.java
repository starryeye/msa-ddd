package dev.practice.gift.common.exception;

import dev.practice.gift.common.response.ErrorCode;

public class EntityNotFoundException extends BaseException{

    public EntityNotFoundException() {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }
}
