// Name: Divyansh Mishra
// Completion_Date: 21/01/2004


import java.io.File;
import java.io.IOException;
// import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class MyfileR {
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        int choice;
        int pathchoice;
        String path;
        

        System.out.println("Press 1 for searching in the current directory");
        System.out.println("Press 2 for entering the full directory to search");
        // System.out.println();
        pathchoice = sc.nextInt();
        switch (pathchoice) {
            case 2:
                System.out.println();
                System.out.println("Enter the directry to be searched: ");
                System.out.println();
                String f = sc.nextLine();
                path = sc.nextLine();
                break;
            case 1:
                System.out.println();
                path = System.getProperty("user.dir");
                break;
            default:
                System.out.println("Choose a valid option");
                path = "Invalid";
                break;
        }

        System.out.println();
        System.out.println("Choose the desired integers: ");
        System.out.println("1 for File names with full path");
        System.out.println("2 for Only file name");
        System.out.println("3 for counting the total number of files");
        System.out.println("4 for printing the content of all the text files");
        System.out.println("5 for printing the word frequencies of all the text files");
        System.out.println("6 for printing the content of all the text files without stop-words");
        System.out.println();
        choice = sc.nextInt();

        // To be proccessed yet...

        // Inititalising the file instant:
        File dir = new File(path);

        if (dir.exists() && dir.isDirectory()){
            switch (choice) {
                case 1:
                    System.out.println("File name with full path: ");
                    System.out.println();
                    fullpathname(dir);
                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    System.out.println("Only file name: ");
                    filename(dir);
                    System.out.println();
                    break;
                case 3:
                    System.out.println();
                    System.out.println("Total number of files is: ");
                    System.out.println(countfile(dir));
                    System.out.println();
                    break; 
                case 4:
                    System.out.println();
                    System.out.println("Here find the content of the files in the sequence of the filename");
                    showContent(dir);
                    System.out.println();
                    break;
                case 5:
                    System.out.println();
                    frequency(dir);
                    System.out.println();
                    break;
                case 6:
                    System.out.println();
                    withoutStopWord(dir);
                    System.out.println();
                    break;
                default:
                    System.out.println("You have choosen a wrong option");
                    break;
            }            
        } else {
            System.out.println("Directory not found, or there is any spelling mistake.");
        }
        sc.close();
    }

    //Helper for printing the full path of each file in the given directory...
    public static void fullpathname(File dir){
            File[] files = dir.listFiles();
            if (files != null){
                for (File file : files){
                    if (file.isFile()){
                        System.out.println(file.getAbsolutePath());
                    } else if(file.isDirectory()){
                        fullpathname(file);
                    }
                }
            }
        }


    // Helper for printing the only file name in the given directory...
    public static void filename(File dir){
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files){
                if (file.isFile()) {
                    System.out.println(file.getName());
                } else if (file.isDirectory()) {
                    filename(file);
                }
            }
        }
    }

    // Helper for counting all the files in the given directory...
    public static int count = 0;
    public static int countfile(File dir){
        
        File[] files = dir.listFiles();
        for (File file : files){
            if(file.isFile()){
                count++;
            }else if(file.isDirectory()){
                countfile(file);
            }
        }
        return count;
    }

    // Helper for extracting all the content from the file...    
    public static void showContent(File dir){
        File[] files = dir.listFiles();
        if (files != null){
            for (File file : files){
                if (isText(file)){
                    try (Scanner fileSc = new Scanner(file)) {
                        System.out.println("Contents of the file "+file+" are: ");
                        while (fileSc.hasNextLine()) {
                            System.out.println(fileSc.nextLine());
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred while reading the file: " + e.getMessage());
                    }  
                }
            }
        }
    }

    // Helper for printing the word frequencies file wise...
    public static void frequency(File dir){
        File[] files = dir.listFiles();
        if (files!=null){
            for (File file:files){
                System.out.println(wordCount(file));
            }
        }
    }

    // Helper for counting the word frequency in a text file...
    public static  HashMap<String, Integer> wordCount(File file){
        HashMap<String, Integer> result = new HashMap<>();
        if(isText(file)){
            try (Scanner fsc = new Scanner(file)) {
                while (fsc.hasNextLine()) {
                    String line = fsc.nextLine();
                    String[] words = line.split(" ");
                    for(String word:words){
                        if(result.containsKey(word)){
                            int new_value = result.get(word) + 1;
                            result.put(word, new_value);
                        }else{
                            result.put(word, 1);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            } 
        }
        return result;
    }

    // Helper for removing the stop words and printing the content of the file...
    public static void withoutStopWord(File dir){
        String[] stopList = {"a", "an", "and", "are", "as", "at", "for", "from", "has", "he",
        "in", "is", "it", "its", "of", "on", "that", "the", "to", "was", "were", "with"};

        File[] files = dir.listFiles();
        if(files!=null){
            for(File file:files){
                if(isText(file)){
                    try(Scanner fsc = new Scanner(file)){
                        while (fsc.hasNextLine()) {
                            String line = fsc.nextLine();
                            String[] words = line.split(" ");
                            for(String word:words){
                                if (! contains(stopList, word)) {System.out.print(word+""); }
                                }
                            }
                    } catch (IOException e) {
                        System.out.println("An error occurred while reading the file: " + e.getMessage());
                    }
                }
            }
        }
    }

    // Helper for checking the text file...
    private static boolean isText(File file) {
        if(file.isFile()){
            String fileName = file.getPath();
            return fileName.toLowerCase().endsWith(".txt");
        } else{ return false;}
    }

    // Helper for checking that whether the word is there in the array...
    private static boolean contains(String[] array, String targetWord) {
        return Arrays.asList(array).contains(targetWord);
    }
}