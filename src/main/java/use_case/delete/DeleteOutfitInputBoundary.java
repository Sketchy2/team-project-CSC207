package use_case.delete;

/**
 * Input boundary for the delete outfit use case.
 */
public interface DeleteOutfitInputBoundary {

    /**
     * Executes the delete outfit request.
     *
     * @param inputData the data needed to perform the deletion
     */
    void execute(DeleteOutfitInputData inputData);
}
