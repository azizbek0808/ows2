package uz.com.repository;

import org.springframework.stereotype.Repository;
import uz.com.criteria.GenericCriteria;
import uz.com.dto.auth.AttachPermissionRoleDto;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.dao.GenericDao;

import java.sql.Types;

@Repository
public class GeneralRepository extends GenericDao<_Entity, GenericCriteria> implements IGeneralRepository {

    @Override
    public Boolean attachRole(AttachPermissionRoleDto dto) {
        return call(dto, "attachPermissionRole", Types.BOOLEAN);
    }
}
