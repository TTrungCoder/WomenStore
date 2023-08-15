package com.web.controller.admin;

import com.web.domain.Account;
import com.web.model.AccountDTO;
import com.web.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("account", new AccountDTO());
        return "admin/accounts/addOrEdit";
    }

    @GetMapping("edit/{username}")
    public ModelAndView edit(ModelMap model, @PathVariable("username") String username) {

        //tìm kiếm id category
        Optional<Account> opt = accountService.findById(username);

        //tạo ra đối tượng
        AccountDTO dto = new AccountDTO();


        //kiểm tra nếu tồn tại
        if (opt.isPresent()) {

            //lấy thông tin category
            Account entity = opt.get();

            //copy thông tin sang dto
            BeanUtils.copyProperties(entity, dto);

            dto.setIsEdit(true);

            dto.setPassword("");

            model.addAttribute("account", dto);

            return new ModelAndView("admin/accounts/addOrEdit", model);
        }

        model.addAttribute("message", "Account is not existed");

        return new ModelAndView("forward:/admin/categories", model);


    }

    @GetMapping("delete/{username}")
    public ModelAndView delete(ModelMap model,
                               @PathVariable("username") String username) {

        accountService.deleteById(username);

        model.addAttribute("message", "Accounts is deleted !");

        return new ModelAndView("forward:/admin/accounts/", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model,
                                     @Valid @ModelAttribute("account") AccountDTO dto, BindingResult result) {

        //nếu có lỗi -> nạp lại trang addOrEdit
        if (result.hasErrors()) {
            return new ModelAndView("admin/accounts/addOrEdit");
        }

        //Tạo ra đối tượng entity
        Account entity = new Account();

        //Copy dữ liệu từ đối tượng dto -> entity
        BeanUtils.copyProperties(dto, entity);

        //Lưu thông tin entity vào CSDL
        accountService.save(entity);


        //Hiển thị thông báo
        model.addAttribute("message", "The Account is saved !");

        return new ModelAndView("forward:/admin/accounts", model);
    }

    @RequestMapping("")
    public String list(ModelMap model) {

        List<Account> list = accountService.findAll();

        model.addAttribute("accounts", list);

        return "admin/accounts/list";
    }
}