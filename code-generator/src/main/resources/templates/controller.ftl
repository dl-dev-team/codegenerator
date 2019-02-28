package ${basePackage}.${moduleName}.controller;

import ${basePackage}.${moduleName}.core.entity.${modelNameUpperCamel};
import ${basePackage}.${moduleName}.service.${modelNameUpperCamel}Service;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.accenture.smsf.framework.starter.web.core.annotation.RestController;

import java.util.List;

/**
 *
 * @author ${author}
 */
@RestController
@RequestMapping("/${moduleName}/${modelNameLowerCamel}")
public class ${modelNameUpperCamel}Controller {

    @Autowired
    ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping("/save")
    public int ${modelNameLowerCamel}Save(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        return ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
    }

    @PostMapping("/batch-save")
    public int ${modelNameLowerCamel}BatchSave(@RequestBody List<${modelNameUpperCamel}> ${modelNameLowerCamel}s) {
        return ${modelNameLowerCamel}Service.save(${modelNameLowerCamel}s);
    }

    @DeleteMapping("/delete")
    public int ${modelNameLowerCamel}Delete(@RequestParam("id") String id) {
	    return ${modelNameLowerCamel}Service.delete(id);
    }

    @DeleteMapping("/batch-delete")
    public int ${modelNameLowerCamel}BatchDelete(@RequestParam("ids") String ids) {
        return ${modelNameLowerCamel}Service.batchDelete("\"" + String.join("\",\"", ids.split(",")) + "\"");
    }

    @PutMapping("/update")
    public int ${modelNameLowerCamel}Update(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
	    return ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
    }

    @GetMapping("/find")
    public ${modelNameUpperCamel} ${modelNameLowerCamel}Find(@RequestParam("id") String id) {
        return ${modelNameLowerCamel}Service.findById(id);
    }

    @GetMapping("/list-paged/{page-no}/{page-size}")
    public PageInfo<${modelNameUpperCamel}> ${modelNameLowerCamel}ListPaged(@PathVariable(value="page-no") int
    pageNumber,
    @PathVariable(value="page-size") int pageSize) {
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.list(pageNumber, pageSize);
        return new PageInfo<>(list);
    }

    @GetMapping("/list")
    public List<${modelNameUpperCamel}> ${modelNameLowerCamel}List() {
        return ${modelNameLowerCamel}Service.list();
    }

    @PostMapping("/find-by-paged/{page-no}/{page-size}")
    public PageInfo<${modelNameUpperCamel}> ${modelNameLowerCamel}FindByPaged(@RequestBody ${modelNameUpperCamel}
    ${modelNameLowerCamel}, @PathVariable("page-no") int pageNumber, @PathVariable("page-size") int pageSize) {
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findBy(${modelNameLowerCamel}, pageNumber, pageSize);
        return new PageInfo<>(list);
    }

    @PostMapping("/find-by")
    public List<${modelNameUpperCamel}> ${modelNameLowerCamel}FindBy(@RequestBody ${modelNameUpperCamel}
    ${modelNameLowerCamel}) {
        return ${modelNameLowerCamel}Service.findBy(${modelNameLowerCamel});
    }

    @GetMapping("/find-one")
    public ${modelNameUpperCamel} ${modelNameLowerCamel}FindOne(@RequestParam("fieldName") String fieldName,
    @RequestParam("value") String
    value) {
        return ${modelNameLowerCamel}Service.findBy(fieldName, value);
    }

    @PostMapping("/find-by/{columns}")
    public List<${modelNameUpperCamel}> ${modelNameLowerCamel}FindByColumnsPaged(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel},
    @PathVariable("columns") String columns) {
        return ${modelNameLowerCamel}Service.findByColumns(${modelNameLowerCamel}, columns);
    }
}
