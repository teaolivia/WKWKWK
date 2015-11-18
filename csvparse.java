import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class csvparse {
	
	public static void main(String[] args){
		csvparse obj = new csvparse();
		obj.baca();
	}
	
	public void baca(){
		
		String csvFile = ""; // lokasi .csv
		BufferedReader br = null;
		String line = "";
		String csvParse = ",";
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readline()) != null){
				
				// parse per atribut
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try{
					br.close();
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		System.out.println("Done.");
	}
}
