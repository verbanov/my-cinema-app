package cinema.dao.impl;

import cinema.dao.AbstractDao;
import cinema.dao.RoleDao;
import cinema.exception.DataProcessingException;
import cinema.model.Role;
import cinema.model.Role.RoleName;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Role getByName(String roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> getByName = session.createQuery(
                    "FROM Role r WHERE r.roleName = :roleName", Role.class);
            RoleName name = RoleName.valueOf(roleName);
            getByName.setParameter("roleName", name);
            return getByName.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Role with roleName " + roleName + " not found", e);
        }
    }
}
