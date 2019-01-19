package be.pxl.ja.opgave2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Opgave2 {
	public static void main(String[] args) throws InterruptedException {
		Path path = Paths.get("resources/opgave2/passphrases.txt");
		List<PassPhraseValidator> list = new ArrayList<>();

		try(BufferedReader reader = Files.newBufferedReader(path)){
			String line = null;
			while ((line = reader.readLine())!=null){
				String[] split = line.split(" ");
				PassPhraseValidator<String> validator = new PassPhraseValidator<String>(Arrays.asList(split));
				validator.start();
				list.add(validator);
			}
		} catch (IOException e) {
			System.out.println("Oops, something went wrong!");
			System.out.println(e.getMessage());
		}

		int validCount = 0;
		for(PassPhraseValidator validator : list){
			validator.join();
			if(validator.isValid()){
				validCount++;
			}
		}

		System.out.println(validCount);
	}
}
