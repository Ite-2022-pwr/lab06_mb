module data {
    requires lombok;
    requires org.hibernate.commons.annotations;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    
    
    opens pwr.ite.bedrylo.model;
    opens pwr.ite.bedrylo.model.enums;
    
    exports pwr.ite.bedrylo.dto;
    exports pwr.ite.bedrylo.model.enums;
    exports pwr.ite.bedrylo.model;
}
