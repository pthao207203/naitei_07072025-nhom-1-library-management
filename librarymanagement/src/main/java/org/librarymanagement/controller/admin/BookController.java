package org.librarymanagement.controller.admin;

import org.librarymanagement.constant.ApiEndpoints;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminBookController")
@RequestMapping(ApiEndpoints.ADMIN_BOOK)
public class BookController {
    @GetMapping
    public String showBooklist() {
        return "admin/books/index";
    }

    @GetMapping("/{id}/edit")
    public String editBook() {
        return "admin/books/edit";
    }


    @GetMapping("/{id}/detail")
    public String showBookdetail() {
        return "admin/books/detail";
    }

}
