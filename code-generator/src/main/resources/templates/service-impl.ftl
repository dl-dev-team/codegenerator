package ${basePackage}.${moduleName}.service.impl;

import ${basePackage}.${moduleName}.core.entity.${modelNameUpperCamel};
import ${basePackage}.${moduleName}.service.${modelNameUpperCamel}Service;
import com.ac.smsf.codegen.core.service.impl.AbstractMapperServiceImpl;
import com.accenture.smsf.framework.boot.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ${author}
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class ${modelNameUpperCamel}ServiceImpl extends AbstractMapperServiceImpl<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {


}
