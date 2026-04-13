package vn.fpoly.assignmentjava202.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.assignmentjava202.DAO.CategoriesDAO;
import vn.fpoly.assignmentjava202.Entity.Catergories;
import vn.fpoly.assignmentjava202.Entity.Products;
import vn.fpoly.assignmentjava202.Service.ProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoriesDAO categoriesDAO;

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products",productService.getAllProducts());
        model.addAttribute("page","/admin/products");
        return "admin/sidebar";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Products());
        model.addAttribute("categories",categoriesDAO.findAll());
        model.addAttribute("page","/admin/product-form-add");
        return "admin/sidebar";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        Products products = productService.updateProduct(id);
        if(products==null) {
            return "redirect:/admin/products";
        }
        model.addAttribute("product",products);
        model.addAttribute("categories",categoriesDAO.findAll());
        model.addAttribute("page","/admin/product-form-edit");
        return "admin/sidebar";
    }

    @PostMapping("/products/add")
    public String saveProduct(Products products, @RequestParam("categoryId") String categoryId, @RequestParam("imageFile") MultipartFile imageFile, RedirectAttributes redirectAttributes) throws IOException {

        if(!imageFile.isEmpty()) {
            String fileName = Paths.get(imageFile.getOriginalFilename()).getFileName().toString();
            String upLoadDir =  "src/main/resources/static/images/ao_phong/";
            Path uploadPath = Paths.get(upLoadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(imageFile.getInputStream(),uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            products.setImage(fileName);
        }

Catergories catergories = categoriesDAO.findById(categoryId).orElse(null);
products.setCategory(catergories);
products.setCreateDate(LocalDate.now());

productService.addProduct(products);
redirectAttributes.addFlashAttribute("message","Thêm sản phẩm thành công");
return "redirect:/admin/products";
    }

    @PostMapping("/products/edit")
    public String editProduct(Products products, @RequestParam("categoryId") String categoryId,@RequestParam("imageFile") MultipartFile imageFile, RedirectAttributes redirectAttributes) throws IOException {

        Products existingProduct = productService.updateProduct(products.getId());
        if (existingProduct != null) {
            products.setCreateDate(existingProduct.getCreateDate());
        }

        if (!imageFile.isEmpty()) {
            String fileName = Paths.get(imageFile.getOriginalFilename()).getFileName().toString();
            String uploadDir = "src/main/resources/static/images/ao_phong/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(imageFile.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            products.setImage(fileName);
        }

        Catergories catergories = categoriesDAO.findById(categoryId).orElse(null);
        products.setCategory(catergories);
        productService.addProduct(products);
        redirectAttributes.addFlashAttribute("message","Bạn sửa sản phẩm thành công");
        return "redirect:/admin/products";
    }

}
