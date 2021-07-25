import java.lang.Math;
import java.util.Arrays;

class Dad {
	int age;
	int weight;
}

class Son extends Dad{
	int test;
	
	public void calc() {
		this.test = this.age * this.weight;
	}
}