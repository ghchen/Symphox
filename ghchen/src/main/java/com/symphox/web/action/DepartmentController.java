package com.symphox.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.symphox.dto.DepartmentDto;
import com.symphox.dto.RestResponse;
import com.symphox.service.DepartmentService;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService ds;

    @GetMapping(value = "/api/dept/{id}")
    public DepartmentDto fetch(@PathVariable("id") String id) {
        id = HtmlUtils.htmlEscape(id);
        return ds.fetch(id);
    }

    @PostMapping(value = "/api/dept")
    public DepartmentDto create(@RequestBody DepartmentDto form) {
        return ds.create(form);
    }

    @PutMapping(value = "/api/dept")
    public DepartmentDto update(@RequestBody DepartmentDto form) {
        return ds.update(form);
    }

    @DeleteMapping(value = "/api/dept/{id}")
    public RestResponse disable(@PathVariable("id") String id) {
        id = HtmlUtils.htmlEscape(id);
        return ds.disable(id);
    }

    @PutMapping(value = "/api/dept/{id}")
    public RestResponse enabled(@PathVariable("id") String id) {
        id = HtmlUtils.htmlEscape(id);
        return ds.enabled(id);
    }

}
