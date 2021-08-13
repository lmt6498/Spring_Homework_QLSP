package controllers;

import dao.SelectProduct;
import models.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.CategoryService;
import services.ProductService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    ProductService productService = new ProductService();
    CategoryService categoryService =new CategoryService();

    @RequestMapping("/show")
    public ModelAndView show() throws SQLException, ClassNotFoundException {
        List<Product> list = SelectProduct.select();
        ModelAndView modelAndView = new ModelAndView("show","lists",list);

        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showEdit(@PathVariable int id, Model model){
        ModelAndView modelAndView = new ModelAndView("edit","listCate", categoryService.listCate);
        model.addAttribute("product",productService.list.get(id));
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView showCreate(Model model) {
        ModelAndView modelAndView = new ModelAndView("create","listCate", categoryService.listCate);
        model.addAttribute("product",new Product());
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id) throws SQLException, ClassNotFoundException {
        productService.delete(id);
        return "redirect:/show";
    }

    @PostMapping("/edit")
    public String edit(Product product) throws SQLException, ClassNotFoundException {
        productService.edit(product);
        return "redirect:/show";
    }

    @PostMapping("/create")
    public String create(Product product) throws SQLException, ClassNotFoundException {
        productService.save(product);
        return "redirect:/show";
    }

    @PostMapping("/find")
    public ModelAndView findByName(@RequestParam String findName) {

        ArrayList<Product> products = new ArrayList<>();
        for (Product s:productService.list
        ) {
            if (s.getName().contains(findName)){
                products.add(s);
            }
        }
        ModelAndView modelAndView = new ModelAndView("search","products",products);
        return modelAndView;
    }
}
