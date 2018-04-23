package Main;

import java.io.*; // Importing the IO package is essential in working with IO in java
import java.time.LocalDate;


public class EggFileConverter {

	private String eggText= "";
	final private  String SPRTR = "|"; // the predefined constant SPRTR is our separator which we use
	int lines =0; //from default, the number of lines is 0
	int k=0;

	public void eggToFileConverter(Egg[][] eggsToFile, String fileName) throws IOException, FileNotFoundException {
		if (eggsToFile != null) { // the if condition is crucial as it would produce a NullPointerException otherwise (Took too long to find out)
			//File eggTextFile = new File(fileName);
			for (int i = 0; i < eggsToFile.length; i++) { // the ending condition is the length of the first dimension of the eggsToFile[][] Array
				
				for (int j = 0; j < eggsToFile[i].length; j++) { // here the ending condition is the length of the second dimension in the (first) dimension i of the Array
					if (eggsToFile[i][j]!=null) {
					eggText = eggText + i + SPRTR + j + SPRTR + eggsToFile[i][j].getWeight() + SPRTR
							+ eggsToFile[i][j].getEggsize() + SPRTR + eggsToFile[i][j].getProdDate() + SPRTR
							+ eggsToFile[i][j].getExpiDate() + SPRTR + eggsToFile[i][j].getDefect()
							+ String.format("%n");
							// to summarize, the Strings of the attributes are chained together in a line
							// and a new line is created with the String.format(%n)
							// Note: The typical \n does not work in this situation as it only produces 
							// white spaces which isn't helping anyhow
				}			
				}
			}

			FileWriter eggTextFileWriter;
			
				eggTextFileWriter = new FileWriter(fileName); // a new file with the name fileName(which again is a variable String) is created 
				eggTextFileWriter.write(eggText); // with the command write(String) we can write the pre-shaped text into the file
			
			
				if (eggTextFileWriter != null) {
					
				eggTextFileWriter.close();
				}
		}
	}

	/***
	 * @brief Method which converts the given text file into Egg Object in an EggArray
	 * @param fileName
	 * @return Egg[][]
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws FarmDeliveryQuantityException
	 */
	public Egg[][] fileToEgg(String fileName) throws FileNotFoundException, IOException, FarmDeliveryQuantityException {//void or Egg[][]?
		BufferedReader eggTextLineCounter= new BufferedReader(new FileReader (fileName));// try/catch?

		
			while(eggTextLineCounter.readLine()!=null) { //as long as the next line is filled the counter goes up, as the last line which is empty returns null the loop terminates there
				
				lines ++; // counts the lines that are in the text
							
			}
			eggTextLineCounter.close(); // as mentioned often closing of the IO elements is crucial
			
		BufferedReader eggToTextReader = new BufferedReader(new FileReader(fileName));
		
		String[][] eggTextPalette = new String[lines][7]; // needs to defined before to avoid an IndexOutOfBound 0 exception where there are no elements in the array#
											 //^^^^^  ^ we know the amount of lines thanks to the counter before and we can count the number of elements that are needed to be put in
		for (int i=0; i<lines -1; i++) {
			
			String s = eggToTextReader.readLine(); //reads the line of the file
//			System.out.println(s); // visualization of progress in text
		
			for(int j= 0; j<6; j++) {
			if (s!=null) {
			String[] eggText = s.split("\\"+SPRTR); // we need to add the double backslash here because it interfered with the output is it showed "\|" as separator in the text
			 eggTextPalette[i][j] = eggText[j]; // here we define that the attribute on the place j with the line i should be filled with the content of the split word we got
//			 System.out.println(eggTextPalette[i][j]); // visualization of Array
			}
			}
		}
		
		eggToTextReader.close(); // needs to be closed as it causes many problems if there is a resource leak
	
		// we now have each attribute as a String in an Array. Our task now is to read the contents
		// of the Array and convert them to attributes we can work with later
		
		ChickenFarm eggsOfFileFarm = new ChickenFarm();
		
		
		Egg[][] eggsFromFile = eggsOfFileFarm.createEggBoxStack(lines);		
		for(int i=0; i<eggsFromFile.length-1; i++) {
				for (int j = 0; j<eggsFromFile[i].length-1; j++)
				{
				
				Egg fileEgg = new Egg();
				eggsFromFile[i][j] = fileEgg;
				
				if(eggTextPalette[k][2]!=null){
				fileEgg.weight = Integer.parseInt(eggTextPalette[k][2]); // "parse" converts the String into the data type of need
			
				fileEgg.eggsize = Size.valueOf(eggTextPalette[k][3]);	// we use the attributes of each line of the previously created eggTextPalette Array		
				fileEgg.prodDate = LocalDate.parse(eggTextPalette[k][4]); // as we know the numbers of the positions of the attributes we can specifically name them
				fileEgg.expiDate = LocalDate.parse(eggTextPalette[k][5]);
				fileEgg.defect = Boolean.parseBoolean(eggTextPalette[k][6]);
				
				k++; // now the next line is taken into focus
				}
				}
				
		}
		return eggsFromFile; // as the method is of the type Egg[][] it has to return a two-dimensional Array of eggs
	}
		
		
	

	public String getEggText() {
		return eggText;
	}
	

}
