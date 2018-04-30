package testat1;
import java.io.FileNotFoundException;
import java.io.IOException;
import Main.ChickenFarm;
import Main.Colour;
import Main.EasterEggFactory;
import Main.EggColoredException;
import Main.EggCrackException;
import Main.EggFileConverter;
import Main.EggNotBoiledException;
import Main.FarmDeliveryQuantityException;

public class enhacedProductionLine {
	
public static void main (String[]args) throws FileNotFoundException, IOException, FarmDeliveryQuantityException{
		
	ChickenFarm myChickenFarm = new ChickenFarm ();
	EasterEggFactory myEasterEggFactory = new EasterEggFactory ();
	
	myEasterEggFactory.productionImport(myChickenFarm.deliverEgg(25));
		for (int i=1; i<=10;i++) { // a loop which simply creates 10 files with the matching filenames to it
		EggFileConverter myETFC = new EggFileConverter();	
		
		myETFC.eggToFileConverter(myEasterEggFactory.getEasterEggBoxStack(),"eier"+i+".txt"); // converting to file
//		System.out.println(myETFC.getEggText());	//printing out array
		
//		if(myETFC.getEggText()!= null) {
//		myETFC.fileToEgg("eier"+i+".txt");	// and turning it back into an Array
//		}
		}
	
	
	}
	}


