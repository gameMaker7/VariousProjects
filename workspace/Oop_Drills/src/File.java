import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class File {

	FileWriter save = null;
	public void method() {
		try{
			save = new FileWriter("fred.txt");
			save.write("hello fred\nGoodbye Fred");
		}
		catch(IOException e){

		}
		finally{
			if(save != null){
				try{
					save.close();
				}
				catch(IOException e){}
		}

	}

	FileReader reader = null;
	try{
		reader = new FileReader("Fred.txt");
		for(;;){
			int c = reader.read();
			if(c == -1)break;
			System.out.print((char)c);
		}
	}
	catch(Exception e){
	}
	finally{
		if(reader != null){

		}
	}

}
}
