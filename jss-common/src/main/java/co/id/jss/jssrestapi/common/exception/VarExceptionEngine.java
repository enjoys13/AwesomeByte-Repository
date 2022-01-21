package co.id.jss.jssrestapi.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VarExceptionEngine {

    private final Logger LOG = LoggerFactory.getLogger(VarExceptionEngine.class);


    public ResponseEntity generate(VarException ex){

        LOG.info("======================= ERROR");

        //SEND NOTIFICATION ERROR TO IT STAFF REGISTERED IN DABATASE FOR THESE ERRORS
        LOG.info(ex.getMessage(), ex);
        return new ResponseEntity(new VarExceptionResponse(ex), ex.getHttpStatus());
    }

}
