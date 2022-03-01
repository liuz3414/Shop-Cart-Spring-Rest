package com.syqu.shop.controller;

import com.syqu.shop.domain.Product;
import com.syqu.shop.service.CategoryService;
import com.syqu.shop.service.ProductService;
import com.syqu.shop.validator.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    private final ProductValidator productValidator;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, ProductValidator productValidator, CategoryService categoryService) {
        this.productService = productService;
        this.productValidator = productValidator;
        this.categoryService = categoryService;
    }

    @GetMapping("/product/new")
    public String newProduct(Model model) {
        model.addAttribute("productForm", new Product());
        model.addAttribute("method", "new");
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("page", "new");
        return "product";
    }

    @PostMapping("/product/new")
    public String newProduct(@ModelAttribute("productForm") Product productForm, BindingResult bindingResult, Model model) {
        productValidator.validate(productForm, bindingResult);
        /*--------------------*/
        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            model.addAttribute("method", "new");
            model.addAttribute("page", "home");
            return "product";
        }
        /*--------------------*/
        productService.save(productForm);
        model.addAttribute("page", "home");
        logger.debug(String.format("Product with id: %s successfully created.", productForm.getId()));

        return "redirect:/home";
    }
    
    @GetMapping("/search/{text}")
    public String searchProduct(@PathVariable("text") String text, Model model) {
    	
    	if(text.equals(""))
    		 return "redirect:/home";
    	model.addAttribute("products", productService.getProductBySearch(text));
    	model.addAttribute("categories", categoryService.findAll());
    	model.addAttribute("page", "home");
    	return "home";
    }
    @GetMapping("/search/id/{id}")
    public String searchProductEmpty(@PathVariable("id") long id, Model model) {
    	Product product = productService.findById(id);
    	
    	model.addAttribute("categories", categoryService.findAll());
    	model.addAttribute("productForm", product);
    	model.addAttribute("page", "home");
    	return "product";
    }
    
    
    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") long productId, Model model){
        Product product = productService.findById(productId);
        if (product != null){
        	model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("productForm", product);
            model.addAttribute("page", "home");
            model.addAttribute("method", "edit");
            return "product";
        }else {
            return "error/404";
        }
    }

    @PostMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") long productId, @ModelAttribute("productForm") Product productForm, BindingResult bindingResult, Model model){
        productValidator.validate(productForm, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            model.addAttribute("method", "edit");
            model.addAttribute("page", "home");
            return "product";
        }
        productService.edit(productId, productForm);
        
        logger.debug(String.format("Product with id: %s has been successfully edited.", productId));

        return "redirect:/home";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") long productId){
        Product product = productService.findById(productId);
        
        if (product != null){
           productService.delete(productId);
           logger.debug(String.format("Product with id: %s successfully deleted.", product.getId()));
           return "redirect:/home";
        }else {
            return "error/404";
        }
    }
}
