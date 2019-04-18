package controller;

import model.Model;
import model.ProductModel;
import org.springframework.web.bind.annotation.*;
import services.ProductService;
import util.Response;

@RestController
public class ProductController {
    private ProductService productService = ProductService.getInstance();


    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Response getProductModel(@PathVariable("id") String productId) {
        if (productId == null || productId.isEmpty()) {
            return new Response().setState(false).setData("Product id parameter must be given");
        }
        Model model = productService.getModel(productId);
        if (productService.hasErrors()) {
            return new Response().setState(false).setData(productService.getError());
        }
        return new Response().setState(true).setData(model);
    }

    @RequestMapping(value =  "/products", method = RequestMethod.POST)
    @ResponseBody
    public Response createUser(@RequestBody ProductModel productModel) {
        if (productModel == null) {
            return new Response().setState(false).setData("Product data must be given");
        }
        productService.createModel(productModel);
        if (productService.hasErrors()) {
            return new Response().setState(false).setData(productService.getError());
        }
        return new Response().setState(true).setData("Product was created");
    }

    @RequestMapping(value =  "/products", method = RequestMethod.PUT)
    @ResponseBody
    public Response updateUser(@RequestBody ProductModel productModel) {
        if (productModel == null) {
            return new Response().setState(false).setData("Product data must be given");
        }
        productService.updateModel(productModel);
        if (productService.hasErrors()) {
            return new Response().setState(false).setData(productService.getError());
        }
        return new Response().setState(true).setData("Product was updated");
    }

    @RequestMapping(value =  "/products/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deleteUser(@PathVariable("id") String productId) {
        if (productId == null || productId.isEmpty()) {
            return new Response().setState(false).setData("Product id parameter must be given");
        }
        productService.deleteModel(productId);
        if (productService.hasErrors()) {
            return new Response().setState(false).setData(productService.getError());
        }
        return new Response().setState(true).setData("Product was deleted");
    }
}
