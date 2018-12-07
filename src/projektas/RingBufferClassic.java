
package projektas;
public interface RingBufferClassic<E>
{
    /** 
    * Gets the capacity of the buffer.
    * @return the capacity of the buffer
    */
    int capacity();
    
    /**
    * Gets the amount of elements in the buffer.
    * @return number of elements in buffer.
    */
    int size();
    
    /**
     * Finds and returns an element with index i in buffer.
     * @param i is the index of the element in the buffer.
     * @return the element with index i.
     */
    E get(int i);
    
    /**
     * Replaces an element in index i with element e.
     * @param i is the index of replaced element.
     * @param e is the element that will take the place in index i.
     * @return the element that was replaced by e.
     */
    E set(int i, E e);
    
    /**
     * Adds an element to the buffer by shifting other elements.
     * @param i is the index of where the element will be placed.
     * @param e is the element that will be placed in the buffer.
     */
    void add(int i, E e);
    
    /**
     * Removes an element from the buffer and replaces it with null.
     * @param i is the index of which element will be removed.
     * @return the element that was removed.
     */
    E remove(int i);
    
}
