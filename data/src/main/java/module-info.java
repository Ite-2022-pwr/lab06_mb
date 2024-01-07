module data {
    requires lombok;
    requires org.hibernate.commons.annotations;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    
    
    opens pwr.ite.bedrylo.model.data;
    opens pwr.ite.bedrylo.model.data.enums;
    opens pwr.ite.bedrylo.model.request.enums;
    opens pwr.ite.bedrylo.model.request;
    
    exports pwr.ite.bedrylo.dto;
    exports pwr.ite.bedrylo.model.data.enums;
    exports pwr.ite.bedrylo.model.request.enums;
    exports pwr.ite.bedrylo.model.request;
    exports pwr.ite.bedrylo.model.data;

}
