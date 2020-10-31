package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.Author;
import kn18012.librarymanagement.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lib-dashboard")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/new-author")
    public String addAuthorView(Model model) {
        model.addAttribute("author", new Author());
        return "lib/new-author";
    }

    @PostMapping("/create-author")
    public String createAuthor(@ModelAttribute Author author) {
        authorService.save(author);
        return "redirect:/lib-dashboard";
    }

    @GetMapping("/edit-author/{authorId}")
    public String editAuthorView(@PathVariable("authorId") Long id, Model model) {
        model.addAttribute("author", authorService.findAuthorById(id));
        return "lib/edit-author";
    }

    @PostMapping("/update-author/{authorId}")
    public String editAuthorView(@PathVariable("authorId") Long id, @ModelAttribute Author author) {
        authorService.update(id, author);
        return "redirect:/lib-dashboard";
    }

    @DeleteMapping
    @RequestMapping("/delete-author/{authorId}")
    public String deleteAuthor(@PathVariable("authorId") Long id) {
        authorService.deleteAuthorById(id);
        return "redirect:/lib-dashboard";
    }
}
