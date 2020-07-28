package com.symphox.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.symphox.dto.RestResponse;
import com.symphox.dto.StaffDto;
import com.symphox.dto.StaffQueryForm;
import com.symphox.service.StaffService;

@RestController
public class StaffController {

    @Autowired
    private StaffService ss;

    @GetMapping(value = "/api/findAll/staffs")
    public Page<StaffDto> findAll(Pageable p) {
        return ss.findAll(p);
    }

    @GetMapping(value = "/api/staffs")
    public Page<StaffDto> query(StaffQueryForm form, @PageableDefault(value = 10) Pageable p) {
        return ss.query(form, p);
    }

    @GetMapping(value = "/api/staff/{id}")
    public StaffDto fetch(@PathVariable("id") String id) {
        id = HtmlUtils.htmlEscape(id);
        return ss.fetch(id);
    }

    @PostMapping(value = "/api/staff")
    public StaffDto create(@RequestBody StaffDto form) {
        return ss.create(form);
    }

    @PutMapping(value = "/api/staff")
    public StaffDto update(@RequestBody StaffDto form) {
        return ss.update(form);
    }

    @DeleteMapping(value = "/api/staff/{id}")
    public RestResponse disable(@PathVariable("id") String id) {
        id = HtmlUtils.htmlEscape(id);
        return ss.disable(id);
    }

    @PutMapping(value = "/api/staff/{id}")
    public RestResponse enabled(@PathVariable("id") String id) {
        id = HtmlUtils.htmlEscape(id);
        return ss.enabled(id);
    }

}
