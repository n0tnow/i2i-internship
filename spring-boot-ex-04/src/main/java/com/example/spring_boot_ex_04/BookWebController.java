package com.example.spring_boot_ex_04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookWebController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books/list";
    }
    
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/add";
    }

    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException());
            model.addAttribute("book", book);
            return "books/view";
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Book not found!");
            return "redirect:/books";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException());
            model.addAttribute("book", book);
            return "books/edit";
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Book not found!");
            return "redirect:/books";
        }
    }

    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id,
                           @ModelAttribute("book") Book book,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        
        // Set the ID to ensure we're updating the correct book
        book.setId(id);
        
        // Basic validation
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            bindingResult.rejectValue("title", "error.book", "Title is required");
        }
        
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            bindingResult.rejectValue("author", "error.book", "Author is required");
        }
        
        // Check for duplicate title (exclude current book)
        if (book.getTitle() != null && !book.getTitle().trim().isEmpty()) {
            var existingBooks = bookRepository.findByTitle(book.getTitle().trim());
            if (!existingBooks.isEmpty() && existingBooks.get(0).getId() != id) {
                bindingResult.rejectValue("title", "error.book", "A book with this title already exists");
            }
        }
        
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        
        try {
            // Verify book exists
            bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException());
            
            // Clean the input
            book.setTitle(book.getTitle().trim());
            book.setAuthor(book.getAuthor().trim());
            
            bookRepository.save(book);
            redirectAttributes.addFlashAttribute("message", 
                "Book '" + book.getTitle() + "' has been updated successfully!");
            return "redirect:/books";
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Book not found!");
            return "redirect:/books";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update book. Please try again.");
            return "books/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException());
            
            String bookTitle = book.getTitle();
            bookRepository.deleteById(id);
            
            redirectAttributes.addFlashAttribute("message", 
                "Book '" + bookTitle + "' has been deleted successfully!");
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Book not found!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete book. Please try again.");
        }
        
        return "redirect:/books";
    }

    @PostMapping
    public String addBook(@ModelAttribute("book") Book book, 
                         BindingResult bindingResult, 
                         Model model, 
                         RedirectAttributes redirectAttributes) {
        
        // Basic validation
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            bindingResult.rejectValue("title", "error.book", "Title is required");
        }
        
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            bindingResult.rejectValue("author", "error.book", "Author is required");
        }
        
        // Check for duplicate title
        if (book.getTitle() != null && !book.getTitle().trim().isEmpty()) {
            if (!bookRepository.findByTitle(book.getTitle().trim()).isEmpty()) {
                bindingResult.rejectValue("title", "error.book", "A book with this title already exists");
            }
        }
        
        if (bindingResult.hasErrors()) {
            return "books/add";
        }
        
        try {
            // Clean the input
            book.setTitle(book.getTitle().trim());
            book.setAuthor(book.getAuthor().trim());
            
            bookRepository.save(book);
            redirectAttributes.addFlashAttribute("message", 
                "Book '" + book.getTitle() + "' has been added successfully!");
            return "redirect:/books";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add book. Please try again.");
            return "books/add";
        }
    }
}