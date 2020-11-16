package com.ds.project.apothecary.dtos;

import java.util.List;

/**
 * The type Api error dto.
 */
public class ApiErrorDto {
    /**
     * The Errors.
     */
    private List<String>
            errors;

    /**
     * Instantiates a new Api error dto.
     *
     * @param pErrors the errors
     */
    public ApiErrorDto(final List<String> pErrors) {
        this.errors =
                pErrors;
    }

    /**
     * Gets errors.
     *
     * @return the errors
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Sets errors.
     *
     * @param pErrors the errors
     */
    public void setErrors(final List<String> pErrors) {
        this.errors =
                pErrors;
    }
}
