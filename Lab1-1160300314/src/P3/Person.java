package P3;

import java.util.HashSet;
import java.util.Set;

public class Person {
	private String name;
	public int distance;
	public  boolean visited;
	public Set<Person> friend;
	public int index;
	public Person(String name) {
		this.name = name;
		this.distance = 0;
		this.visited = false;
		this.friend = new HashSet<>();
	}
	public String getName(){
		return this.name;
	}
}
