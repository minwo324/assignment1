package one.main;

public class Debug {
		
	public static void out(Object obj) {
		if (Main.DEBUG) {
			System.out.println(obj.toString());		
		}
	}

}
