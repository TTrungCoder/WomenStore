package com.web.controller.admin;

import javax.validation.Valid;

import com.web.domain.Category;
import com.web.model.CategoryDTO;
import com.web.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("")
    public String list(ModelMap model) {
        List<Category> list = categoryService.findAll();

        model.addAttribute("categories", list);
        return "admin/categories/list";
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "admin/categories/addOrEdit";
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(ModelMap model, @PathVariable("id") Long id) {

        //tìm kiếm id category
        Optional<Category> opt = categoryService.findById(id);

        //tạo ra đối tượng
        CategoryDTO dto = new CategoryDTO();


        //kiểm tra nếu tồn tại
        if (opt.isPresent()) {

            //lấy thông tin category
            Category entity = opt.get();

            //copy thông tin sang dto
            BeanUtils.copyProperties(entity, dto);

            dto.setIsEdit(true);

            model.addAttribute("category", dto);

            return new ModelAndView("admin/categories/addOrEdit");
        }

        model.addAttribute("message","Category wasn't existed");

        return new ModelAndView("forward:/admin/categories", model);


    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(ModelMap model,
                               @PathVariable("id") Long id) {

        categoryService.deleteById(id);

        model.addAttribute("message", "Category was deleted !");

        return new ModelAndView("forward:/admin/categories", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("category") CategoryDTO dto, BindingResult result) {

        if(result.hasErrors()) {
            return new ModelAndView("admin/categories/addOrEdit", model);
        }

        Category entity = new Category();

        BeanUtils.copyProperties(dto, entity);

        categoryService.save(entity);

        model.addAttribute("message", "Category was saved");

        return new ModelAndView("forward:/admin/categories", model);
    }

    @GetMapping("search")
    public String search(ModelMap model, //tìm kiếm thông tin theo tên
                         @RequestParam(name="name", required = false)String name) {

        List<Category> list = null;

        //kiểm tra nếu điền vào
        if (StringUtils.hasText(name)) {
            list = categoryService.findByNameContaining(name);
        }else {
            list = categoryService.findAll();
        }

        model.addAttribute("categories", list);

        return "admin/categories/list";
    }
}