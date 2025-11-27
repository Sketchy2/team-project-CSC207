package use_case.delete;

/**
 * Output boundary for the delete outfit use case.
 * Defines how success and failure responses are presented.
 */
public interface DeleteOutfitOutputBoundary {

    /**
     * Prepares the success view after an outfit is deleted.
     *
     * @param data output data containing the updated outfit list and message
     */
    void prepareSuccessView(DeleteOutfitOutputData data);

    /**
     * Prepares the failure view when the deletion cannot be completed.
     *
     * @param errorMessage the error message to display
     */
    void prepareFailView(String errorMessage);
}
