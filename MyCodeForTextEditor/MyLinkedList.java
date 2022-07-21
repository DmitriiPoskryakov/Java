package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;
	LLNode<E> copy; 
	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		tail = new LLNode<E>();
		head = new LLNode<E>();
		head.next = tail;
		tail.prev = head;
		size = 0;
		
	
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		if (element == null )throw new NullPointerException ();
		LLNode<E> addEnd = new LLNode<E>(element);		
		addEnd.prev = tail.prev;
		addEnd.next = tail;
		addEnd.prev.next = addEnd ;
		tail.prev = addEnd;		
		size++;
		addEnd.index = size - 1;
		return true;
		
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		
		if (index < 0 || index >= size)throw new IndexOutOfBoundsException ();
		copy = head.next;
		E x = null;								
		for (int i = 0 ; i < size; i++) {				
			if (copy.index == index) x = copy.data;			
			else copy = copy.next;
		}		
		return x;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method	
		if (element == null )throw new NullPointerException ();
		if (index < 0)throw new IndexOutOfBoundsException ();
		if (size == 0) {
			add(element);
			head.next.index = index;
		}
		else {
		if (index >= size )throw new IndexOutOfBoundsException ();
		
		LLNode<E> addIndex = new LLNode<E>(element);
		boolean chack = false;					
		
		
		copy = head.next;
		for (int i = 0 ; i < size; i++) {	
			if (copy.index == index) {				 																		
				chack = true;				
				addIndex.next = copy;
				addIndex.prev = copy.prev;
				copy.prev.next = addIndex;				
				copy.prev = addIndex;
				addIndex.index = index;								
				size ++;				
							
			}
			
			if (chack == true) {
				copy.index++;
			}
			
			copy = copy.next;			
		}
		}									
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if (index < 0 || index >= size)throw new IndexOutOfBoundsException ();
		copy = head.next;
		boolean chack = false;		
		E x = null;		
		for (int i = 0 ; i < size; i++) {	
			if (copy.index == index) {													
				chack = true;
				x = copy.data;			
				(copy.prev).next = copy.next;
				(copy.next).prev = copy.prev;																										
			}
			
			if (chack == true) copy.index--;
						
			copy = copy.next;
		
		}
		
		size --;
				
		return x;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (index < 0 || index >= size)throw new IndexOutOfBoundsException ();
		if (element == null )throw new NullPointerException ();
		copy = head.next;
		E x = null;
		for (int i = 0 ; i < size; i++) {				
			if (copy.index == index) {
				x = copy.data;
				copy.data = element;
				break;
			}
			else copy = copy.next;
		}		
		
		return x;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;
	int index;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode() 
	{
		this.data = null;
		this.prev = null;
		this.next = null;
	}
	
	

}
