package lib280.dispenser;

import lib280.base.Dispenser280;
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.ContainerFull280Exception;
import lib280.exception.NoCurrentItem280Exception;
import lib280.list.SimpleList280;

public abstract class Queue280<I extends Comparable<? super I>> implements Dispenser280<I> {

	protected SimpleList280<I> stackItems;
	

	@SuppressWarnings("unchecked")
	@Override
	public Queue280<I> clone() throws CloneNotSupportedException {
		return (Queue280<I>) super.clone();
	}

	@Override
	public void deleteItem() throws NoCurrentItem280Exception {
		try {
			stackItems.deleteFirst();
		}
		catch( ContainerEmpty280Exception e ) {
			throw new NoCurrentItem280Exception("Tried to delete item from the head of an empty queue.");
		}
	}

	@Override
	public void insert(I x) throws ContainerFull280Exception {
		stackItems.insertLast(x);
	}

	@Override
	public I item() throws NoCurrentItem280Exception {
		
		try {
			stackItems.goFirst();
		}
		catch (ContainerEmpty280Exception e) {
			throw new NoCurrentItem280Exception("Triedt o obtain item from empty queue.");
		}

		return stackItems.firstItem();
	}

	@Override
	public boolean itemExists()  {
		return !this.isEmpty();
	}

	@Override
	public void clear() {
		stackItems.clear();
	}

	@Override
	public boolean isEmpty() {
		return stackItems.isEmpty();
	}

	@Override
	public boolean isFull() {
		return stackItems.isFull();
	}


	@Override
	public String toString() {
		return "Queue starting with front item: " + stackItems;
	}


}
