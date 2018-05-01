package Setup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import Swing.Akatar;

public class Save {
	static FileOutputStream save;
	static ObjectOutputStream output;

	public static void saveGame(Akatar game, String text){
		try{
			save = new FileOutputStream(text + ".txt");	
			output = new ObjectOutputStream(save);
			output.writeObject(game);	
			output.close();
		}
		catch(IOException e){
		}
	}
}
