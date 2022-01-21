package co.id.jss.jssrestapi.service;

import co.id.jss.jssrestapi.domain.AuditTrail;
import co.id.jss.jssrestapi.domain.User;
import co.id.jss.jssrestapi.repository.AuditTrailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuditTrailService {

    private Logger LOG = LoggerFactory.getLogger(AuditTrailService.class);

    private final AuditTrailRepository auditTrailRepository;

    @Autowired
    public AuditTrailService(AuditTrailRepository auditTrailRepository) {

        this.auditTrailRepository = auditTrailRepository;
    }

    @Transactional
    public void addAuditTrail(User user, String uri, String method) {

        try {

            AuditTrail auditTrail = new AuditTrail();

            String activity = "( " + method + " ) " + uri;

            auditTrail.setDateTime(LocalDateTime.now());
            auditTrail.setUserId(user.getId());
            auditTrail.setActivity(activity);

            auditTrailRepository.save(auditTrail);
        }
        catch (Exception ex) {

            LOG.error("Error Adding Audit Trail ===> " + user.getUserlogin() + ", request : " + "( " + method + " ) " + uri);
        }
    }
}
