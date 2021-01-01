package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.Author;
import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/lib-dashboard")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors/page/{pageNumber}")
    public String pagedAuthorView(@PathVariable("pageNumber") int pageNumber, @RequestParam("phrase") String phrase, Model model) {
        Page<Author> page = authorService.searchForAuthor(phrase, pageNumber);
        List<Author> authors = page.getContent();

        model.addAttribute("authors", authors);
        model.addAttribute("phrase", phrase);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        return "lib/author-list";
    }

    @GetMapping("/new-author")
    public String addAuthorView(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("author", new Author());
        return "lib/new-author";
    }

    @PostMapping("/create-author")
    public String createAuthor(@ModelAttribute Author author) {
        authorService.save(author);
        return "redirect:/lib-dashboard/authors/page/1?phrase=&author_create=success";
    }

    @GetMapping("/edit-author/{authorId}")
    public String editAuthorView(@AuthenticationPrincipal User user, @PathVariable("authorId") Long id, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("author", authorService.findAuthorById(id));
        return "lib/edit-author";
    }

    @PostMapping("/update-author/{authorId}")
    public String updateAuthor(@PathVariable("authorId") Long id, @Valid @ModelAttribute Author author, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "lib/edit-author";
        }

        authorService.update(id, author);
        return "redirect:/lib-dashboard/authors/page/1?phrase=&author_update=success";
    }

    @DeleteMapping
    @RequestMapping("/delete-author/{authorId}")
    public String deleteAuthor(@PathVariable("authorId") Long id) {
        authorService.deleteAuthorById(id);
        return "redirect:/lib-dashboard/authors/page/1?phrase=&author_delete=success";
    }
}
