import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author  sinkala.kundananji on 31.10.16.
 */
public class BookShelf {
    public static void main(String[] args) {
        BookShelf bookshelf = new BookShelf();
        int choice;

        while (!quit) {
            choice = getMenu();
            if (selected == null) {
                switch (choice) {
                    case 1: bookshelf.load(); break;
                    case 2: bookshelf.addBook(); break;
                    case 3: bookshelf.selectBook(null); break;
                    case 4: bookshelf.sumUp(); break;
                    case 5: bookshelf.save(); break;
                    case 6: quit = true; break;
                }
            }
            else {
                switch (choice) {
                    case 1: bookshelf.deselectBook(); break;
                    case 2: bookshelf.updateSelectedPrice(); break;
                    case 3: bookshelf.updateSelectedRating(); break;
                    case 4: bookshelf.updateSelectedReview(); break;
                    case 5: bookshelf.removeSelected(); break;
                }
            }
        }
        
        System.out.println("Goodbye!");
   } 

    private static ArrayList<Book> books;
    private static Book selected;
    
    private static boolean quit = false;

    public BookShelf() {
        books = new ArrayList<>();
        selected = null;
    }
    
    public static int getMenu() {
    int userChoice, menuSize;
    Scanner keyboard = new Scanner(System.in);

    System.out.println("");
    if (selected == null) {
        System.out.println("Your Personal Bookshelf");
        System.out.println("1. Load a previous bookshelf");
        System.out.println("2. Add a book to your shelf");
        System.out.println("3. Select a Book from the shelf");
        System.out.println("4. Summary of your bookshelf");
        System.out.println("5. Save your bookshelf to disk");
        System.out.println("6. Quit\n");
    }
    else {
        System.out.println("This is the book you've selected right now:");
        System.out.println(selected+"\n");
        System.out.println("1. Put the selected copy back on the shelf");
        System.out.println("2. Update this book's price");
        System.out.println("3. Update this book's rating");
        System.out.println("4. Update this book's review");
        System.out.println("5. Remove the selected copy from the shelf");
    }

    // get input from user
    menuSize = selected == null ? 6 : 5;
    System.out.print("Enter your choice (1-"+menuSize+"): ");
    userChoice = keyboard.nextInt();

    // validate input
    while (userChoice < 1 || userChoice > menuSize) {
        System.out.print("Please enter a valid choice (1-"+menuSize+"): ");
        userChoice = keyboard.nextInt();
    }
    return userChoice;
}

    
    // method to add the book
    public void addBook() {
        Scanner in = new Scanner(System.in);
        
        // ask for Title and Author...
        System.out.println("Enter the book Title ?");
        String title = in.nextLine();
        System.out.println("Enter the book Author ?");
        String author = in.nextLine();

        selectBook(title);
        
        if (selected == null) {
            books.add(new Book(title, author));
            selected = books.get(books.size() -1);
        }
        else {
            selected.setCount(selected.getCount() +1);
        }
    }
    
    // select the book from shelf only if it is there
    public void selectBook(String title) {
        Scanner in = new Scanner(System.in);
    
        if (title == null) {
            // ask for Title...  
            System.out.println("Select the title of book from the shelf again: ");
            title = in.nextLine();
        }

        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                selected = book;
                System.out.println("You have selected \""+selected.getTitle()+"\" from the shelf");            
            }
            else {
                System.out.println("\""+title+"\" could not be found on your shelf.");}
        }
    }
    
    public void deselectBook() {
        selected = null;
    }
    
    // update method for review by entering the string review
    public void updateSelectedReview() {
        if (selected == null) return;
        
        Scanner in = new Scanner(System.in);
        String review;

        // ask for Review...
        System.out.print("Enter your review for this book: ");
        review = in.nextLine();

        selected.setReview(review);
    }
    
   // rating method
    public void updateSelectedRating() {
        if (selected == null) return;
        
        Scanner in = new Scanner(System.in);
        int rating;

        // ask for Rating...
        System.out.print("Enter a rating for this book: ");
        rating = in.nextInt();

        selected.setRating(rating);
    }
    
    // price method
    public void updateSelectedPrice() {
        if (selected == null) return;
        
        Scanner in = new Scanner(System.in);
        int price;

        // ask for Price..
        System.out.print("Enter a price for this book: ");
        price = in.nextInt();

        selected.setPrice(price);
    }
    
    // removal of selscted book from the shelf
    public void removeSelected() {
        if (selected == null) return;
        if (selected.getCount() > 1) selected.setCount(selected.getCount() -1);
        else {
            books.remove(selected);
            selected = null;
        }
    }
    
    // Display all the information of the shelf by number,rating and value
    public void sumUp() {
        int bookCount = 0;
        int goodOnes = 0;
        int value = 0;

        for (Book book : books) {
            bookCount += book.getCount();
            if (book.getRating() > 3) goodOnes += book.getCount();
            value += book.getCount() * book.getPrice();
        }

        System.out.println("Your shelf holds a total of "+bookCount+" books.");
        System.out.printf("%.2f%% of those books are considered to be good ones.\n", goodOnes * (100.0 / bookCount));
        System.out.println("The total value of all the books within your shelf is "+value+" SEK.");
    }
    
    // save method on the txt.file
    public void save() {
        try {
            PrintWriter writer = new PrintWriter("lastBookshelf.txt", "UTF-8");
            writer.println("Title, Author, Rating, Review, Price, Count");

            for (Book book : books) {
                String description = "";
                description += book.getTitle()+", ";
                description += book.getAuthor()+", ";
                description += book.getRating()+", ";
                description += book.getReview()+", ";
                description += book.getPrice()+", ";
                description += book.getCount()+", ";
                writer.println(description);
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // reading or loading the files exsting on txt.file
    public void load() {
        try {
            Scanner scanner = new Scanner(new File("lastBookshelf.txt"), "UTF-8");
            scanner.useDelimiter(", ");
            books = new ArrayList<>();

            while (scanner.hasNextLine()) {
                scanner.nextLine();
                if (!scanner.hasNext()) break;
                Book book = new Book(scanner.next(), scanner.next());
                book.setRating(Integer.parseInt(scanner.next()));
                book.setReview(scanner.next());
                book.setPrice(Integer.parseInt(scanner.next()));
                book.setCount(Integer.parseInt(scanner.next()));
                books.add(book);
            }
            
            System.out.println("Your bookshelf has been successfully loaded.");
        }
        catch (Exception e) {
            System.out.println("Sorry, there's no previous version that could be retrieved from disk.");
        }
    }
}
