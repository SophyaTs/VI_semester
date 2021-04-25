package problem4;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class SkipList {
    private static class Node{
    	final public int value;
    	final public AtomicReference<Node>[] next;
    	final public int level;
    	
    	public Node(int value, int level, int maxLevels) {
    		this.value = value;
    		this.level = level;
    		this.next = (AtomicReference<Node>[])new AtomicReference<?>[maxLevels];
    		for(int i=0;i<maxLevels;++i)
    			this.next[i]=null;
    	}
    	
    }
    
    final int maxLevels;
    final double p = 0.5;
    final public AtomicInteger size = new AtomicInteger(0);
    final AtomicInteger levels = new AtomicInteger(0);
    final AtomicReference<Node> head;
    
    public SkipList(int maxLevels) {
    	this.maxLevels = maxLevels;
    	this.head = new AtomicReference<Node>(new Node(Integer.MIN_VALUE,0,maxLevels));
    }
    
    private Random levelRandom = new Random(0);
	private int chooseRandomLevel() {
		int newLevel = 0;
		while (newLevel < maxLevels - 1 && levelRandom.nextFloat() < this.p) {
			newLevel += 1;
		}
		return newLevel;
	}
    
    public void add(int value) {
    	AtomicReference<Node> ref = head;
    	Node current = (Node) ref.get();
    	Node[] update = new Node[maxLevels];
    	int currentLevel = levels.get();
    	
    	for(int i = currentLevel; i>=0;i--) {
    		ref = head;
        	current = (Node) ref.get();
    		while(current.next[i] != null && current.next[i].get().value < value) {
    			ref = current.next[i];
    			current = (Node) ref.get();
    		}
    		update[i] = current;
    	}
    	ref = current.next[0];
		if(ref != null)
			current = (Node) ref.get();
    	
    	if(ref == null || current.value != value) {
    		int rLevel = chooseRandomLevel();
    		if(rLevel > currentLevel) {
    			for(int i=currentLevel+1;i<=rLevel;++i)
    				update[i]=head.get();
    			
    			levels.compareAndSet(currentLevel, rLevel);
    		}
    		
    		Node newNode = new Node(value,rLevel,maxLevels);
    		
    		for(int i=0;i<=rLevel;++i) {
    			newNode.next[i] = update[i].next[i];
    			update[i].next[i]= new AtomicReference<Node>(newNode);
    		}
    		
    		size.incrementAndGet();
    	}
    	else {
    		//System.out.println("D "+value);
    	}
    }
    
    public void remove(int value) {
    	AtomicReference<Node> ref = head;
    	Node current = (Node) ref.get();
    	Node[] update = new Node[maxLevels];
    	int currentLevel = levels.get();
    	
    	for(int i = currentLevel; i>=0;i--) {
    		while(current.next[i] != null && current.next[i].get().value < value) {
    			ref = current.next[i];
    			current = (Node) ref.get();
    		}
    		update[i] = current;
    	}
    	ref = current.next[0];
		if(ref!= null)
			current = (Node) ref.get();
		
		if(ref!= null && current.value==value) {
			for(int i=0;i<=currentLevel;++i) {
				if(update[i].next[i]==null || update[i].next[i].get() != current)
					break;
				update[i].next[i]= current.next[i];
			}
			
			int level = levels.get();
			while(level>0 && head.get().next[level] == null) {
				--level;
			}
			levels.compareAndSet(currentLevel, level);
			size.decrementAndGet();
		}
		else {
			//System.out.println("N "+value);
		}
    }
    
    public boolean contains(int value) {
    	AtomicReference<Node> ref = head;
    	Node current = (Node) ref.get();
    	Node[] update = new Node[maxLevels];
    	int currentLevel = levels.get();
    	
    	for(int i = currentLevel; i>=0;i--) {
    		while(current.next[i] != null && current.next[i].get().value < value) {
    			ref = current.next[i];
    			current = (Node) ref.get();
    		}
    		update[i] = current;
    	}
    	ref = current.next[0];
		if(ref!= null)
			current = (Node) ref.get();
		
		if(ref!= null && current.value==value) {
			return true;
		}
		else
			return false;
    }
    
    @Override	
    public String toString() {
    	String str = "";
    	
    	int l = levels.get();
    	for(int i=l;i>=0;--i) {
    		AtomicReference<Node> ref = head.get().next[i];
        	Node current = (Node) ref.get();
        	while(ref != null) {
        		str+=" "+current.value;
        		ref = current.next[i];
        		if(ref!=null)
        			current = (Node) ref.get();
        	}
        	str+='\n';
    	}
    	
    	return str;
    }
    
}
