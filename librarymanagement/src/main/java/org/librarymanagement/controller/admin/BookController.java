package org.librarymanagement.controller.admin;

import jakarta.servlet.http.HttpServletResponse;
import org.librarymanagement.constant.ApiEndpoints;
import org.librarymanagement.service.BookService;
import org.librarymanagement.utils.ExcelValidator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller("adminBookController")
@RequestMapping(ApiEndpoints.ADMIN_BOOK)
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String showBooklist() {
        return "admin/books/index";
    }

    @GetMapping("/{id}/edit")
    public String editBook() {
        return "admin/books/edit";
    }


    @GetMapping("/{id}")
    public String showBookdetail() {
        return "admin/books/detail";
    }

    @PostMapping("/import")
    public String importBooks(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            // Validate trước khi import
            List<String> errors = ExcelValidator.validateExcelFile(file.getInputStream());
            if (!errors.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Import thất bại, file không hợp lệ!");
                redirectAttributes.addFlashAttribute("errors", errors); // gửi danh sách lỗi sang view
                redirectAttributes.addFlashAttribute("alertType", "danger");
                return "redirect:" + ApiEndpoints.ADMIN_BOOK;
            }

            // Nếu file hợp lệ thì mới gọi service để import
            bookService.importBooksFromExcel(file);
            redirectAttributes.addFlashAttribute("message", "Import thành công!");
            redirectAttributes.addFlashAttribute("alertType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Import thất bại: " + e.toString());
            redirectAttributes.addFlashAttribute("alertType", "danger");
        }
        return "redirect:" + ApiEndpoints.ADMIN_BOOK;
    }

    @GetMapping("/download-template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/public/files/Template.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=book_template.xlsx");
        StreamUtils.copy(resource.getInputStream(), response.getOutputStream());
    }

}
