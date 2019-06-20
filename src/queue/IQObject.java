package queue;

/**
 * a simple Queue Object Interface used in the Priority Queue
 */
public interface IQObject {
    /**
     * @return the criteria used to sort the Priority Queue
     */
    int integerCriteria();

    /**
     * The comparator used to sort the Priority Queue
     * @param obj the other Queue Object to compare with
     * @return True if the current Queue Object has a integer criteria lower that the other Queue Object, else False
     */
    boolean lowerThan(IQObject obj);

    /**
     * @return the Object that is stored in this Queue Object
     */
    Object getObject();
}
